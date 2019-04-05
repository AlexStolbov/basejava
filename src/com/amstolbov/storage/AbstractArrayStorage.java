package com.amstolbov.storage;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public List<Resume> getCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
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
    protected void doUpdate(Integer existPosition, Resume resume) {
        storage[existPosition] = resume;
    }

    @Override
    protected void doSave(Resume resume, Integer existPosition) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storage[getSaveIndex(existPosition)] = resume;
        size++;
    }

    @Override
    protected Resume doGet(Integer existPosition) {
        return storage[existPosition];
    }

    @Override
    protected void doDelete(Integer existPosition) {
        deleteArrayElement(existPosition);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteArrayElement(int findIndex);

    @Override
    protected boolean isExist(Integer existPosition) {
        return existPosition > -1;
    }

}
