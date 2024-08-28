package com.brh.downloader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    private TextField targetFolder;

    @FXML
    protected void searchFolder() {

        Stage stage  = (Stage) targetFolder.getScene().getWindow(); //Stage holen
        DirectoryChooser directoryChooser = new DirectoryChooser(); //File Chooser Objekt erstellen
        File selected = directoryChooser.showDialog( stage); //FileChooser öffnen
        targetFolder.setText(selected.getAbsolutePath()); //gewählten Pfad an das Textfeld übergeben
    }

    @FXML
    protected void download() {
        String link = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Blume_mit_Schmetterling_und_Biene_1uf.JPG/440px-Blume_mit_Schmetterling_und_Biene_1uf.JPG";
        String link2 = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/Blumenwiese2.jpg/440px-Blumenwiese2.jpg";

        String target = targetFolder.getText();

         if(!link.isEmpty() && !target.isEmpty())   {
             new Thread( new Download( link, target) ).start();
             new Thread( new Download( link2, target) ).start();
         }
    }
}