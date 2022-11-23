package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et_id, et_pass;
    private ImageButton btn_login, btn_goregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_goregister = findViewById(R.id.btn_goregister);

        // 회원가입 버튼을 클릭 시 회원가입 화면으로 이동
        btn_goregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //로그인
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });


    }

    private void doLogin() {
        String loginId = et_id.getText().toString().trim();
        String loginPasswd = et_pass.getText().toString().trim();
        /*
        if(loginId.length() == 0) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            et_id.requestFocus();

            return;
        }
        if(loginPasswd.length() == 0){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            et_pass.requestFocus();

            return;
        }

        Member member = AppDatabase.findMember(loginId);

        if(member == null){
            Toast.makeText(this, "존재하지 않는  아이디입니다.", Toast.LENGTH_SHORT).show();
            et_id.requestFocus();
            return;
        }
        else if(member.getLoginPasswd().equals(loginPasswd) == false){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다..", Toast.LENGTH_SHORT).show();
            et_pass.requestFocus();
            return;
        }
        */
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        //intent.putExtra("loginedMemberId", member.getId());
        startActivity(intent);
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();

    }
}