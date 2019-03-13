package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public void update(Resume resume) {
        ExistPosition existPosition = checkForExistElement(resume.getUuid());
        updateElement(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        ExistPosition existPosition = getExistPosition(resume.getUuid());
        if (existPosition.exist) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(resume, existPosition);
        }
    }

    @Override
    public Resume get(String uuid) {
        ExistPosition existPosition = checkForExistElement(uuid);
        return getElement(existPosition);
    }

    @Override
    public void delete(String uuid) {
        ExistPosition existPosition = checkForExistElement(uuid);
        deleteElement(existPosition);
    }

    protected abstract void clearStorage();

    protected abstract void updateElement(ExistPosition existPosition, Resume resume);

    protected abstract void saveElement(Resume resume, ExistPosition existPosition);

    protected abstract Resume getElement(ExistPosition existPosition);

    protected abstract void deleteElement(ExistPosition existPosition);

    protected abstract ExistPosition getExistPosition(String uuid);

    protected class ExistPosition {
        protected boolean exist;
        protected int intPos;
        protected String strPos;

        protected ExistPosition(boolean exist, int intPos, String strPos) {
            this.exist = exist;
            this.intPos = intPos;
            this.strPos = strPos;
        }
    }

    private ExistPosition checkForExistElement(String uuid) {
        ExistPosition existPosition = getExistPosition(uuid);
        if (!existPosition.exist) {
            throw new NotExistStorageException(uuid);
        } else {
            return existPosition;
        }
    }
}
