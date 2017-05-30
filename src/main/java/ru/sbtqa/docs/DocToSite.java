package ru.sbtqa.docs;

import org.w3c.dom.stylesheets.LinkStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sbt-svetlakov-av on 15.05.17.
 */
public class DocToSite {

    private static void saveHtmlFiles(Map<String, String> results) throws IOException {
        String path = System.getProperty("user.dir") + "/docs";
        FileHelper.createDir(path);

        for (Map.Entry<String, String> html : results.entrySet()) {
            String filePath;
            if (html.getKey().contains("Home")) {
                filePath = path + "/index.html";
            } else {
                filePath = path + "/" + html.getKey().replace(".md", ".html");
            }

            FileHelper.saveFile(filePath, html.getValue());
        }
    }

    private static void extractFiles() throws IOException {
        String path = System.getProperty("user.dir") + "/docs";

        List<String> fileExtensions = new ArrayList<>();
        fileExtensions.add(".js");
        fileExtensions.add(".css");

        for(String extension: fileExtensions) {
            Map<String, String> files = FileHelper.readFiles(FileHelper.getFiles(extension));
            for (Map.Entry<String, String> file : files.entrySet()) {
                String filePath = path + "/" + file.getKey();
                FileHelper.saveFile(filePath, file.getValue());
            }
        }
    }

    public static void main(String[] args) throws IOException {

        try {
            Map<String, String> mds = FileHelper
                    .readFiles(FileHelper.getFiles(".md"));

            Map<String, String> result = HtmlBuilder.framingHtmls(Converter.markdownToHtml(mds),
                    HtmlBuilder.getSampleTextWithMenu(mds), "##ArticleBody");

            saveHtmlFiles(result);
            extractFiles();
            System.out.println("Docs is now in docs folder");

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
