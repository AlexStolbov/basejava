package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setup() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        String updateUuid = UUID_2;
        Resume updateResume = new Resume(updateUuid);
        assert(updateResume != storage.get(updateUuid));
        storage.update(updateResume);
        assert(updateResume == storage.get(updateUuid));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("not_exist"));
    }

    @Test
    public void save() {
        String newUuid = "uuid4";
        Resume newResume = new Resume(newUuid);
        storage.save(newResume);
        assertEquals(4, storage.size());
        assert(newResume == storage.get(newUuid));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume resume = new Resume(UUID_2);
        storage.save(resume);
    }

    @Test
    public void saveOverflow() {
        for (int i = 4; i <= 10000; i++) {
            storage.save(new Resume(String.valueOf(i)));
        }
        try {
            storage.save(new Resume("overflow"));
            Assert.fail("Expected StorageException");
        } catch(StorageException strExc) {
            assertEquals("Storage overflow", strExc.getMessage());
        }
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        String getUuid = UUID_1;
        assertEquals(new Resume(getUuid), storage.get(getUuid));
        getUuid = UUID_2;
        assertEquals(new Resume(getUuid), storage.get(getUuid));
        getUuid = UUID_3;
        assertEquals(new Resume(getUuid), storage.get(getUuid));
    }

    @Test(expected  = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        int currentSize = storage.size();
        storage.delete(UUID_1);
        currentSize--;
        assertEquals(currentSize, storage.size());
        storage.delete(UUID_2);
        currentSize--;
        assertEquals(currentSize, storage.size());
        storage.delete(UUID_3);
        currentSize--;
        assertEquals(currentSize, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] storageAll = storage.getAll();
        assertEquals(3, storageAll.length);
        assert(Arrays.asList(storageAll).indexOf(new Resume(UUID_1)) > -1);
        assert(Arrays.asList(storageAll).indexOf(new Resume(UUID_2)) > -1);
        assert(Arrays.asList(storageAll).indexOf(new Resume(UUID_3)) > -1);
    }

}