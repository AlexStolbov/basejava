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
    protected void doUpdate(Integer existPosition, Resume resume) {
        storage.set(existPosition, resume);
    }

    @Override
    protected void doSave(Resume resume, Integer existPosition) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Integer existPosition) {
        return storage.get(existPosition);
    }

    @Override
    protected void doDelete(Integer existPosition) {
        storage.remove((int) existPosition);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer existPosition) {
        return (int) existPosition > -1;
    }
}
