package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void deleteElement(int findIndex) {
        if (findIndex < size - 1) {
            System.arraycopy(storage, findIndex + 1, storage, findIndex, size - findIndex - 1);
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected int getSaveIndex(int findIndex) {
            int insertIndex = -findIndex - 1;
            if (insertIndex != size) {
                System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
            }
            return insertIndex;
        }
}
