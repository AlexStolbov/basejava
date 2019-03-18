package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<Resume, Resume> storage = new HashMap<>();

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>(storage.values());
        result.sort(COMPARE_FULL_NAME);
        return result;
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
        storage.put((Resume) existPosition, resume);
    }

    @Override
    protected void saveElement(Resume resume, Object existPosition) {
        storage.put(resume, resume);
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
    protected Resume getExistPosition(String uuid) {
        Resume key = new Resume(uuid);
        if (storage.containsKey(key)) {
            return key;
        } else {
            return null;
        }
    }

    @Override
    protected boolean elementExistInThisPosition(Object existPosition) {
        return existPosition != null;
    }
}
