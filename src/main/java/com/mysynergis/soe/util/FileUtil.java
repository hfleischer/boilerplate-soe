package com.mysynergis.soe.util;

// --------------------------------------------------------------
//
// IMPORTS
//
// --------------------------------------------------------------

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Helper class - file utilities<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class FileUtil {

    private FileUtil() {
        // no public instance
    }

    public static BufferedWriter createWriter(File file) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
    }

    public static BufferedReader createReader(File file) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
    }

    /**
     * Getting a file
     *
     * @param filePath Path to file
     * @return
     */
    private static File getFile(String filePath) {
        return new File(filePath);
    }

    public static String readFileContent(String sourcePath) throws IOException {

        File source = getFile(sourcePath);
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader sourceReader = createReader(source)) {
            String line;
            while ((line = sourceReader.readLine()) != null) {
                contentBuilder.append(String.format("%s%n", line));
            }
        }
        return contentBuilder.toString();

    }

}
