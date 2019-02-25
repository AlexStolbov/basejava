import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    /**
     * Index of last resume.
     */
    private int lastIndex = -1;

    public void clear() {
        Arrays.fill(storage, 0, lastIndex, null);
        lastIndex = -1;
    }

    /**
     * Adds the specified item to the repository.
     * Check that repository is full.
     * Verifies that uuid is corrert.
     * @param r resume for add.
     */
    public void save(Resume r) {
        if (lastIndex == storage.length - 1) {
            System.out.println("ERROR save: storage is full");
            return;
        }
        if (r.uuid == null) {
            System.out.println("ERROR save: uuid is null");
            return;
        }
        if (getIndex(r.uuid) < 0) {
            lastIndex++;
            storage[lastIndex] = r;
        }
    }

    /**
     * Returns an item by uuid.
     * @param uuid search identifier.
     * @return resume or null
     */
    public Resume get(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            return storage[findIndex];
        } else {
            System.out.printf("ERROR get: uuid - %s not in storage \n", uuid);
        }
        return null;
    }

    /**
     * Removes the element with the given uuid, if it exists. And compresses the storage.
     * @param uuid - uuid of the item to remove.
     */
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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, lastIndex + 1);
    }

    /**
     * The lastIndex of the storage without empty items.
     * @return lastIndex
     */
    public int size() {
        return lastIndex + 1;
    }

    public void update(Resume r) {
        int findIndex = getIndex(r.uuid);
        if (findIndex > -1) {
            storage[findIndex] = r;
        } else {
            System.out.printf("ERROR update: uuid - %s not find in storage", r.uuid);
        }
    }

    /**
     * Returns the index of the item with the given uuid or -1 if not found.
     * @param uuid - find value
     * @return index of uuid
     */
    private int getIndex(String uuid) {
        for (int i = 0; i <= lastIndex; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
