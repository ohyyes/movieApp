package com.example.movieapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    //chip group 선언
    ChipGroup chipGroupEI, chipGroupSN, chipGroupTF, chipGroupJP;
    Chip btn_e, btn_i, btn_s, btn_n, btn_t, btn_f, btn_j, btn_p;
    // 각 EditText 와 각 chipGroup 에 값이 있는지 TF로 확인할 변수들
    boolean has_EI, has_SN, has_TF, has_JP;

    //닉네임, [완료!] 버튼
    private EditText et_name;
    // 버튼 : 등록 버튼 (완료!)
    private ImageButton ib_register;
    private ImageButton ib_back;

    private boolean has_et_name;

    // -------------- 다영 -------------------
    String name, mbti;
    public String change_name, change_mbti;

    //firebase로 로그인한 사용자 정보 불러오기
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();


    //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
    private void setIbRegisterEnableDisable(boolean name, boolean EI, boolean SN, boolean TF,boolean JP){
        //모든 입력창에 값이 있다면 -> 버튼 활성화 및 배경색 가시적으로 바꾸기 !
        if(name && EI && SN && TF && JP){
            ib_register.setEnabled(true);
            ib_register.setImageResource(R.drawable.btn_done);

            ib_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    EditProfileActivity.super.onBackPressed();
                    Toast.makeText(getApplicationContext(), "정보수정 성공", Toast.LENGTH_SHORT).show();
                    //firebase에서도 값 바꾸기!

                    //Firebase 로그인한 사용자 정보
                    mAuth = FirebaseAuth.getInstance();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    String userUid = user.getUid();

                    //firebase 수정 함수
                    modifyUser(userUid);
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
        has_EI = false;
        has_SN = false;
        has_TF = false;
        has_JP = false;


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
                if(et_name.length() > 0) {
                    change_name = et_name.getText().toString();
                    has_et_name = true;
                }
                else has_et_name = false;

                //각 입력값에 값이 있는지 확인 후 버튼 활성화 or 비활성화 하는 메소드
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);


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

        //EI chip 버튼 클릭 시
        btn_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_EI = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_EI = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        //SN chip 버튼 클릭 시
        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_SN = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_SN = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        //TF chip 버튼 클릭 시
        btn_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_TF = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_TF = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        //JP chip 버튼 클릭 시
        btn_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_JP = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });
        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                has_JP = true;
                setIbRegisterEnableDisable(has_et_name, has_EI, has_SN, has_TF,has_JP);
            }
        });

        /* 회원정보 초기값 */
        //(다영느님의 touch 필요)

        //Firebase 로그인한 사용자 정보
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();

        //firebase에서 값 읽어와서 화면에 name, mbti 띄우는 함수
        readUser(userUid);
    }

    //mbti list로 저장하는 함수
    private void checkedMbti() {
        ArrayList<String> mbti_list = new ArrayList<>();
        if (btn_e.isChecked()) mbti_list.add("E");
        if (btn_i.isChecked()) mbti_list.add("I");
        if (btn_s.isChecked()) mbti_list.add("S");
        if (btn_n.isChecked()) mbti_list.add("N");
        if (btn_t.isChecked()) mbti_list.add("T");
        if (btn_f.isChecked()) mbti_list.add("F");
        if (btn_p.isChecked()) mbti_list.add("P");
        if (btn_j.isChecked()) mbti_list.add("J");

        change_mbti = mbti_list.toString();
    }

    //로그인 user의 name, mbti읽기
    private void readUser(String userUid){
        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);

                //각각의 값 가져오기
                //이거 화면에 옮기면 됨
                name = user.getName();
                mbti = user.getMbti();

                // DB에 저장되어있던 닉네임 값을 setText()의 입력값으로 넣어 input창에 기존 닉네임이 입력되어있도록 설정
                et_name.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //내 프로필 수정하기 -> 값 수정 함수
    private void modifyUser(String userUid){
        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);

                //값 수정
                checkedMbti();
                userReference.child("user").child(userUid).child("name").setValue(change_name);
                userReference.child("user").child(userUid).child("mbti").setValue(change_mbti);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
