package graphics;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private Map<String, Image> map;

    public ImageLoader(String fileName) {
        map = new HashMap<String, Image>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String next = in.readLine();
            do {
                String[] picInfo = next.split(" ");
                map.put(picInfo[0], Toolkit.getDefaultToolkit().getImage(picInfo[1]));
                next = in.readLine();
            } while (next != null);
        } catch (FileNotFoundException e) {
            System.out.println("File with pics doesn`t exist!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage(String img) {
        return map.get(img);
    }
}
