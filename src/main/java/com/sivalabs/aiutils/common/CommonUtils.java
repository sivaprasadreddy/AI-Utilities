package com.sivalabs.aiutils.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class CommonUtils {

    public static void writeTextToFile(String textData, File file) {
        try {
            FileUtils.write(file, textData, StandardCharsets.UTF_8);
            System.out.printf("Saved %s to %s%n", file.getName(), file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Error writing text to file", e);
        }
    }

    public static String writeImageToFile(String imageData) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = String.format("image_%s.png", timestamp);
        Path filePath = new File(fileName).toPath();
        try {
            byte[] bytes = Base64.getDecoder().decode(imageData);
            FileUtils.writeByteArrayToFile(filePath.toFile(), bytes);
            System.out.printf("Saved %s", fileName);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Error writing prompt to file", e);
        }
    }
}
