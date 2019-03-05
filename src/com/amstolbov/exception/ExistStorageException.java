package com.amstolbov.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super(String.format("Resume %s already exist", uuid), uuid);
    }
}
