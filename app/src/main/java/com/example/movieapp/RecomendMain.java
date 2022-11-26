package com.example.movieapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecomendMain {
    private ArrayList<String> myList;
    private ArrayList<String> dataList;
    private ArrayList<String> resultList = new ArrayList<>();

    public RecomendMain(ArrayList<String> mDataList,  ArrayList<String> mMyList) {
        this.dataList = mDataList;
        this.myList = mMyList;
    }

    public ArrayList<String> recomendFunc() {
        for (int i=0; i<dataList.size(); i++) {
            int cnt = 0;

            String str = dataList.get(i);
            List<String> tmpList = Arrays.asList(str.split(","));

            if (tmpList.get(1).equals(myList.get(0))) { cnt++; }
            if (tmpList.get(2).equals(myList.get(1))) { cnt++; }
            if (tmpList.get(3).equals(myList.get(2))) { cnt++; }
            if (tmpList.get(4).equals(myList.get(3))) { cnt++; }

            if (cnt >= 3) {
                resultList.add(tmpList.get(0));
            }
        }

        return resultList;
    }
}
