package com.example.movieapp;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    //firebase에서 데이터 추가/조회 하기 위한 instance
    private DatabaseReference mDatabase;

    private EditText et_email, et_pw1, et_pw2, et_name;
    private ImageButton btn_register;
    public String email, pw1, pw2, nickname;
    private Chip btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;
    private ChipGroup chipGroupEI, chipGroupSN, chipGroupTF, chipGroupJP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (ImageButton) findViewById(R.id.btn_register);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pw1 = (EditText) findViewById(R.id.et_pw1);
        et_pw2 = (EditText) findViewById(R.id.et_pw2);
        et_name = (EditText) findViewById(R.id.et_name);
        chipGroupEI = (ChipGroup) findViewById(R.id.chipGroupEI);
        chipGroupSN = (ChipGroup)findViewById(R.id.chipGroupSN);
        chipGroupTF = (ChipGroup)findViewById(R.id.chipGroupTF);
        chipGroupJP = (ChipGroup)findViewById(R.id.chipGroupJP);
        btn_e = (Chip) findViewById(R.id.btn_e);
        btn_i = (Chip) findViewById(R.id.btn_i);
        btn_s = (Chip) findViewById(R.id.btn_s);
        btn_n = (Chip) findViewById(R.id.btn_n);
        btn_t = (Chip) findViewById(R.id.btn_t);
        btn_f = (Chip) findViewById(R.id.btn_f);
        btn_j = (Chip) findViewById(R.id.btn_j);
        btn_p = (Chip) findViewById(R.id.btn_p);

        chipGroupEI.setSelectionRequired(true);
        chipGroupSN.setSelectionRequired(true);
        chipGroupTF.setSelectionRequired(true);
        chipGroupJP.setSelectionRequired(true);

        //click시 check표시 띄우기 (색깔로 변환 뒤 지우기!)
        btn_e.setCheckable(true);
        btn_i.setCheckable(true);
        btn_s.setCheckable(true);
        btn_n.setCheckable(true);
        btn_t.setCheckable(true);
        btn_f.setCheckable(true);
        btn_j.setCheckable(true);
        btn_p.setCheckable(true);

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        readUser();

        // 다음 버튼을 클릭 시 닉네임, mbti입력 화면으로 이동
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();

                email = et_email.getText().toString();
                pw1 = et_pw1.getText().toString();
                pw2 = et_pw2.getText().toString();
                nickname = et_name.getText().toString();

                //이메일, 비밀번호, 닉네임이 공백이 아닐 경우
                if(!email.equals("") && !pw1.equals("") && !pw2.equals("") && !nickname.equals("")){
                    //회원가입 조건 성립 -> 화면 넘어감
                    if(pw1.equals(pw2)){
                        //조건 : 비밀번호 6자 이상
                        if(pw1.length() > 5){
                            createUser(email, pw1, nickname);
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
    private void createUser(String email, String pwd, String nickname){
        UserAccount user = new UserAccount(nickname, email);
        Toast.makeText(RegisterActivity.this, "createUser", Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDatabase.child("users").child(nickname).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    // 회원가입 성공시
                                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            Log.w("signInWithCustomToken:failure", task.getException());
                            // 계정이 중복된 경우
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //데이터베이스 파일 읽기
    private void readUser(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(UserAccount.class) != null){
                    UserAccount post = dataSnapshot.getValue(UserAccount.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(RegisterActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}