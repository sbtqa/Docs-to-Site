package ru.sbtqa.doctosite;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbt-svetlakov-av on 18.05.17.
 */
public class HtmlBuilder {

    public static Map<String, String> framingHtmls(Map<String, String> htmls, String sample, String changeText) {
        Map<String, String> result = new HashMap();

        for (Map.Entry<String, String> html : htmls.entrySet()) {
            String framingText = sample.replace(changeText, html.getValue());

            result.put(html.getKey(), framingText);
        }
        return result;
    }

    public static String getSampleTextWithMenu(Map<String, String> mds) throws IOException, URISyntaxException {
        String sidebar = Converter.markdownToHtml(FileHelper.readSidebarFile());

        sidebar = sidebar.replace("https://github.com/sbtqa/docs/wiki/", "");
        for (Map.Entry<String, String> md : mds.entrySet()) {
            sidebar = sidebar.replace("\"" + md.getKey().replace(".md", ""),
                    "\"" + md.getKey().replace(".md", ".html"));
        }

        String sample = FileHelper.readFile(
                FileHelper.getFiles("index.html").get(0));
        sample = sample.replace("##Sidebar", sidebar);

        return sample;
    }
}
