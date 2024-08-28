package com.brh.downloader;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Controller{
    @FXML
    private TextField targetFolder;

    @FXML
    private TextField linkTextfield;

    @FXML
    private TableView<DownloadItem> tableView;

    @FXML
    private TableColumn<DownloadItem, String> columnUrls;

    @FXML
    private TableColumn<DownloadItem, Integer> columnBytes;

    private final ObservableList<DownloadItem> downloadItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnUrls.setCellValueFactory(new PropertyValueFactory<DownloadItem,String>("Link"));
        columnBytes.setCellValueFactory(new PropertyValueFactory<DownloadItem,Integer>("DownloadedBytes"));
    }

    @FXML
    public void addLink() {

        String link = linkTextfield.getText();

        if(link.isEmpty()) return;

        downloadItems.add(new DownloadItem(link, 0));
        linkTextfield.setText("");

        tableView.setItems( downloadItems );
    }
    @FXML
    protected void searchFolder() {

        Stage stage  = (Stage) targetFolder.getScene().getWindow(); //Stage holen
        DirectoryChooser directoryChooser = new DirectoryChooser(); //File Chooser Objekt erstellen
        File selected = directoryChooser.showDialog( stage); //FileChooser öffnen
        targetFolder.setText(selected.getAbsolutePath()); //gewählten Pfad an das Textfeld übergeben
    }

    @FXML
    protected void clearAllDownloads(){
        downloadItems.clear();
        tableView.setItems( downloadItems );
    }

    @FXML
    protected void deleteDownload(){
        var focus = tableView.getFocusModel();
        int index = focus.focusedIndexProperty().getValue();
        downloadItems.remove(index);
        tableView.setItems( downloadItems );
    }

    @FXML
    protected void download(){
        String target = targetFolder.getText();

         if(!target.isEmpty())   {
             for( var item : downloadItems){
                 new Thread( new Download( item.getLink(), target) ).start();
             }
         }
    }
}