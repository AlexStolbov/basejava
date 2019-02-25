/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    String uuid;
    private String name;

    public Resume() {

    }

    public Resume(String newUUID) {
        uuid = newUUID;
    }

    @Override
    public String toString() {
        return uuid;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName(){
        return name;
    }
}
