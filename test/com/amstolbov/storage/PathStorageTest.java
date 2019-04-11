package com.amstolbov.storage;

import static org.junit.Assert.*;

public class PathStorageTest extends AbstractStorageTest{

    public PathStorageTest() {
        super(new PathStorage(AbstractStorage.STORAGE_PATH, new ObjectStreamStorage()));
    }

}