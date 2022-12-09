package com.example.movieapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.movieapp.adapter.ReviewFragmentAdapter;
import com.example.movieapp.data.ReviewMainData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
    private TextView tv_nickname, tv_mbti; //닉네임

    //리스트 선언
    private ArrayList<ReviewMainData> review_list; //내 리뷰가 담긴 리스트

    //어댑터 선언
    private MyPageFragmentAdapter adapter;
    //리사이클러 뷰에서 사용할
    private LinearLayoutManager linearLayoutManager;

    //firebase
    String[] all_title;
    String title;
    int i;


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
        tv_mbti = (TextView) rootView.findViewById(R.id.tv_mbti);

        //수평 스크롤뷰로 설정하기 ㅎㅎ
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); //???
        rec_review_list.setLayoutManager(linearLayoutManager);

        //리스트 생성
        review_list = new ArrayList<>();

        //firebase에서 닉네임 가져오기 -다영-
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();

        //닉네임 화면에 띄워주는 함수
        readUser(userUid);

        //all review에 DB값 받아서 저장
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference();

        //firebase에서 title, date 등등 가져와서 all_review에 추가
        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                //name, rate, date, review
                try {
                    //영화 제목만 가져오기
                    Map<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                    String dataStr = String.valueOf(data.keySet());
                    all_title = dataStr.split(", ");

                    //얻은 title로 DB 다시 보기
                    for (i = 0; i < all_title.length; i++) {
                        //문자열 다듬기
                        title = all_title[i];
                        title = title.replace("[", "");
                        title = title.replace("]", "");
                        if (title.equals("email") || title.equals("mbti") || title.equals("name")) {
                            System.out.println("email, mbti .. equals");
                        } else {
                            userReference.child("user").child(userUid).child(title).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String title = snapshot.getKey();
                                    //DB poster저장하면 연결하기
                                    String poster = snapshot.child("poster").getValue().toString();
                                    Bitmap bitmap_poster = StringToBitmap(poster);

                                    String rating = snapshot.child("rating").getValue().toString();
                                    String date = snapshot.child("date").getValue().toString();
                                    String review = snapshot.child("review").getValue().toString();
                                    System.out.println("title in onDataChange " + title);
                                    //data 객체에 영화 값들 저장
                                    ReviewMainData data = new ReviewMainData(bitmap_poster, title, rating, date, review); //아이템 추가하는 코드
//                                  DB poster 연결하면 이걸로 data객체 바꾸기
//                                  ReviewMainData data = new ReviewMainData(poster, title, rating, date, review); //아이템 추가하는 코드

                                    review_list.add(data);
                                    System.out.println("all_review in datachange" + review_list);

//                                    adapter = new ReviewFragmentAdapter(all_review, homeActivity);
                                    //ReviewFragment 레이아웃의 리사이클러뷰와 어댑터 연결
                                    //어댑터 생성
                                    adapter = new MyPageFragmentAdapter(review_list, homeActivity);
                                    rec_review_list.setAdapter(adapter);

                                    // 저장된 리뷰가 없을 때 -> "작성한 감상평이 없네요!" 레이아웃을 보이게
                                    if (review_list.isEmpty()) {
                                        rec_review_list.setVisibility(View.INVISIBLE);  // 리사이클러뷰 잠깐 안 보이게 설정
                                        lin_no_review.setVisibility(View.VISIBLE);      // lin_no_result 레이아웃을 보이게 설정
                                    }
                                    // 있을 땐, 리사이클러뷰가 보이게 !
                                    else {
                                        rec_review_list.setVisibility(View.VISIBLE);    // 리사이클러뷰 보이게
                                        lin_no_review.setVisibility(View.INVISIBLE);    // lin_no_result 레이아웃 안 보이게
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                    //review 작성 안했을 경우
                    //리뷰데이터가 없으면 감상평 등록 레이아웃
                }catch (Exception e) {
                    System.out.println("review Fragement Error");
                }
                    }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                String mbti = user.getMbti();

                mbti = mbti.replace("[", "");
                mbti = mbti.replace(",", "");
                mbti = mbti.replace("]", "");

                tv_nickname.setText(name);
                tv_mbti.setText(mbti);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
