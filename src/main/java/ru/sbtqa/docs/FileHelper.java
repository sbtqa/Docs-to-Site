package ru.sbtqa.docs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by sbt-svetlakov-av on 17.05.17.
 */
public class FileHelper {

    /**
     * Read file
     *
     * @param path
     * @return file content
     * @throws IOException
     */
    public static String readFile(String path) throws IOException {

        InputStream inputStream = DocToSite.class.getClassLoader()
                .getResourceAsStream(path);
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Return map where key is path, value is file content
     *
     * @param files
     * @return map where key is path, value is file content
     */
    public static Map<String, String> readFiles(List<String> files) {

        Map<String, String> results = new HashMap();

        for (String path : files) {
            try {
                results.put(path, readFile(path));
            } catch (IOException er) {
                er.printStackTrace();
            }
        }

        return results;
    }

    public static void saveFile(String filePath, String content) throws IOException {
        BufferedWriter writer = null;
        try {
            File logFile = new File(filePath);
            logFile.createNewFile();

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(content);
        } finally {
            writer.close();
        }
    }


    public static List<String> getFiles(String regex) throws IOException {
        List<String> results = new ArrayList();

        CodeSource src = DocToSite.class.getProtectionDomain().getCodeSource();
        if (src != null) {
            URL jar = src.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            while (true) {
                ZipEntry e = zip.getNextEntry();
                if (e == null)
                    break;
                String name = e.getName();
                if (name.contains(regex)) {
                    results.add(name);
                }
            }
        }

        return results;
    }

    public static String readSidebarFile() throws IOException, URISyntaxException {
        List<String> files = getFiles("_Sidebar.md");

        if (files.size() > 1) {
            throw new ArrayStoreException("Sidebar.md should be only one");
        }

        return readFile(files.get(0));
    }

    public static void createDir(String path) {
        new File(path).mkdir();
    }
}
