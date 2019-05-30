package com.amstolbov.storage;

import com.amstolbov.Config;
import com.amstolbov.ResumeTestData;
import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;
import com.amstolbov.model.SectionType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final SectionType[] fillSections = ResumeTestData.ALL_SECTIONS;

    private static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1,
            "full name 1",
            ResumeTestData.ALL_CONTACTS,
            fillSections);
    private static final Resume RESUME_2 = ResumeTestData.getResume(UUID_2,
            "full name 2",
            ResumeTestData.ALL_CONTACTS,
            fillSections);
    private static final Resume RESUME_3 = ResumeTestData.getResume(UUID_3,
            "full name 3",
            ResumeTestData.ALL_CONTACTS,
            fillSections);
    private static final Resume RESUME_4 = ResumeTestData.getResume(UUID_4,
            "full name 4",
            ResumeTestData.WITHOUT_ALL_CONTACTS,
            fillSections);

    protected static final String STORAGE_PATH = Config.get().getParam(Config.ParamType.STORAGE_DIR);
    protected static final File STORAGE_DIR = new File(STORAGE_PATH);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setup() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume updatedResume = ResumeTestData.getResume(UUID_2,
                "full name 5",
                ResumeTestData.ONLY_PHONE,
                fillSections);
        assertNotSame(updatedResume, storage.get(UUID_2));
        storage.update(updatedResume);
        assertThat(storage.get(UUID_2), is(updatedResume));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
        Resume rr = storage.get(UUID_4);
        assertEquals(RESUME_4, rr);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int sizeAfterDelete = storage.size() - 1;
        storage.delete(UUID_1);
        assertEquals(sizeAfterDelete, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> storageAll = storage.getAllSorted();
        assertThat(storageAll, is(expected));
    }

}