package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabaseRef;
    //firebase에서 데이터 추가/조회 하기 위한 instance
    private DatabaseReference userReference;

    private EditText et_email, et_pw1, et_pw2, et_name;
    private ImageButton ib_register;
    private boolean has_et_email, has_et_pw1, has_et_pw2, has_et_name;
    public String email, pw1, pw2, name, profile="";
    public ArrayList<String> mbti_list = new ArrayList<>();
    private Chip btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;
    private ChipGroup chipGroupEI, chipGroupSN, chipGroupTF, chipGroupJP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ib_register = (ImageButton) findViewById(R.id.ib_register);
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

        //초기화
        has_et_email = false;
        has_et_pw1 = false;
        has_et_pw2 = false;
        has_et_name = false;

        //firebase 정의
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance();
        userReference = mDatabaseRef.getReference();

        // 이메일 입력창에 값이 들어오면
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // email 입력창에 값이 1개이상 들어오면 true 로 바꿈
                if(et_email.length() > 0) {
                    email = et_email.getText().toString();
                    has_et_email = true;
                }
                else has_et_email = false;

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name);
            }
        });

        // 비번 입력창에 값이 들어오면
        et_pw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // pw1 입력창에 값이 1개이상 들어오면 true 로 바꿈
                // 비밀번호는 6자 이상이어야 함
                if(et_pw1.length() > 5) {
                    pw1 = et_pw1.getText().toString();
                    has_et_pw1 = true;
                }
                else has_et_pw1 = false;

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name);
            }
        });

        // 비번확인창에 입력값이 들어오면
        et_pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // pw2 입력창에 값이 1개이상 들어오면 true 로 바꿈
                if(et_pw2.length() > 0){
                    pw2 = et_pw2.getText().toString();
                    has_et_pw2 = true;
                }
                else has_et_pw2 = false;

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name);
            }
        });

        // 닉네임 입력창에 값이 들어오면
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // et_name 입력창에 값이 1개이상 들어오면 true 로 바꿈
                if(et_name.length() > 0) {
                    name = et_name.getText().toString();
                    has_et_name = true;
                }
                else has_et_name = false;

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name);
            }
        });
    }

    //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
    private void setIbRegisterEnableDisable(boolean bl_email, boolean bl_pw1, boolean bl_pw2, boolean bl_name){
        //모든 입력창에 값이 있다면 -> 버튼 활성화 및 배경색 가시적으로 바꾸기 !
        if(bl_email && bl_pw1 && bl_pw2 && bl_name){
            ib_register.setEnabled(true);
            ib_register.setBackgroundColor(Color.parseColor("#2196F3"));

            ib_register.setOnClickListener(new View.OnClickListener() {
                //값이 다 존재하는 경우
                @Override
                public void onClick(View view) {
                    //DB 저장, mbti list로 변환
                    if(pw1.equals(pw2)){
                        checkedMbti();
                        createUser(email, pw1, name);
                        readUser();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(RegisterActivity.this, "비밀번호가 같지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        // 하나라도 빈 입력창이 있다면 -> 버튼 비활성화
        else{
            ib_register.setEnabled(false);
            ib_register.setBackgroundColor(getColor(android.R.color.transparent)); //투명하게
        }
    }

    //mbti list로 저장하는 함수
    private void checkedMbti(){
        if(btn_e.isChecked()) mbti_list.add("E");
        if(btn_i.isChecked()) mbti_list.add("I");
        if(btn_s.isChecked()) mbti_list.add("S");
        if(btn_n.isChecked()) mbti_list.add("N");
        if(btn_t.isChecked()) mbti_list.add("T");
        if(btn_f.isChecked()) mbti_list.add("F");
        if(btn_p.isChecked()) mbti_list.add("P");
        if(btn_j.isChecked()) mbti_list.add("J");
    }
    //계정 생성
    private void createUser(String email, String pwd, String name){
        UserAccount user = new UserAccount(name, pwd, email);
//        Toast.makeText(RegisterActivity.this, "createUser", Toast.LENGTH_SHORT).show();
        Log.d("email =", email);
        Log.d("pwd =", pwd);
        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            userReference.child("User").child("1").setValue(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "reference등록 성공", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("reference에 등록 실패", name);
                                }
                            });
                        } else{
                            Log.w("signInWithCustomToken:failure", task.getException());
                            // 계정이 중복된 경우
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //데이터베이스 파일 읽기
    private void readUser(){
        Toast.makeText(RegisterActivity.this, "readUser()함수", Toast.LENGTH_SHORT).show();
        userReference.child("User").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                if(dataSnapshot.getValue(UserAccount.class) != null){
//                    UserAccount post = dataSnapshot.getValue(UserAccount.class);
//                    Log.w("FireBaseData", "getData" + post.toString());
//                } else {
//                    Toast.makeText(RegisterActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}