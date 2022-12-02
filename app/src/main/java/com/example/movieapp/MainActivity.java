package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private ImageButton btn_login, btn_goregister;
    private FirebaseAuth firebaseAuth;
    public String name, mbti;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

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

                //doLogin();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void doLogin() {
        String email = et_id.getText().toString().trim();
        String pwd = et_pass.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, pwd)
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