package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected final Comparator<Resume> COMPARE_FULL_NAME = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void update(Resume resume) {
        Object existPosition = checkForExistElement(resume.getUuid());
        updateElement(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        Object existPosition = checkForNotExistElement(resume.getUuid());
        saveElement(resume, existPosition);
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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = getAllSortedCertainStorage();
        result.sort(COMPARE_FULL_NAME);
        return result;
    }

    protected abstract void updateElement(Object existPosition, Resume resume);

    protected abstract void saveElement(Resume resume, Object existPosition);

    protected abstract Resume getElement(Object existPosition);

    protected abstract void deleteElement(Object existPosition);

    protected abstract Object getExistPosition(String uuid);

    protected abstract boolean elementExistInThisPosition(Object existPosition);

    protected abstract List<Resume> getAllSortedCertainStorage();

    private Object checkForExistElement(String uuid) {
        Object existPosition = getExistPosition(uuid);
        if (!elementExistInThisPosition(existPosition)) {
            throw new NotExistStorageException(uuid);
        }
        return existPosition;
    }

    private Object checkForNotExistElement(String uuid) {
        Object existPosition = getExistPosition(uuid);
        if (elementExistInThisPosition(existPosition)) {
            throw new ExistStorageException(uuid);
        }
        return existPosition;
    }

}
