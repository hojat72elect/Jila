package gdx.assets.loaders;

import gdx.assets.AssetLoaderParameters;
import gdx.assets.AssetManager;
import gdx.files.FileHandle;

public abstract class SynchronousAssetLoader<T, P extends AssetLoaderParameters<T>> extends AssetLoader<T, P> {
    public SynchronousAssetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public abstract T load(AssetManager assetManager, String fileName, FileHandle file, P parameter);
}
