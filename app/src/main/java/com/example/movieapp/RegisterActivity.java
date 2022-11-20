package com.example.movieapp;

import static com.example.movieapp.R.id.btn_next;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_next = findViewById(R.id.btn_next);

        // 다음 버튼을 클릭 시 닉네임, mbti입력 화면으로 이동
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                startActivity(intent);
            }
        });
    }
}