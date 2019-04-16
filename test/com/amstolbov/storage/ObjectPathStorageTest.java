package com.amstolbov.storage;

import com.amstolbov.storage.serializers.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest{

    public ObjectPathStorageTest() {
        super(new PathStorage(AbstractStorage.STORAGE_PATH, new ObjectStreamSerializer()));
    }

}