import com.amstolbov.model.Resume;
import com.amstolbov.storage.AbstractArrayStorage;
import com.amstolbov.storage.SortedArrayStorage;

/**
 * Test for your com.amstolbov.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    //static final private ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static final private AbstractArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid0");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        Resume r5 = new Resume();
        r5.setUuid("uuid2");
        ARRAY_STORAGE.update(r5);
        System.out.printf("Update uuid2: %s and %s\n", ARRAY_STORAGE.get(r5.getUuid()) != r2,
                            ARRAY_STORAGE.get(r5.getUuid()) == r5);

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.delete(r3.getUuid());
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        String lastUUID = "";
        for (int i = 0; i < 10000; i++) {
            Resume rTemp = new Resume();
            rTemp.setUuid(Integer.toString(i));
            ARRAY_STORAGE.save(rTemp);
            lastUUID = rTemp.getUuid();
        }
        System.out.println("Size: " + ARRAY_STORAGE.size());
        Resume rOver = new Resume();
        rOver.setUuid("10001");
        ARRAY_STORAGE.save(rOver);
        ARRAY_STORAGE.delete(lastUUID);
        ARRAY_STORAGE.clear();
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
