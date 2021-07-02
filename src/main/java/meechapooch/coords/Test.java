package meechapooch.coords;

import meechapooch.coords.utils.FileUtils;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            FileUtils.writeString("poop.txt","poop and pee\n POOPEEEE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
