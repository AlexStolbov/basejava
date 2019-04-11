package com.amstolbov.storage;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    protected File directory;
    private final Serializator serializator;

    public FileStorage(File directory, Serializator serializator) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializator = serializator;
    }

    @Override
    protected void doUpdate(File searchKey, Resume resume) {
        try {
            serializator.doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Can't update resume", searchKey.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File existPosition) {
        try {
            existPosition.createNewFile();
            serializator.doWrite(resume, new BufferedOutputStream(new FileOutputStream(existPosition)));
        } catch (IOException e) {
            throw new StorageException("Can't save file", existPosition.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File existPosition) {
        try {
            return serializator.doRead(new BufferedInputStream(new FileInputStream(existPosition)));
        } catch (IOException e) {
            throw new StorageException("Can't read file storage: ", existPosition.getName());
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Can't clear file storage: ", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected List<Resume> getCopyAll() {
        List<Resume> result = new ArrayList<>();
        for (File f : getFiles()) {
            result.add(doGet(f));
        }
        return result;
    }

    @Override
    public void clear() {
        for (File f : getFiles()) {
            doDelete(f);
        }
    }

    @Override
    public int size() {
        String[] files = directory.list();
        if (files == null) {
            throw new StorageException("Can't read file storage", "");
        }
        return files.length;
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Can't read file storage", "");
        }
        return files;
    }
}
