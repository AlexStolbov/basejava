package com.amstolbov.storage;

import com.amstolbov.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getExistPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int getSaveIndex(Resume resume) {
        return size;
    }

    @Override
    protected void deleteArrayElement(int findIndex) {
        storage[findIndex] = storage[size - 1];
    }
}
