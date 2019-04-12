package com.amstolbov.storage;

import com.amstolbov.storage.serializers.ObjectStreamStorage;

public class FileStorageTest extends AbstractStorageTest{

    public FileStorageTest() {
        super(new FileStorage(AbstractStorage.STORAGE_DIR, new ObjectStreamStorage()));
    }

}