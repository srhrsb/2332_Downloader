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


    /**
     * Initialisierung bei Instanzierung dieses Objekts
     * es wird der Bezug der Spalten des TableView zur Modellklasse
     * DownloadItem hergestellt
     */
    @FXML
    public void initialize() {
        columnUrls.setCellValueFactory(new PropertyValueFactory<DownloadItem,String>("Link"));
        columnBytes.setCellValueFactory(new PropertyValueFactory<DownloadItem,Integer>("DownloadedBytes"));
    }

    /**
     * Link wird hinzugefügt zur Liste DownloadItems und an TableView zur Darstellung übergeben
     */
    @FXML
    public void addLink() {

        String link = linkTextfield.getText();

        if(link.isEmpty()) return;

        downloadItems.add(new DownloadItem(link, 0));
        linkTextfield.setText("");

        tableView.setItems( downloadItems );
    }

    /**
     * Suche nach dem Zielordner für die Downloads
     */
    @FXML
    protected void searchFolder() {
        Stage stage  = (Stage) targetFolder.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory( new File(System.getProperty("user.home")) );
        File selected = directoryChooser.showDialog( stage);
        targetFolder.setText(selected.getAbsolutePath());
    }

    /**
     * Alle Downloads löschen
     */
    @FXML
    protected void clearAllDownloads(){
        downloadItems.clear();
        tableView.setItems( downloadItems );
    }

    /**
     * Download der sich im Focus befindet
     */
    @FXML
    protected void deleteDownload(){
        var focus = tableView.getFocusModel();
        int index = focus.focusedIndexProperty().getValue();
        downloadItems.remove(index);
        tableView.setItems( downloadItems );
    }


    /**
     * Starten der Downloads, welche in der Liste eingetragen sind
     */
    @FXML
    protected void download(){
        String target = targetFolder.getText();

         if(!target.isEmpty())   {
             for( var item : downloadItems){
                 //nebenläufiger Prozess der Objecte vom Typen "Runable" ausführt
                 new Thread( new Download( item.getLink(), target) ).start();
             }
         }
    }
}