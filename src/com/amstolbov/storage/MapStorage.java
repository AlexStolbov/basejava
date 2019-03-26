package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public List<Resume> getCopyAll() {
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
    protected void updateElement(String existPosition, Resume resume) {
        storage.put((String) existPosition, resume);
    }

    @Override
    protected void saveElement(Resume resume, String existPosition) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(String existPosition) {
        return storage.get(existPosition);
    }

    @Override
    protected void deleteElement(String existElement) {
        storage.remove(existElement);
    }

    @Override
    protected String getExistPosition(String uuid) {
        return uuid;
    }

    @Override
    protected boolean elementExistInThisPosition(String existPosition) {
        return storage.containsKey(existPosition);
    }
}
