package com.amstolbov.storage;

public class ArrayStorage extends AbstractArrayStorage{

    @Override
    public void deleteElement(int findIndex) {
        storage[findIndex] = storage[size - 1];
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int getSaveIndex(int findIndex) {
        return size;
    }
}
