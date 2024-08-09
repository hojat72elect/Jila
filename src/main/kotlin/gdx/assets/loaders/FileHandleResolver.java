package gdx.assets.loaders;

import gdx.assets.AssetManager;
import gdx.files.FileHandle;

/**
 * Interface for classes the can map a file name to a {@link FileHandle}. Used to allow the {@link AssetManager} to load
 * resources from anywhere or implement caching strategies.
 */
public interface FileHandleResolver {
    public FileHandle resolve(String fileName);
}
