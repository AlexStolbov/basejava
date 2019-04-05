package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void doUpdate(Resume existPosition, Resume resume) {
        storage.put(existPosition.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Resume existPosition) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume existPosition) {
        return existPosition;
    }

    @Override
    protected void doDelete(Resume existElement) {
        storage.remove(existElement.getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume existPosition) {
        return existPosition != null;
    }
}
