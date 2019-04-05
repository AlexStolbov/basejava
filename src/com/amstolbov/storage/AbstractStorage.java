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
        SK existPosition = getExistedSearchKey(resume.getUuid());
        doUpdate(existPosition, resume);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK existPosition = getNotExistedSearchKey(resume.getUuid());
        doSave(resume, existPosition);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK existPosition = getExistedSearchKey(uuid);
        return doGet(existPosition);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK existPosition = getExistedSearchKey(uuid);
        doDelete(existPosition);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> result = getCopyAll();
        Collections.sort(result);
        return result;
    }

    protected abstract void doUpdate(SK existPosition, Resume resume);

    protected abstract void doSave(Resume resume, SK existPosition);

    protected abstract Resume doGet(SK existPosition);

    protected abstract void doDelete(SK existPosition);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK existPosition);

    protected abstract List<Resume> getCopyAll();

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
