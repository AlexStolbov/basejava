package com.amstolbov.storage;

import com.amstolbov.storage.serializers.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest{
    public JsonPathStorageTest() {
        super(new PathStorage(AbstractStorageTest.STORAGE_PATH, new JsonStreamSerializer()));
    }
}
