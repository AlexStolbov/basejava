/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.uuid = "uuid1";
        Resume r2 = new Resume();
        r2.uuid = "uuid2";
        r2.setName("Resume 2");
        Resume r3 = new Resume();
        r3.uuid = "uuid3";

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.uuid));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        System.out.printf("Name r2: %s \n", ARRAY_STORAGE.get(r2.uuid).getName());
        r2.setName("Resume 2. v2.");
        ARRAY_STORAGE.update(r2);
        System.out.printf("New name r2: %s \n", ARRAY_STORAGE.get(r2.uuid).getName());

        printAll();
        ARRAY_STORAGE.delete(r1.uuid);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        for (int i = 0; i < 10000; i++) {
            ARRAY_STORAGE.save(new Resume(Integer.toString(i)));
        }
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.save(new Resume("10001"));

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
