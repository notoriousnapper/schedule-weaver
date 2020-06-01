package com.jesse.personal.scheduleweaver.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {

    private void testPathFilePrint() {

        String path = "src/main/resources/action";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        System.out.println("test absolute:" + absolutePath);

        try (Stream<Path> paths = Files.walk(Paths.get(absolutePath))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(
                            System.out::println
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
