package com.brh.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Directories {

    public static List<String> getAllFiles( String directoryUrl ){

        try {

            URL url = new URL(directoryUrl);
            String domain = url.getProtocol()+"://"+url.getHost();

            Document doc = Jsoup.connect(directoryUrl).get();

            Elements links = doc.select("a[href]");

            List<String> fileLinks = new ArrayList<>();

            for (Element link : links) {
                String href = link.attr("href");

                if (!href.equals("../") && !href.contains("?")) {

                    if(!href.contains( "://" )){
                        fileLinks.add( domain+"/"+href );
                    }
                    else{
                        fileLinks.add(href);
                    }
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