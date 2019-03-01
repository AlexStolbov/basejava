package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int findIndex = getIndex(resume.getUuid());
        if (findIndex == -1) {
            System.out.printf("ERROR update: uuid - %s not find in storage", resume.getUuid());
        } else {
            storage[findIndex] = resume;
        }
    }

    public void save(Resume resume) {
        int findIndex = getIndex(resume.getUuid());
        if (findIndex > 0) {
            System.out.println("ERROR save: uuid is present in storage");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("ERROR save: storage is full");
        } else {
            int saveIndex = getSaveIndex(findIndex);
            storage[saveIndex] = resume;
            size++;
        }
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.printf("Resume %s not exist \n", uuid);
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex == -1) {
            System.out.println("ERROR delete: uuid - %s not find in storage");
        } else {
            deleteElement(findIndex);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract int getSaveIndex(int findIndex);

    protected abstract void deleteElement(int findIndex);
}
