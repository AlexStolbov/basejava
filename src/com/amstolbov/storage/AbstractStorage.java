package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected final String NOT_EXIST_INDEX_COLLECTION = "";

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public void update(Resume resume) {
        Object existPosition = checkForExistElement(resume.getUuid());
        updateElement(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        Object existPosition = getExistPosition(resume.getUuid());
        if (elementExistInThisPosition(existPosition)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(resume, existPosition);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object existPosition = checkForExistElement(uuid);
        return getElement(existPosition);
    }

    @Override
    public void delete(String uuid) {
        Object existPosition = checkForExistElement(uuid);
        deleteElement(existPosition);
    }

    protected abstract void clearStorage();

    protected abstract void updateElement(Object existPosition, Resume resume);

    protected abstract void saveElement(Resume resume, Object existPosition);

    protected abstract Resume getElement(Object existPosition);

    protected abstract void deleteElement(Object existPosition);

    protected abstract Object getExistPosition(String uuid);

    protected boolean elementExistInThisPosition(Object existPosition) {
        return !existPosition.equals(NOT_EXIST_INDEX_COLLECTION) ;
    }

    private Object checkForExistElement(String uuid) {
        Object existPosition = getExistPosition(uuid);
        if (!elementExistInThisPosition(existPosition)) {
            throw new NotExistStorageException(uuid);
        } else {
            return existPosition;
        }
    }
}
