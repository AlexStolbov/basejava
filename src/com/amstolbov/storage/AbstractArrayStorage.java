package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int findIndex = getIndex(resume.getUuid());
        if (findIndex < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[findIndex] = resume;
        }
    }

    @Override
    public void save(Resume resume) {
        int findIndex = getIndex(resume.getUuid());
        if (findIndex > -1) {
            throw new ExistStorageException(resume.getUuid());
        } else if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            int saveIndex = getSaveIndex(findIndex);
            storage[saveIndex] = resume;
            size++;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(findIndex);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract int getSaveIndex(int findIndex);

    protected abstract void deleteElement(int findIndex);
}
