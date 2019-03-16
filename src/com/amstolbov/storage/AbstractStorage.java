package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;

public abstract class AbstractStorage implements Storage {
    private final String FIND_EXIST = "FIND_EXIST";
    private final String FIND_EMPTY = "FIND_EMPTY";

    @Override
    public void update(Resume resume) {
        Object existPosition = checkForExistElement(resume.getUuid(), FIND_EXIST);
        updateElement(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        Object existPosition = checkForExistElement(resume.getUuid(), FIND_EMPTY);
        saveElement(resume, existPosition);
    }

    @Override
    public Resume get(String uuid) {
        Object existPosition = checkForExistElement(uuid, FIND_EXIST);
        return getElement(existPosition);
    }

    @Override
    public void delete(String uuid) {
        Object existPosition = checkForExistElement(uuid, FIND_EXIST);
        deleteElement(existPosition);
    }

    protected abstract void updateElement(Object existPosition, Resume resume);

    protected abstract void saveElement(Resume resume, Object existPosition);

    protected abstract Resume getElement(Object existPosition);

    protected abstract void deleteElement(Object existPosition);

    protected abstract Object getExistPosition(String uuid);

    protected abstract boolean elementExistInThisPosition(Object existPosition);

    private Object checkForExistElement(String uuid, String findType) {
        Object existPosition = getExistPosition(uuid);
        boolean elementExist = elementExistInThisPosition(existPosition);
        if (findType.equals(FIND_EXIST) && !elementExist) {
            throw new NotExistStorageException(uuid);
        }
        if (findType.equals(FIND_EMPTY) && elementExist) {
            throw new ExistStorageException(uuid);
        }
        return existPosition;
    }
}
