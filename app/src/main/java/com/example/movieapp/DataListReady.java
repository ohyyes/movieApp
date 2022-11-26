package com.example.movieapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataListReady {
    public static List<List<String>> data_list = new ArrayList<>();

    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference userReference = database.getReference();

    public static void readMovie() {
        Log.d("readMovie = ", "readmovie");
        for (int i = 0; i < 63; i++) {
            userReference.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Movie movie = snapshot.getValue(Movie.class);
                    List<String> data = new ArrayList<>();
                    String title = movie.getTitle();
                    String IE = movie.getIE();
                    String NS = movie.getNS();
                    String TF = movie.getTF();
                    String JP = movie.getJP();
                    String Img = movie.getImg();
                    Log.d("title = ", title);

                    data.add(title);
                    data.add(IE);
                    data.add(NS);
                    data.add(TF);
                    data.add(JP);
                    data.add(Img);

                    data_list.add(data);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
