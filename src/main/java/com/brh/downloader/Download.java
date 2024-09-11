package com.brh.downloader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;


public class Download implements Runnable {

    private String link;
    private String target;
    private File outputFile;
    private int downloadIndex;
    private Action<Integer, Integer> progressCallback;

    public Download(String link, String target, int downloadIndex, Action<Integer, Integer> progressCallback) {
        this.link = link;
        this.target = target;
        this.downloadIndex = downloadIndex;
        this.progressCallback = progressCallback;
    }

    public void run(){

        try{
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //öffnen der Verbindung

            //Input Stream
            BufferedInputStream buffInputStream = new BufferedInputStream( connection.getInputStream() );

            File file =  new File(link); //File Objekt um Dateinamen des Links zu ermitteln
            //Ausgabefile mit dem Target-Pfad und übernommenem Dateinamen
            outputFile = new File(target, file.getName());

            //Outputstream mit Ziel
            OutputStream outputStream = new FileOutputStream(outputFile);
            BufferedOutputStream buffOutputStream = new BufferedOutputStream( outputStream , 1024);

            byte[] buffer = new byte[1024]; //Array das Daten zwischenspeicher
            int downloaded = 0; //für Anzeigezwecke des Fortschritts
            int readByte = 0; //wieviel wurde schon geladen

            //solange Daten gelesen werden
            while((readByte = buffInputStream.read(buffer, 0, 1024)) >= 0){
                buffOutputStream.write(buffer, 0, readByte);
                downloaded += readByte;

                System.out.println("Runtergeladen: "+downloaded);


                progressCallback.invoke( downloadIndex, downloaded);
            }

            buffOutputStream.close();
            buffInputStream.close();
            System.out.println("Download erfolgreich");

        }
        catch( IOException e){
            throw new RuntimeException(e);
        }
        finally{

        }
    }
}
