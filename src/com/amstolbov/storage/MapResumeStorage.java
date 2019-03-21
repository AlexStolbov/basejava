package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public List<Resume> getAllSortedCertainStorage() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(Object existPosition, Resume resume) {
        storage.put(((Resume) existPosition).getUuid(), resume);
    }

    @Override
    protected void saveElement(Resume resume, Object existPosition) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object existPosition) {
        return (Resume) existPosition;
    }

    @Override
    protected void deleteElement(Object existElement) {
        storage.remove(((Resume)existElement).getUuid());
    }

    @Override
    protected Resume getExistPosition(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean elementExistInThisPosition(Object existPosition) {
        return existPosition != null;
    }
}
