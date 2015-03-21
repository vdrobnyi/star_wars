package logic;

import java.util.ArrayList;
import java.util.List;

public class IdGenerator {
    private static Integer last_id = 0;
    private static List<Integer> used = new ArrayList<Integer>();
    public static int getNewId() {
        while (used.contains(++last_id)) {
        }
        return last_id;
    }

    public static void idInUse(int id) {
        used.add(id);
    }
}
