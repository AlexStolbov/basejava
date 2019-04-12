package com.amstolbov.storage;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;
import com.amstolbov.storage.serializers.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    protected Path directory;
    private final Serializer serializer;

    public PathStorage(String dir, Serializer serializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        this.directory = Paths.get(dir);
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not readable/writable");
        }
        this.serializer = serializer;
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey.toFile())));
        } catch (IOException e) {
            throw new StorageException("Can't update resume", searchKey.toString(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path existPosition) {
        try {
            Path newPath = Files.createFile(existPosition);
            serializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(newPath.toFile())));
        } catch (IOException e) {
            throw new StorageException("Can't save file", existPosition.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path existPosition) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(existPosition.toFile())));
        } catch (IOException e) {
            throw new StorageException("Can't read file storage: ", existPosition.toString());
        }
    }

    @Override
    protected  void doDelete(Path existPosition) {
        try {
            Files.delete(existPosition);
        } catch(IOException ioe) {
            throw new StorageException("Can't delete resume", existPosition.toString(), ioe);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected  boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected  List<Resume> getCopyAll() {
        return getFiles().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFiles().count();
    }

    private Stream<Path> getFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Can't read file storage", null, e);
        }
    }

}
