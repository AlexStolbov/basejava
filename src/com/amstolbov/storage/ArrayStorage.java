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

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("ERROR save: storage is full");
            return;
        }
        if (r.getUuid() == null) {
            System.out.println("ERROR save: uuid is null");
            return;
        }
        if (getIndex(r.getUuid()) < 0) {
            size++;
            storage[size - 1] = r;
        }
    }

    public Resume get(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            return storage[findIndex];
        } else {
            System.out.printf("ERROR get: uuid - %s not in storage \n", uuid);
        }
        return null;
    }

    public void delete(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            System.arraycopy(storage, findIndex + 1, storage, findIndex, size - findIndex);
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

    public void update(Resume r) {
        int findIndex = getIndex(r.getUuid());
        if (findIndex > -1) {
            storage[findIndex] = r;
        } else {
            System.out.printf("ERROR update: uuid - %s not find in storage", r.getUuid());
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
