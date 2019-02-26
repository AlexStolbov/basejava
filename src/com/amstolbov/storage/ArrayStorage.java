package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int lastIndex = -1;

    public void clear() {
        Arrays.fill(storage, 0, lastIndex, null);
        lastIndex = -1;
    }

    public void save(Resume r) {
        if (lastIndex == storage.length - 1) {
            System.out.println("ERROR save: storage is full");
            return;
        }
        if (r.getUuid() == null) {
            System.out.println("ERROR save: uuid is null");
            return;
        }
        if (getIndex(r.getUuid()) < 0) {
            lastIndex++;
            storage[lastIndex] = r;
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
            System.arraycopy(storage, findIndex + 1, storage, findIndex, lastIndex - findIndex);
            storage[lastIndex] = null;
            lastIndex--;
        } else {
            System.out.println("ERROR delete: uuid - %s not find in storage");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, lastIndex + 1);
    }

    public int size() {
        return lastIndex + 1;
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
        for (int i = 0; i <= lastIndex; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
