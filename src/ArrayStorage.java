import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(this.storage, null);
    }

    /**
     * Adds the specified item to the repository..
     * @param r resume for add.
     */
    void save(Resume r) {
        if (get(r.uuid) == null) {
            this.storage[size()] = r;
        }
    }

    /**
     * Returns an item by uuid.
     * @param uuid search identifier.
     * @return resume or null
     */
    Resume get(String uuid) {
        Resume result = null;
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            result = this.storage[findIndex];
        }
        return result;
    }

    /**
     * Removes the element with the given uuid, if it exists. And compresses the storage.
     * @param uuid - uuid of the item to remove.
     */
    void delete(String uuid) {
        int findIndex = getIndex(uuid);
        if (findIndex > -1) {
            int size = size();
            this.storage[findIndex] = null;

            System.arraycopy(this.storage, findIndex + 1, this.storage, findIndex, size - findIndex);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(this.storage).limit(size()).toArray(Resume[]::new);
    }

    /**
     * The size of the storage without empty items.
     * @return size
     */
    int size() {
        int result = 0;
        for (int i = 0; i < this.storage.length; i++) {
            if (this.storage[i] == null) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Returns the index of the item with the given uuid or -1 if not found.
     * @param uuid - find value
     * @return index of uuid
     */
    private int getIndex(String uuid) {
        int result = -1;
        for (int i = 0; i < size(); i++) {
            if (this.storage[i].uuid.equals(uuid)) {
                result = i;
                break;
            }
        }
        return result;
    }
}
