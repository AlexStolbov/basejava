package com.amstolbov.storage;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    protected File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void doUpdate(File existPosition, Resume resume) {
        doWrite(resume, existPosition);
    }

    @Override
    protected void doSave(Resume resume, File existPosition) {
        try {
            existPosition.createNewFile();
            doWrite(resume, existPosition);
        } catch (IOException e) {
            throw new StorageException("IOError", existPosition.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file);

    @Override
    protected Resume doGet(File existPosition) {
        return doRead(existPosition);
    }

    protected abstract Resume doRead(File file);

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File existPosition) {
        return existPosition.exists();
    }

    @Override
    protected List<Resume> getCopyAll() {
        List<Resume> result = new ArrayList<>();
        for (File f : directory.listFiles()) {
            result.add(doRead(f));
        }
        return result;
    }

    @Override
    public void clear() {
        for (File f : directory.listFiles()) {
            f.delete();
        }
    }

    @Override
    public int size() {
        int result = 0;
        for (File f : directory.listFiles()) {
            result++;
        }
        return result;
    }
}
