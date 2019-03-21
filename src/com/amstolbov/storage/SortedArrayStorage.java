package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getExistPosition(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        int result = Arrays.binarySearch(storage, 0, size, searchKey, (Resume::compareTo));
        return result;
    }

    @Override
    protected int getSaveIndex(int existPosition) {
        int insertIndex = -existPosition - 1;
        if (insertIndex != size) {
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        }
        return insertIndex;
    }

    @Override
    protected void deleteArrayElement(int findIndex) {
        if (findIndex < size - 1) {
            System.arraycopy(storage, findIndex + 1, storage, findIndex, size - findIndex - 1);
        }
    }

}
