package com.amstolbov.storage;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;
import com.amstolbov.storage.serializers.StreamSerializer;

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
    private final StreamSerializer serializer;

    public PathStorage(String dir, StreamSerializer serializer) {
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
            serializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Can't update resume", getFileName(searchKey), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path searchKey) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Can't save file", getFileName(searchKey), e);
        }
        doUpdate(searchKey, resume);
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Can't read file storage: ", getFileName(searchKey));
        }
    }

    @Override
    protected  void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch(IOException ioe) {
            throw new StorageException("Can't delete resume", getFileName(searchKey), ioe);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected  boolean isExist(Path searchKey) {
        return Files.isRegularFile(searchKey);
    }

    @Override
    protected  List<Resume> getCopyAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Can't read file storage", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
