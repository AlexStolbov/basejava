package com.amstolbov.storage;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getSaveIndex(int existPosition);

    protected abstract void deleteArrayElement(int findIndex);

    @Override
    protected void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateElement(int existPosition, Resume resume) {
        storage[existPosition] = resume;
    }

    @Override
    protected void saveElement(Resume resume, int existPosition) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storage[getSaveIndex(existPosition)] = resume;
        size++;
    }

    @Override
    protected Resume getElement(int existPosition) {
        return storage[existPosition];
    }

    @Override
    protected void deleteElement(int existPosition) {
        deleteArrayElement(existPosition);
        storage[size - 1] = null;
        size--;
    }

}
