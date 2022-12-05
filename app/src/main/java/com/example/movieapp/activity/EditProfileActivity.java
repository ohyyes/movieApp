package com.example.movieapp.activity;

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

public class EditProfileActivity extends AppCompatActivity {
    //chip group 선언
    ChipGroup chipGroupEI, chipGroupSN, chipGroupTF, chipGroupJP;
    Chip btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;

    //닉네임, [완료!] 버튼
    private EditText et_name;
    // 버튼 : 등록 버튼 (완료!)
    private ImageButton ib_register;
    private ImageButton ib_back;

    private boolean has_et_name;
    //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
    private void setIbRegisterEnableDisable(boolean name){
        //모든 입력창에 값이 있다면 -> 버튼 활성화 및 배경색 가시적으로 바꾸기 !
        if(name){
            ib_register.setEnabled(true);
            ib_register.setImageResource(R.drawable.btn_done);

            ib_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    EditProfileActivity.super.onBackPressed();
                    Toast.makeText(getApplicationContext(), "정보수정 성공", Toast.LENGTH_SHORT).show();
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
        //activity_edit_profile.xml 과 연결
        setContentView(R.layout.activity_edit_profile);


        //ImageButton, EditText 불러오기
        et_name = findViewById(R.id.et_name);
        ib_register = findViewById(R.id.btn_register);
        ib_back = findViewById(R.id.ib_back);

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

        //뒤로가기버튼 클릭시
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileActivity.super.onBackPressed();
            }
        });

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



        /* 회원정보 초기값 */

        //(다영느님의 touch 필요)
        // DB에 저장되어있던 닉네임 값을 setText()의 입력값으로 넣어 input창에 기존 닉네임이 입력되어있도록 설정
        et_name.setText("안녕하세요");
        // DB에 저장되어있던 MBTI 값에 해당하는 chip 들이 기본으로 선택되도록
        btn_e.setChecked(true);
        btn_n.setChecked(true);
        btn_f.setChecked(true);
        btn_j.setChecked(true);

    }
}
