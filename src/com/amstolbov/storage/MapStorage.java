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
    protected void updateElement(ExistPosition existPosition, Resume resume) {
        storage.put(existPosition.strPos, resume);
    }

    @Override
    protected void saveElement(Resume resume, ExistPosition existPosition) {
        storage.put(existPosition.strPos, resume);
    }

    @Override
    protected Resume getElement(ExistPosition existPosition) {
        return storage.get(existPosition.strPos);
    }

    @Override
    protected void deleteElement(ExistPosition existElement) {
        storage.remove(existElement.strPos);
    }

    @Override
    protected ExistPosition getExistPosition(String uuid) {
        ExistPosition result = new ExistPosition(false, 0, uuid);
        if (storage.containsKey(uuid)) {
            result.strPos = uuid;
            result.exist = true;
        }
        return result;
    }
}
