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

    void clear() {
        Arrays.fill(storage, 0, lastIndex, null);
        lastIndex = -1;
    }

    /**
     * Adds the specified item to the repository..
     * @param r resume for add.
     */
    void save(Resume r) {
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
    Resume get(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            return storage[findIndex];
        }
        return null;
    }

    /**
     * Removes the element with the given uuid, if it exists. And compresses the storage.
     * @param uuid - uuid of the item to remove.
     */
    void delete(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            System.arraycopy(storage, findIndex + 1, storage, findIndex, lastIndex - findIndex);
            storage[lastIndex] = null;
            lastIndex--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, lastIndex + 1);
    }

    /**
     * The lastIndex of the storage without empty items.
     * @return lastIndex
     */
    int size() {
        return lastIndex + 1;
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
