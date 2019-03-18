package com.amstolbov.storage;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));
        result.sort(COMPARE_FULL_NAME);
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getSaveIndex(int existPosition);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateElement(Object existPosition, Resume resume) {
        storage[(Integer) existPosition] = resume;
    }

    @Override
    protected void saveElement(Resume resume, Object existPosition) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storage[getSaveIndex((Integer) existPosition)] = resume;
        size++;
    }

    @Override
    protected Resume getElement(Object existPosition) {
        return storage[(Integer) existPosition];
    }

    @Override
    protected void deleteElement(Object existPosition) {
        deleteArrayElement((Integer) existPosition);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteArrayElement(int findIndex);

    @Override
    protected boolean elementExistInThisPosition(Object existPosition) {
        return (Integer) existPosition > -1;
    }

}
