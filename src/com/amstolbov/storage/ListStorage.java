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
    protected void updateElement(ExistPosition existPosition, Resume resume) {
        storage.set(existPosition.intPos, resume);
    }

    @Override
    protected void saveElement(Resume resume, ExistPosition existPosition) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(ExistPosition existPosition) {
        return storage.get(existPosition.intPos);
    }

    @Override
    protected void deleteElement(ExistPosition existElement) {
        storage.remove(existElement.intPos);
    }

    @Override
    protected ExistPosition getExistPosition(String uuid) {
        int result = -1;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                result = i;
                break;
            }
        }
        return new ExistPosition((result >= 0), result, "");
    }
}
