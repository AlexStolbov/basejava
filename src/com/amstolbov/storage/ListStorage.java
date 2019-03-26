package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected void updateElement(Integer existPosition, Resume resume) {
        storage.set(existPosition, resume);
    }

    @Override
    protected void saveElement(Resume resume, Integer existPosition) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Integer existPosition) {
        return storage.get(existPosition);
    }

    @Override
    protected void deleteElement(Integer existPosition) {
        storage.remove((int) existPosition);
    }

    @Override
    protected Integer getExistPosition(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean elementExistInThisPosition(Integer existPosition) {
        return (int) existPosition > -1;
    }
}
