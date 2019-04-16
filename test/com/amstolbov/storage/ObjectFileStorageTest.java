package com.amstolbov.storage;

import com.amstolbov.storage.serializers.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest{

    public ObjectFileStorageTest() {
        super(new FileStorage(AbstractStorage.STORAGE_DIR, new ObjectStreamSerializer()));
    }

}