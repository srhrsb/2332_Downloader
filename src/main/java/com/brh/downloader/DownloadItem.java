package com.brh.downloader;

public class DownloadItem {

    private String link;
    private int downloadedBytes;

    public DownloadItem(String url, int downloadedBytes) {
        this.link = url;
        this.downloadedBytes = downloadedBytes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDownloadedBytes() {
        return downloadedBytes;
    }

    public void setDownloadedBytes(int downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }
}
