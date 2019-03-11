package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected int size = 0;

    @Override
    public void clear() {
        clearStorage();
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int existPosition = checkForExistElement(resume.getUuid());
        updateElement(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        if (getExistPosition(resume.getUuid()) > -1) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(resume);
            size++;
        }
    }

    @Override
    public Resume get(String uuid) {
        int existPosition = checkForExistElement(uuid);
        return getElement(existPosition);
    }

    @Override
    public void delete(String uuid) {
        int existPosition = checkForExistElement(uuid);
        deleteElement(existPosition);
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void clearStorage();

    protected abstract void updateElement(int existPosition, Resume resume);

    protected abstract void saveElement(Resume resume);

    protected abstract Resume getElement(int existPosition);

    protected abstract void deleteElement(int existPosition);

    protected abstract int getExistPosition(String uuid);

    private int checkForExistElement(String uuid) {
        int existPosition = getExistPosition(uuid);
        if (existPosition < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return existPosition;
        }
    }
}
