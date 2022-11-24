package com.example.movieapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecomendMain {
    private ArrayList<String> myList;
    private List<List<String>> dataList;
    private ArrayList<String> resultList = new ArrayList<>();

    public RecomendMain(List<List<String>> mDataList,  ArrayList<String> mMyList) {
        this.dataList = mDataList;
        this.myList = mMyList;
    }

    public ArrayList<String> recomendFunc() {
        for (int i=0; i<dataList.size(); i++) {
            int cnt = 0;

            if (dataList.get(i).get(1).equals(myList.get(0))) { cnt++; }
            if (dataList.get(i).get(2).equals(myList.get(1))) { cnt++; }
            if (dataList.get(i).get(3).equals(myList.get(2))) { cnt++; }
            if (dataList.get(i).get(4).equals(myList.get(3))) { cnt++; }

            if (cnt >= 3) {
                resultList.add(dataList.get(i).get(0));
            }
        }

        return resultList;
    }
}
