package com.student.Foodly.util;

import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {

    // We keep data in the 'data' directory.
    @Value("${foodly.data.path:data/}")
    private String dataPath;

    public List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        File d = new File(dataPath);
        if (!d.exists()) {
            d.mkdirs(); // make folder if missing
        }

        File file = new File(dataPath, filename);
        if (!file.exists()) {
            return lines; // return empty list if no file
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            // read the file line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public void writeLines(String filename, List<String> lines) {
        // making sure folder exists
        File dir = new File(dataPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dataPath, filename);
        File tempFile = new File(dataPath, filename + ".tmp");

        System.out.println("FileUtil.writeLines: Writing " + lines.size() + " lines to " + file.getAbsolutePath());

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile, false), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("FileUtil.writeLines: IOException writing tmp file for " + filename);
            e.printStackTrace();
            return;
        }

        System.gc(); // clear garbage so windows can move the file

        try {
            java.nio.file.Files.move(tempFile.toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("FileUtil.writeLines: SUCCESS — " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("FileUtil.writeLines: FAILED to move temp file to original for " + filename);
        }
    }

    public void appendLine(String filename, String line) {
        // making sure folder exists before writing
        File dir = new File(dataPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dataPath, filename);
        System.out.println("FileUtil.appendLine: Writing to " + file.getAbsolutePath() + " | Data: " + line);

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.println(line);
            System.out.println("FileUtil.appendLine: SUCCESS — line written to " + filename);
        } catch (IOException e) {
            System.err.println("FileUtil.appendLine: FAILED writing to " + filename);
            e.printStackTrace();
        }
    }
}
