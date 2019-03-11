package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getExistPosition(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected int getSaveIndex(Resume resume) {
        int insertIndex = -getExistPosition(resume.getUuid()) - 1;
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
