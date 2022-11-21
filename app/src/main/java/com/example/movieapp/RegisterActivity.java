package com.example.movieapp;

import static com.example.movieapp.R.id.btn_next;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText et_email, et_pw1, et_pw2;
    private ImageButton btn_next;
    public String email, pw1, pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = (EditText) findViewById(R.id.et_email);
        et_pw1 = (EditText) findViewById(R.id.et_pw1);
        et_pw2 = (EditText) findViewById(R.id.et_pw2);

        btn_next = findViewById(R.id.btn_next);

        // 다음 버튼을 클릭 시 닉네임, mbti입력 화면으로 이동
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();

                email = et_email.getText().toString();
                pw1 = et_pw1.getText().toString();
                pw2 = et_pw2.getText().toString();

                //이메일, 비밀번호가 공백이 아닐 경우
                if(!email.equals("") && !pw1.equals("") && !pw2.equals("")){
                    //회원가입 조건 성립 -> 화면 넘어감
                    if(pw1.equals(pw2)){
                        //조건 : 비밀번호 6자 이상
                        if(pw1.length() > 5){
                            createUser(email, pw1);
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "비밀번호를 6자 이상 입력하세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                    //비밀번호가 같지 않은 경우
                    else{
                        Toast.makeText(RegisterActivity.this, "비밀번호가 같지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }
                //이메일, 비밀번호가 공백일 경우
                else{
                    Toast.makeText(RegisterActivity.this, "이메일 또는 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //계정 생성
    private void createUser(String email, String pwd){
        Toast.makeText(RegisterActivity.this, "createUser", Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                            startActivity(intent);
                        } else {
                            Log.w("signInWithCustomToken:failure", task.getException());
                            // 계정이 중복된 경우
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}