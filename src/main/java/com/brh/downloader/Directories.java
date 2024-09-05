package com.brh.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Directories {

    public static List<String> getAllFiles( String directoryUrl ){

        try {
            Document doc = Jsoup.connect(directoryUrl).get();

            Elements links = doc.select("a[href]");

            List<String> fileLinks = new ArrayList<>();

            for (Element link : links) {
                String href = link.attr("href");

                if (!href.equals("../")) {
                    String absoluteLink = directoryUrl + href;
                    fileLinks.add(absoluteLink);
                }
            }

            System.out.println("Files in the directory:");
            for (String fileLink : fileLinks) {
                System.out.println(fileLink);
            }

            return fileLinks;

        } catch (IOException e) {
            e.printStackTrace();
        }
      return null;
    }
}