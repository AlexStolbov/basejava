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
    protected void updateElement(ExistPosition existPosition, Resume resume) {
        storage[existPosition.intPos] = resume;
    }

    @Override
    protected void saveElement(Resume resume, ExistPosition existPosition) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storage[getSaveIndex(existPosition.intPos)] = resume;
        size++;
    }

    @Override
    protected Resume getElement(ExistPosition existPosition) {
        return storage[existPosition.intPos];
    }

    @Override
    protected void deleteElement(ExistPosition existPosition) {
        deleteArrayElement(existPosition.intPos);
        storage[size - 1] = null;
        size--;
    }

}
