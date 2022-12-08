package com.example.movieapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.UserAccount;
import com.example.movieapp.activity.EditProfileActivity;
import com.example.movieapp.activity.HomeActivity;
import com.example.movieapp.adapter.MyPageFragmentAdapter;
import com.example.movieapp.data.ReviewMainData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageFragment extends Fragment {

    public static MypageFragment newInstance() {
        return new MypageFragment();
    }

    //홈 엑티비티 선언 (화면 전환시 필요)
    HomeActivity homeActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //홈 엑티비티 생성
        homeActivity = (HomeActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        homeActivity = null;
    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MypageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MypageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    private RecyclerView rec_review_list; //리사이클러 뷰
    private LinearLayout lin_no_review; //리뷰 없음 레이아웃
    private ImageButton ib_edit_profile, ib_more; //프로필 수정 버튼, 더보기 버튼
    private TextView tv_nickname; //닉네임

    //리스트 선언
    private ArrayList<ReviewMainData> review_list; //내 리뷰가 담긴 리스트

    //어댑터 선언
    private MyPageFragmentAdapter adapter;
    //리사이클러 뷰에서 사용할
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // rootView 객체에 fragment_mypage.xml 과 연결한 것을 담기.
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage, container, false);


        rec_review_list = (RecyclerView) rootView.findViewById(R.id.rec_review_list);
        lin_no_review = (LinearLayout) rootView.findViewById(R.id.lin_no_review);
        ib_more = (ImageButton) rootView.findViewById(R.id.ib_more);
        ib_edit_profile = (ImageButton) rootView.findViewById(R.id.ib_edit_profile);
        tv_nickname = (TextView) rootView.findViewById(R.id.tv_nickname);

        //firebase에서 닉네임 가져오기 -다영-
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();

        //닉네임 화면에 띄워주는 함수
        readUser(userUid);

        //수평 스크롤뷰로 설정하기 ㅎㅎ
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); //???
        rec_review_list.setLayoutManager(linearLayoutManager);


        //리스트 생성
        review_list = new ArrayList<>();

        //어댑터 생성
        adapter = new MyPageFragmentAdapter(review_list, homeActivity);


        // 테스트용 임시
        ReviewMainData mainData1 = new ReviewMainData(R.drawable.movie1, "쥬라기 월드", 5, "2022.02.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        review_list.add(mainData1);
        ReviewMainData mainData2 = new ReviewMainData(R.drawable.movie2, "스파이더맨:노 웨이 홈", 4, "2021.08.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        review_list.add(mainData2);
        ReviewMainData mainData3 = new ReviewMainData(R.drawable.movie3, "소닉 2", 4.5, "2022.09.03", "우왕 재밌다 우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다우왕 재밌다"); //아이템 추가하는 코드
        review_list.add(mainData3);
        ReviewMainData mainData4 = new ReviewMainData(R.drawable.movie1, "ㄱ", 2.5, "2022.02.20", ""); //아이템 추가하는 코드
        review_list.add(mainData4);


        // 저장된 리뷰가 없을 때 -> "작성한 감상평이 없네요!" 레이아웃을 보이게
        if(review_list.isEmpty()){
            rec_review_list.setVisibility(View.INVISIBLE);  // 리사이클러뷰 잠깐 안 보이게 설정
            lin_no_review.setVisibility(View.VISIBLE);      // lin_no_result 레이아웃을 보이게 설정
        }
        // 있을 땐, 리사이클러뷰가 보이게 !
        else{
            rec_review_list.setVisibility(View.VISIBLE);    // 리사이클러뷰 보이게
            lin_no_review.setVisibility(View.INVISIBLE);    // lin_no_result 레이아웃 안 보이게
        }


        rec_review_list.setAdapter(adapter);



        //프로필수정 버튼 눌렀을 때,
        ib_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Edit_profile.xml 로 이동
                //
                Intent intent = new Intent(homeActivity.getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });


        //더보기 버튼 눌렀을 때
        ib_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onFragmentChange(2, null);
            }
        });
        return rootView;



    }

    //로그인 user의 name, mbti읽기
    private void readUser(String userUid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference();

        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);
                String name = user.getName();
                tv_nickname.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}