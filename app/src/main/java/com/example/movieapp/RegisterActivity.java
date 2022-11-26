package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class RegisterActivity extends AppCompatActivity {
    //chip group 선언
    ChipGroup chipGroupEI, chipGroupSN, chipGroupTF, chipGroupJP;
    Chip btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;


    // 입력창 : 이메일, 비번, 비번 확인, 닉네임
    private EditText et_email, et_pw1, et_pw2, et_name;
    // 버튼 : 등록 버튼 (완료!)
    private ImageButton ib_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_register.xml 과 연결
        setContentView(R.layout.activity_register);


        //ImageButton, EditText 불러오기
        ib_register = (ImageButton) findViewById(R.id.btn_register);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pw1 = (EditText) findViewById(R.id.et_pw1);
        et_pw2 = (EditText) findViewById(R.id.et_pw2);
        et_name = (EditText) findViewById(R.id.et_name);


        //chipGroup 불러오기
        chipGroupEI = (ChipGroup) findViewById(R.id.chipGroupEI);
        chipGroupSN = (ChipGroup) findViewById(R.id.chipGroupSN);
        chipGroupTF = (ChipGroup) findViewById(R.id.chipGroupTF);
        chipGroupJP = (ChipGroup) findViewById(R.id.chipGroupJP);


        //chip 불러오기
        btn_e = (Chip) findViewById(R.id.btn_e); btn_i = (Chip) findViewById(R.id.btn_i);
        btn_s = (Chip) findViewById(R.id.btn_s); btn_n = (Chip) findViewById(R.id.btn_n);
        btn_t = (Chip) findViewById(R.id.btn_t); btn_f = (Chip) findViewById(R.id.btn_f);
        btn_j = (Chip) findViewById(R.id.btn_j); btn_p = (Chip) findViewById(R.id.btn_p);


        // 특정 chipGroup 의 모든 하위 칩이 선택 취소되지 않도록 (즉, 둘 중 하나를 클릭한 이후엔 두 버튼 모두 선택취소 되지 않도록)
        chipGroupEI.setSelectionRequired(true);
        chipGroupSN.setSelectionRequired(true);
        chipGroupTF.setSelectionRequired(true);
        chipGroupJP.setSelectionRequired(true);

        //chip 버튼이 클릭됐음을 가시적으로 보여줌
        btn_e.setCheckable(true); btn_i.setCheckable(true);
        btn_s.setCheckable(true); btn_n.setCheckable(true);
        btn_t.setCheckable(true); btn_f.setCheckable(true);
        btn_j.setCheckable(true); btn_p.setCheckable(true);


        // [완료!] 버튼이 눌렸을 때 실행될 코드
        ib_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // 각 EditText 와 각 chipGroup 에 값이 있는지 TF로 확인할 변수들
                boolean has_email, has_pw1, has_pw2, has_name, has_EI, has_SN, has_TF, has_JP;

                //각 EditText 에 값이 0자 보다 많으면 true 를 아니면 false 를 변수에 저장
                has_email = et_email.length() > 0;
                has_pw1 = et_pw1.length() > 0;
                has_pw2 = et_pw2.length() > 0;
                has_name = et_name.length() > 0;

                // chipGroup 중 하나의 chip 이 선택 되었다면 true 를 아니면 false 를 변수에 저장
                has_EI = btn_e.isChecked() || btn_i.isChecked();
                has_SN = btn_s.isChecked() || btn_n.isChecked();
                has_TF = btn_t.isChecked() || btn_f.isChecked();
                has_JP = btn_j.isChecked() || btn_p.isChecked();

                //입력값과 chip 조건을 모두 만족할 때만 -> 버튼 배경색 변경 및 MainActivity 로 이동
                if(has_email && has_pw1 && has_pw2 && has_name && has_EI && has_SN && has_TF && has_JP){
                    ib_register.setImageResource(R.drawable.registerbutton2);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}