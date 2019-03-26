package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new LinkedList<>();

    @Override
    public List<Resume> getCopyAll() {
        return new LinkedList<>(storage);
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
        storage.set((Integer) existPosition, resume);
    }

    @Override
    protected void saveElement(Resume resume, Object existPosition) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Object existPosition) {
        return storage.get((Integer) existPosition);
    }

    @Override
    protected void deleteElement(Object existPosition) {
        storage.remove((int) existPosition);
    }

    @Override
    protected Object getExistPosition(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean elementExistInThisPosition(Object existPosition) {
        return (int) existPosition > -1;
    }
}
