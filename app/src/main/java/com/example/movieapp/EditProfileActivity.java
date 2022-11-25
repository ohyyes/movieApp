package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditProfileActivity extends AppCompatActivity {
    // 입력창 : 이메일, 비번, 비번 확인, 닉네임
    private EditText et_name;
    // 버튼 : 등록 버튼 (완료!)
    private ImageButton ib_register;

    // EditText 에 값이 있는지 TF로 확인
    private boolean has_et_name;

    //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
    private void setIbRegisterEnableDisable(boolean name){
        //모든 입력창에 값이 있다면 -> 버튼 활성화 및 배경색 가시적으로 바꾸기 !
        if(name){
            ib_register.setEnabled(true);
            ib_register.setBackgroundColor(Color.parseColor("#2196F3"));

            ib_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //화면 이동
                    Intent intent = new Intent(EditProfileActivity.this, MypageFragment.class);
                    startActivity(intent);
                }
            });
        }
        // 하나라도 빈 입력창이 있다면 -> 버튼 비활성화
        else{
            ib_register.setEnabled(false);
            ib_register.setBackgroundColor(getColor(android.R.color.transparent)); //투명하게
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_name = findViewById(R.id.et_name);
        ib_register = findViewById(R.id.btn_register);

        //초기화
        has_et_name = false;


        // 닉네임 입력창에 값이 들어오면
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // et_name 입력창에 값이 1개이상 들어오면 true 로 바꿈
                if(et_name.length() > 0) has_et_name = true;
                else has_et_name = false;

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_name);
            }
        });

    }
}