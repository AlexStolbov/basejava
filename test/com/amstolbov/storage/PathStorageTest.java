package com.amstolbov.storage;

import com.amstolbov.storage.serializers.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest{

    public PathStorageTest() {
        super(new PathStorage(AbstractStorage.STORAGE_PATH, new ObjectStreamStorage()));
    }

}