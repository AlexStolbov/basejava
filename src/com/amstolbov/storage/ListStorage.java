package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new LinkedList<>();

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
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
    protected void updateElement(int existPosition, Resume resume) {
        storage.set(existPosition, resume);
    }

    @Override
    protected void saveElement(Resume resume, int existPosition) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(int existPosition) {
        return storage.get(existPosition);
    }

    @Override
    protected void deleteElement(int existElement) {
        storage.remove(existElement);
    }

    @Override
    protected int getExistPosition(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
