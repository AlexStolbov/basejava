package com.amstolbov.storage;

import com.amstolbov.storage.serializers.ObjectStreamStorage;

public class FileStorageObjectTest extends AbstractStorageTest{

    public FileStorageObjectTest() {
        super(new FileStorage(AbstractStorage.STORAGE_DIR, new ObjectStreamStorage()));
    }

}