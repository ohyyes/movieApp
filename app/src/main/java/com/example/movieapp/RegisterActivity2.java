package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity2 extends AppCompatActivity {
//    private FirebaseAuth firebaseAuth;
    private ImageButton btn_register;
    private String email, pwd, name;
    private EditText et_name;
    private ImageButton btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;

    public RegisterActivity2(){
        //email, pwd 가져오기
        RegisterActivity info = new RegisterActivity();
        email = info.email;
        pwd = info.pw1;
        Log.d("email: ", email);
        Log.d("info.email: ", info.email);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

//        firebaseAuth = FirebaseAuth.getInstance();

        btn_register = findViewById(R.id.btn_register);
        et_name = findViewById(R.id.et_name);
        btn_e = findViewById(R.id.btn_e);
        btn_i = findViewById(R.id.btn_i);
        btn_s = findViewById(R.id.btn_s);
        btn_n = findViewById(R.id.btn_n);
        btn_t = findViewById(R.id.btn_t);
        btn_f = findViewById(R.id.btn_f);
        btn_j = findViewById(R.id.btn_j);
        btn_p = findViewById(R.id.btn_p);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                Log.d("email: ", email);
                //닉네임이 공백이 아닌 경우
                if(!name.equals("")){
                    Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity2.this, "닉네임을 입력해 주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
