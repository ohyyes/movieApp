package com.example.movieapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.DataListReady;
import com.example.movieapp.R;
import com.example.movieapp.fragment.RecFragment;
import com.example.movieapp.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private ImageButton btn_login, btn_goregister;

    //firebase
    private FirebaseAuth firebaseAuth;
    public String name, mbti;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_goregister = findViewById(R.id.btn_goregister);

        firebaseAuth = FirebaseAuth.getInstance();

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
        /* front
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

        //firebase 통해 로그인
        firebaseAuth.signInWithEmailAndPassword(loginId, loginPasswd)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userUid = user.getUid();

                            getFirebase(userUid);
                            DataListReady.readMovie();
                            try {
                                Toast.makeText(MainActivity.this, "잠시 기다려 주세요.", Toast.LENGTH_SHORT).show();
                                Thread.sleep(2000); //2초 대기
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //firebase에서 mbti, name 읽어오기
    private void getFirebase(String userUid){
        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);

                String fb_name = user.getName();
                String fb_mbti = user.getMbti();
                name = fb_name;
                mbti = fb_mbti;

                mbti = mbti.replace("[", "");
                mbti = mbti.replace("]", "");
                RecFragment.MBTIList = new ArrayList<>(Arrays.asList(mbti.split(", ")));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}