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
    protected void doUpdate(String searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, String existPosition) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(String existPosition) {
        return storage.get(existPosition);
    }

    @Override
    protected void doDelete(String existElement) {
        storage.remove(existElement);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }
}
