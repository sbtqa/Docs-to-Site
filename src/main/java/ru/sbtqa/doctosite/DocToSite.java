package ru.sbtqa.doctosite;

import java.io.IOException;
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

    public static void main(String[] args) throws IOException {

        try {
            Map<String, String> mds = FileHelper
                    .readFiles(FileHelper.getFiles(".md"));

            Map<String, String> result = HtmlBuilder.framingHtmls(Converter.markdownToHtml(mds),
                    HtmlBuilder.getSampleTextWithMenu(mds), "##ArticleBody");

            saveHtmlFiles(result);
            System.out.println("Docs is now in docs folder");

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
