package com.amstolbov.storage;

import com.amstolbov.Config;

public class SqlStorageTest  extends AbstractStorageTest{

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getParam(Config.ParamType.DB_URL)
                , Config.get().getParam(Config.ParamType.DB_USER)
                , Config.get().getParam(Config.ParamType.DB_PASSWORD)));
    }
}