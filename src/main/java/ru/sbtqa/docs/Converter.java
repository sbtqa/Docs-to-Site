package ru.sbtqa.docs;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbt-svetlakov-av on 17.05.17.
 */
public class Converter {

    public static Map<String, String> markdownToHtml(Map<String, String> mds) {
        Map<String, String> results = new HashMap();

        if (mds.size() == 0) {
            throw new ArrayIndexOutOfBoundsException("Markdown size is 0");
        }

        for (Map.Entry<String, String> md : mds.entrySet()) {

            results.put(md.getKey().replace(".md", ".html"), markdownToHtml(md.getValue()));
        }

        return results;
    }

    public static String markdownToHtml(String md) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        Node document = parser.parse(md);
        return renderer.render(document);
    }
}
