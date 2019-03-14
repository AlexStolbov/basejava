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
        String existPosition = checkForExistElement(resume.getUuid());
        updateElement(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        String existPosition = getExistPosition(resume.getUuid());
        if (elementExistInThisPosition(existPosition)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(resume, existPosition);
        }
    }

    @Override
    public Resume get(String uuid) {
        String existPosition = checkForExistElement(uuid);
        return getElement(existPosition);
    }

    @Override
    public void delete(String uuid) {
        String existPosition = checkForExistElement(uuid);
        deleteElement(existPosition);
    }

    protected abstract void clearStorage();

    protected abstract void updateElement(String existPosition, Resume resume);

    protected abstract void saveElement(Resume resume, String existPosition);

    protected abstract Resume getElement(String existPosition);

    protected abstract void deleteElement(String existPosition);

    protected abstract String getExistPosition(String uuid);

    protected boolean elementExistInThisPosition(String existPosition) {
        return !existPosition.equals(NOT_EXIST_INDEX_COLLECTION) ;
    };

    private String checkForExistElement(String uuid) {
        String existPosition = getExistPosition(uuid);
        if (!elementExistInThisPosition(existPosition)) {
            throw new NotExistStorageException(uuid);
        } else {
            return existPosition;
        }
    }
}
