package com.example.mike.week3daily4.base;

public interface BasePresenter<V extends BaseView> {
    void attachView( V view );
    void detachView();
}
