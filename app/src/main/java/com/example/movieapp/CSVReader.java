package com.example.movieapp;

import java.io.*;
import java.util.*;

public class CSVReader {
    private ArrayList<String> myList;
    public  ArrayList<String> resultList = new ArrayList<>();

    public CSVReader (ArrayList<String> mMyList) {
        this.myList = mMyList;
        readCSV();
    }

    public void readCSV() {
        List<List<String>> csvList = new ArrayList<List<String>>();
        File csv = new File("C:\\Users\\82104\\Desktop\\모바일프로그래밍\\data1.csv");
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                List<String> aLine = new ArrayList<String>();
                String[] lineArr = line.split(",");
                aLine = Arrays.asList(lineArr);
                csvList.add(aLine);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i=1; i<csvList.size(); i++) {
            int cnt = 0;

            if (csvList.get(i).get(1).equals(myList.get(0))) { cnt++; }
            if (csvList.get(i).get(2).equals(myList.get(1))) { cnt++; }
            if (csvList.get(i).get(3).equals(myList.get(2))) { cnt++; }
            if (csvList.get(i).get(4).equals(myList.get(3))) { cnt++; }

            if (cnt >= 3) {
                resultList.add(csvList.get(i).get(0));
            }
        }
    }
}
