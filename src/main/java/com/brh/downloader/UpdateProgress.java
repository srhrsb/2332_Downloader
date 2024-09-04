package com.brh.downloader;

public class UpdateProgress {

    private int updateIndex;

    private int bytes;

    public UpdateProgress(int updateIndex, int bytes) {
        this.updateIndex = updateIndex;
        this.bytes = bytes;
    }

    public int getUpdateIndex() {
        return updateIndex;
    }

    public void setUpdateIndex(int updateIndex) {
        this.updateIndex = updateIndex;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }
}
