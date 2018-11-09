package com.example.mike.week3daily4.views.main_activity;

import com.example.mike.week3daily4.base.BasePresenter;
import com.example.mike.week3daily4.base.BaseView;

public class MainContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter{
        void toggleMusic();
    }

}
