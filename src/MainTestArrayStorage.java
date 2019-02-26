import com.amstolbov.model.Resume;
import com.amstolbov.storage.ArrayStorage;

/**
 * Test for your com.amstolbov.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final private ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        Resume r4 = new Resume("uuid2");
        ARRAY_STORAGE.update(r4);
        System.out.printf("Update uuid2: %s and %s\n", ARRAY_STORAGE.get(r4.getUuid()) != r2,
                            ARRAY_STORAGE.get(r4.getUuid()) == r4);

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        for (int i = 0; i < 10000; i++) {
            ARRAY_STORAGE.save(new Resume(Integer.toString(i)));
        }
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.save(new Resume("10001"));
        ARRAY_STORAGE.clear();
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
