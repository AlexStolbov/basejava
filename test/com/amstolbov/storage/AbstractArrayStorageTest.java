package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final Resume resumeOne = new Resume("uuid1");
    private static final Resume resumeTwo = new Resume("uuid2");
    private static final Resume resumeThree = new Resume("uuid3");
    private static final Resume resumeFour = new Resume("uuid4");

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setup() {
        storage.clear();
        storage.save(resumeOne);
        storage.save(resumeTwo);
        storage.save(resumeThree);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume(resumeTwo.getUuid());
        assertNotSame(updatedResume, storage.get(updatedResume.getUuid()));
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get(updatedResume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resumeFour);
    }

    @Test
    public void save() {
        String newUuid = "uuid4";
        Resume newResume = new Resume(newUuid);
        storage.save(newResume);
        assertEquals(4, storage.size());
        assertTrue(newResume == storage.get(newUuid));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resumeOne);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            int storageLimit = storage.getStorageLimit();
            for (int i = 0; i < storageLimit; i++) {
                storage.save(new Resume(String.valueOf(i)));
            }
        } catch (StorageException strExc) {
            Assert.fail("Error overflow");
        }
        storage.save(resumeFour);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(resumeOne, storage.get(resumeOne.getUuid()));
        assertEquals(resumeTwo, storage.get(resumeTwo.getUuid()));
        assertEquals(resumeThree, storage.get(resumeThree.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int sizeAfterDelete = storage.size() - 1;
        storage.delete(resumeOne.getUuid());
        assertEquals(sizeAfterDelete, storage.size());
        storage.get(resumeOne.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] expected = {resumeOne, resumeTwo, resumeThree};
        Resume[] storageAll = storage.getAll();
        assertArrayEquals(expected, storageAll);
    }

}