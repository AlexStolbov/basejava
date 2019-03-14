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
    protected void updateElement(String existPosition, Resume resume) {
        storage.set(convertIndexFrom(existPosition), resume);
    }

    @Override
    protected void saveElement(Resume resume, String existPosition) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(String existPosition) {
        return storage.get(convertIndexFrom(existPosition));
    }

    @Override
    protected void deleteElement(String existElement) {
        storage.remove(convertIndexFrom(existElement));
    }

    @Override
    protected String getExistPosition(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return String.valueOf(i);
            }
        }
        return NOT_EXIST_INDEX_COLLECTION;
    }

    private int convertIndexFrom(String index) {
        return Integer.valueOf(index);
    }

}
