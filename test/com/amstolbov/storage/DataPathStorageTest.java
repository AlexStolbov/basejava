package com.amstolbov.storage;

import com.amstolbov.storage.serializers.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(AbstractStorage.STORAGE_PATH, new DataStreamSerializer()));
    }
}
