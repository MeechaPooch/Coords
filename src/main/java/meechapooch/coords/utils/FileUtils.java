package meechapooch.coords.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    public static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public static String writeString(String path, String content) throws IOException {
        Path fileName = new File(path).toPath();
        Files.write(fileName, content.getBytes(StandardCharsets.UTF_8));

        List<String> actual = Files.readAllLines(fileName);
        String ret = "";
        for (String s : actual) {
            ret += s;
        }
        return ret;
    }
}
