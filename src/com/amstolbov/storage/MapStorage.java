package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[]{});
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected void updateElement(Object existPosition, Resume resume) {
        storage.put((String)existPosition, resume);
    }

    @Override
    protected void saveElement(Resume resume, Object existPosition) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object existPosition) {
        return storage.get(existPosition);
    }

    @Override
    protected void deleteElement(Object existElement) {
        storage.remove(existElement);
    }

    @Override
    protected String getExistPosition(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return NOT_EXIST_INDEX_COLLECTION;
    }

}
