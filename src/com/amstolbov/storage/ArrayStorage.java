package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("ERROR save: storage is full");
            return;
        }
        if (resume.getUuid() == null) {
            System.out.println("ERROR save: uuid is null");
            return;
        }
        int findIndex = getIndex(resume.getUuid());
        if (findIndex > -1) {
            System.out.println("ERROR save: uuid is present in storage");
            return;
        }
        size++;
        storage[size - 1] = resume;
    }

    public Resume get(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            return storage[findIndex];
        } else {
            System.out.printf("ERROR get: uuid - %s not in storage \n", uuid);
            return null;
        }
    }

    public void delete(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            storage[findIndex] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR delete: uuid - %s not find in storage");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int findIndex = getIndex(resume.getUuid());
        if (findIndex > -1) {
            storage[findIndex] = resume;
        } else {
            System.out.printf("ERROR update: uuid - %s not find in storage", resume.getUuid());
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
