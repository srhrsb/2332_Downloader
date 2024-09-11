package com.brh.downloader;

public interface Action<T,U> {
    void invoke(T value1 , U value2);

}



