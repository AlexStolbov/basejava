package com.amstolbov.storage;

import com.amstolbov.storage.serializers.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest{
    public JsonPathStorageTest() {
        super(new PathStorage(AbstractStorage.STORAGE_PATH, new JsonStreamSerializer()));
    }
}
