package com.amstolbov.storage;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected ExistPosition getExistPosition(String uuid) {
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                result = i;
                break;
            }
        }
        return new ExistPosition((result >= 0), result, "");
    }

    @Override
    protected int getSaveIndex(int existPosition) {
        return size;
    }

    @Override
    protected void deleteArrayElement(int findIndex) {
        storage[findIndex] = storage[size - 1];
    }
}
