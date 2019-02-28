package com.amstolbov.storage;

import com.amstolbov.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage{

    public void update(Resume resume) {
        int findIndex = getIndex(resume.getUuid());
        if (findIndex == -1) {
            System.out.printf("ERROR update: uuid - %s not find in storage", resume.getUuid());
        } else {
            storage[findIndex] = resume;
        }
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("ERROR save: uuid is present in storage");
            return;
        } else if (size >= STORAGE_LIMIT){
            System.out.println("ERROR save: storage is full");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void delete(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex == -1) {
            System.out.println("ERROR delete: uuid - %s not find in storage");
        } else {
            storage[findIndex] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
