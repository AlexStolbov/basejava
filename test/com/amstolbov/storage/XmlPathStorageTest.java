package com.amstolbov.storage;

import com.amstolbov.storage.serializers.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest{

    public XmlPathStorageTest() {
        super(new PathStorage(AbstractStorage.STORAGE_PATH, new XmlStreamSerializer()));
    }

}