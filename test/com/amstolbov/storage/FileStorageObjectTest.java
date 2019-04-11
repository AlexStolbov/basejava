package com.amstolbov.storage;

public class FileStorageObjectTest extends AbstractStorageTest{

    public FileStorageObjectTest() {
        super(new FileStorage(AbstractStorage.STORAGE_DIR, new ObjectStreamStorage()));
    }

}