package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void update(Resume resume) {
    }

    public void save(Resume resume) {
    }

    public void delete(String uuid) {
    }

    public Resume[] getAll() {
        return new Resume[0];
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
