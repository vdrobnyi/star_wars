package logic;

public class IdGenerator {
    private static int last_id = 0;
    public static int getNewId() {
        return ++last_id;
    }
}
