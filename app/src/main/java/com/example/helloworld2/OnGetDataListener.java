package com.example.helloworld2;

public interface OnGetDataListener<T> {
    void onSuccess(T dataResponse);
    void onFailure();
}
