package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class EditProfileActivity extends AppCompatActivity {
    //chip group 선언
    ChipGroup chipGroupEI, chipGroupSN, chipGroupTF, chipGroupJP;
    Chip btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;

    //닉네임, [완료!] 버튼
    private EditText et_name;
    private ImageButton ib_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_edit_profile.xml 과 연결
        setContentView(R.layout.activity_edit_profile);


        //ImageButton, EditText 불러오기
        et_name = findViewById(R.id.et_name);
        ib_register = findViewById(R.id.btn_register);


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



        // [완료!] 버튼이 눌렸을 때 실행될 코드
        ib_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // 닉네임 EditText 에 값이 있는지 TF로 확인할 변수들
                boolean has_name;

                //닉네임 EditText 에 값이 0자 보다 많으면 true 를 아니면 false 를 변수에 저장
                has_name = et_name.length() > 0;


                //입력값과 chip 조건을 모두 만족할 때만 -> 버튼 배경색 변경 및 MyPageFragment 로 이동
                if(has_name){
                    ib_register.setImageResource(R.drawable.registerbutton2); //버튼 배경색 변경
                    // 엑티비티 to 프래그먼트 화면 전환 코드 (수연이가 써줄..ㅎ)
//                    Intent intent = new Intent(RegisterActivity.this, MyPageFragment.class);
//                    startActivity(intent);
                }
            }
        });
    }
}