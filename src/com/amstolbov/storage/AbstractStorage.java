package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK existPosition = checkForExistElement(resume.getUuid());
        updateElement(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK existPosition = checkForNotExistElement(resume.getUuid());
        saveElement(resume, existPosition);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK existPosition = checkForExistElement(uuid);
        return getElement(existPosition);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK existPosition = checkForExistElement(uuid);
        deleteElement(existPosition);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> result = getCopyAll();
        Collections.sort(result);
        return result;
    }

    protected abstract void updateElement(SK existPosition, Resume resume);

    protected abstract void saveElement(Resume resume, SK existPosition);

    protected abstract Resume getElement(SK existPosition);

    protected abstract void deleteElement(SK existPosition);

    protected abstract SK getExistPosition(String uuid);

    protected abstract boolean elementExistInThisPosition(SK existPosition);

    protected abstract List<Resume> getCopyAll();

    private SK checkForExistElement(String uuid) {
        SK existPosition = getExistPosition(uuid);
        if (!elementExistInThisPosition(existPosition)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return existPosition;
    }

    private SK checkForNotExistElement(String uuid) {
        SK existPosition = getExistPosition(uuid);
        if (elementExistInThisPosition(existPosition)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return existPosition;
    }

}
