package gdx.assets;

import gdx.assets.loaders.AssetLoader;
import gdx.assets.loaders.AsynchronousAssetLoader;
import gdx.assets.loaders.SynchronousAssetLoader;
import gdx.files.FileHandle;
import gdx.utils.Array;
import gdx.utils.GdxRuntimeException;
import gdx.utils.Logger;
import gdx.utils.TimeUtils;
import gdx.utils.async.AsyncExecutor;
import gdx.utils.async.AsyncResult;
import gdx.utils.async.AsyncTask;

/**
 * Responsible for loading an asset through an {@link AssetLoader} based on an {@link AssetDescriptor}. Please don't forget to
 * update the overriding emu file on GWT backend when changing this file!
 */
class AssetLoadingTask implements AsyncTask<Void> {
    final AssetDescriptor assetDesc;
    final AssetLoader loader;
    final AsyncExecutor executor;
    final long startTime;
    AssetManager manager;
    volatile boolean asyncDone;
    volatile boolean dependenciesLoaded;
    volatile Array<AssetDescriptor> dependencies;
    volatile AsyncResult<Void> depsFuture;
    volatile AsyncResult<Void> loadFuture;
    volatile Object asset;

    volatile boolean cancel;

    public AssetLoadingTask(AssetManager manager, AssetDescriptor assetDesc, AssetLoader loader, AsyncExecutor threadPool) {
        this.manager = manager;
        this.assetDesc = assetDesc;
        this.loader = loader;
        this.executor = threadPool;
        startTime = manager.log.getLevel() == Logger.DEBUG ? TimeUtils.nanoTime() : 0;
    }

    /**
     * Loads parts of the asset asynchronously if the loader is an {@link AsynchronousAssetLoader}.
     */
    @Override
    public Void call() throws Exception {
        if (cancel) return null;
        AsynchronousAssetLoader asyncLoader = (AsynchronousAssetLoader) loader;
        if (!dependenciesLoaded) {
            dependencies = asyncLoader.getDependencies(assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
            if (dependencies != null) {
                removeDuplicates(dependencies);
                manager.injectDependencies(assetDesc.fileName, dependencies);
            } else {
                // if we have no dependencies, we load the async part of the task immediately.
                asyncLoader.loadAsync(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
                asyncDone = true;
            }
        } else {
            asyncLoader.loadAsync(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
            asyncDone = true;
        }
        return null;
    }

    /**
     * Updates the loading of the asset. In case the asset is loaded with an {@link AsynchronousAssetLoader}, the loaders
     * {@link AsynchronousAssetLoader#loadAsync(AssetManager, String, FileHandle, AssetLoaderParameters)} method is first called on
     * a worker thread. Once this method returns, the rest of the asset is loaded on the rendering thread via
     * {@link AsynchronousAssetLoader#loadSync(AssetManager, String, FileHandle, AssetLoaderParameters)}.
     *
     * @return true in case the asset was fully loaded, false otherwise
     * @throws GdxRuntimeException
     */
    public boolean update() {
        if (loader instanceof SynchronousAssetLoader)
            handleSyncLoader();
        else
            handleAsyncLoader();
        return asset != null;
    }

    private void handleSyncLoader() {
        SynchronousAssetLoader syncLoader = (SynchronousAssetLoader) loader;
        if (!dependenciesLoaded) {
            dependenciesLoaded = true;
            dependencies = syncLoader.getDependencies(assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
            if (dependencies == null) {
                asset = syncLoader.load(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
                return;
            }
            removeDuplicates(dependencies);
            manager.injectDependencies(assetDesc.fileName, dependencies);
        } else
            asset = syncLoader.load(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
    }

    private void handleAsyncLoader() {
        AsynchronousAssetLoader asyncLoader = (AsynchronousAssetLoader) loader;
        if (!dependenciesLoaded) {
            if (depsFuture == null)
                depsFuture = executor.submit(this);
            else if (depsFuture.isDone()) {
                try {
                    depsFuture.get();
                } catch (Exception e) {
                    throw new GdxRuntimeException("Couldn't load dependencies of asset: " + assetDesc.fileName, e);
                }
                dependenciesLoaded = true;
                if (asyncDone)
                    asset = asyncLoader.loadSync(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
            }
        } else if (loadFuture == null && !asyncDone)
            loadFuture = executor.submit(this);
        else if (asyncDone)
            asset = asyncLoader.loadSync(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
        else if (loadFuture.isDone()) {
            try {
                loadFuture.get();
            } catch (Exception e) {
                throw new GdxRuntimeException("Couldn't load asset: " + assetDesc.fileName, e);
            }
            asset = asyncLoader.loadSync(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
        }
    }

    /**
     * Called when this task is the task that is currently being processed and it is unloaded.
     */
    public void unload() {
        if (loader instanceof AsynchronousAssetLoader)
            ((AsynchronousAssetLoader) loader).unloadAsync(manager, assetDesc.fileName, resolve(loader, assetDesc), assetDesc.params);
    }

    private FileHandle resolve(AssetLoader loader, AssetDescriptor assetDesc) {
        if (assetDesc.file == null) assetDesc.file = loader.resolve(assetDesc.fileName);
        return assetDesc.file;
    }

    private void removeDuplicates(Array<AssetDescriptor> array) {
        boolean ordered = array.ordered;
        array.ordered = true;
        for (int i = 0; i < array.size; ++i) {
            final String fn = array.get(i).fileName;
            final Class type = array.get(i).type;
            for (int j = array.size - 1; j > i; --j)
                if (type == array.get(j).type && fn.equals(array.get(j).fileName)) array.removeIndex(j);
        }
        array.ordered = ordered;
    }
}