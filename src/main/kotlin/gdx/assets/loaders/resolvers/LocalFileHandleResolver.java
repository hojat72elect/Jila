package gdx.assets.loaders.resolvers;

import gdx.Gdx;
import gdx.assets.loaders.FileHandleResolver;
import gdx.files.FileHandle;

public class LocalFileHandleResolver implements FileHandleResolver {
    @Override
    public FileHandle resolve(String fileName) {
        return Gdx.files.local(fileName);
    }
}
