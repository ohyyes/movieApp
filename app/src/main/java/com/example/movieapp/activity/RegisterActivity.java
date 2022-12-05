package com.example.movieapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class RegisterActivity extends AppCompatActivity {
    //chip group 선언
    ChipGroup chipGroupEI, chipGroupSN, chipGroupTF, chipGroupJP;
    Chip btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;


    // 입력창 : 이메일, 비번, 비번 확인, 닉네임
    private EditText et_email, et_pw1, et_pw2, et_name;
    // EditText 에 값이 있는지 TF로 확인
    private boolean has_et_email, has_et_pw1, has_et_pw2, has_et_name;
    // 각 EditText 와 각 chipGroup 에 값이 있는지 TF로 확인할 변수들
    boolean has_EI, has_SN, has_TF, has_JP;
    // 버튼 : 등록 버튼 (완료!)
    private ImageButton ib_register;


    //입력값 유무에 따른 등록 버튼 활성화 구현
    private void setIbRegisterEnableDisable(boolean email, boolean pw1, boolean pw2, boolean name, boolean EI, boolean SN, boolean TF, boolean JP){
        //입력값과 chip 조건을 모두 만족할 때만 -> 버튼 배경색 변경 및 MainActivity 로 이동

        if(email && pw1 && pw2 && name && EI && SN && TF && JP){
            ib_register.setEnabled(true);
            ib_register.setImageResource(R.drawable.btn_done);

            ib_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                }
            });
        }
        // 하나라도 빈 입력창이 있다면 -> 버튼 비활성화
        else{
            ib_register.setEnabled(false);
            ib_register.setImageResource(R.drawable.registerbutton);
        }
    }

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


        has_et_email = false;
        has_et_pw1 = false;
        has_et_pw2 = false;
        has_et_name = false;

        //chipGroup 불러오기
        chipGroupEI = (ChipGroup) findViewById(R.id.chipGroupEI);
        chipGroupSN = (ChipGroup) findViewById(R.id.chipGroupSN);
        chipGroupTF = (ChipGroup) findViewById(R.id.chipGroupTF);
        chipGroupJP  =(ChipGroup) findViewById(R.id.chipGroupJP);


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

        // 초기화
        has_EI = false;
        has_SN = false;
        has_TF = false;
        has_JP = false;

        //EI chip 버튼 클릭 시
        btn_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_EI = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_EI = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        //SN chip 버튼 클릭 시
        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_SN = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_SN = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        //TF chip 버튼 클릭 시
        btn_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_TF = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_TF = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        //JP chip 버튼 클릭 시
        btn_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_JP = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_JP = true;
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        // 이메일 입력창에 값이 들어오면
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //값 입력할 때
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // email 입력창에 값이 1개이상 들어오면 true 로 바꿈
                if(et_email.length() > 0) has_et_email = true;
                else has_et_email = false;

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        // 비번 입력창에 값이 들어오면
        et_pw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // pw1 입력창에 값이 1개이상 들어오면 true 로 바꿈
                if(et_pw1.length() > 6) has_et_pw1 = true;
                else {
                    has_et_pw1 = false;
                    et_pw1.setError("비밀번호는 7자리 이상 입력해주세요.",null);//에러메세지 띄우기
                    }

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        // 비번확인창에 입력값이 들어오면
        et_pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 입력창에 값이 바뀐 후 실행할 코드
            @Override
            public void afterTextChanged(Editable editable) {
                // pw2 입력창에 값이 1개이상 들어오면 true 로 바꿈
                if(et_pw2.getText().toString().equals(et_pw1.getText().toString())) has_et_pw2 = true;
                else {
                    has_et_pw2 = false;
                    et_pw2.setError("비밀번호를 똑같이 입력해주세요",null);//에러메세지 띄우기
                    }

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

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
                setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });



        //입력값 유무에 따른 등록 버튼 활성화 함수
        setIbRegisterEnableDisable(has_et_email, has_et_pw1, has_et_pw2, has_et_name, has_EI, has_SN, has_TF,has_JP);

    }
}