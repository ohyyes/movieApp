package com.example.movieapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.activity.MainActivity;
import com.example.movieapp.activity.RegisterActivity;
import com.example.movieapp.adapter.ReviewFragmentAdapter;
import com.example.movieapp.activity.HomeActivity;
import com.example.movieapp.data.ReviewMainData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    //홈 엑티비티 선언 (화면 전환시 필요)
    HomeActivity homeActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //홈 엑티비티 생성
        homeActivity = (HomeActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //홈 엑티비티 제거
        homeActivity = null;
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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


    //데이터 리스트
    private ArrayList<ReviewMainData> all_review;

    //어댑터 선언
    private ReviewFragmentAdapter adapter;
    private ArrayAdapter<String> spinnerAdapter;

    //감상평 목록 정렬 기준 스피너 리스트
    String[] sortItems = {"최신순", "이름순", "별점높은순", "별점낮은순"};

    //버튼 선언
    private Button btn_edit, btn_back, btn_delete;
    private CheckBox checkbox;

    //편집모드 여부 변수
    private int editMode = 0;
    String[] all_title;
    String title;
    int i;

    //firebase값 저장 변수
    Bitmap bitmap_poster;
    String rating, date, review;

    // 로딩중 표시를 위한 다이얼로그
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_review, container, false);

        //스피너 연결 후 어댑터 지정 -> 어댑터는 android 라이브러리에 정의된 것 사용
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, sortItems);
        spinner.setAdapter(spinnerAdapter);

        //버튼 연결
        btn_edit = rootView.findViewById(R.id.btn_edit);
        btn_back = rootView.findViewById(R.id.btn_back);
        btn_delete = rootView.findViewById(R.id.btn_delete);
        checkbox = rootView.findViewById(R.id.checkbox);

        RecyclerView review_list = (RecyclerView) rootView.findViewById(R.id.review_list);
        LinearLayout lin_no_review = (LinearLayout) rootView.findViewById(R.id.lin_no_review);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()); //???
        review_list.setLayoutManager(layoutManager);

        //DB에서 title 가져오기
        //title이 존재 -> 해당 poster, title, review, rating, year 가져오기
        //all review에 DB값 받아서 저장
        all_review = new ArrayList<>();

        //firebase에서 닉네임 가져오기 -다영-
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference();

        //firebase에서 title, date 등등 가져와서 all_review에 추가
        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                        if(title.equals("email") || title.equals("mbti") || title.equals("name")){
                            System.out.println("email, mbti .. equals");
                        } else{
                            userReference.child("user").child(userUid).child(title).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        //DB 불러오기
                                        String title = snapshot.getKey();

                                        //poster Bitmap으로 바꾸기
                                    if(snapshot.child("poster").getValue() != null){
                                        String poster = snapshot.child("poster").getValue().toString();
                                        bitmap_poster = StringToBitmap(poster);
                                    }
                                    if(snapshot.child("rating").getValue() != null){
                                        rating = snapshot.child("rating").getValue().toString();
                                    }
                                    if(snapshot.child("date").getValue() != null){
                                        date = snapshot.child("date").getValue().toString();
                                    }
                                    if(snapshot.child("review").getValue() != null){
                                        review = snapshot.child("review").getValue().toString();
                                    }
                                        //객체 저장
                                        ReviewMainData data = new ReviewMainData(bitmap_poster, title, rating, date, review); //아이템 추가하는 코드
                                        all_review.add(data);

                                        //ReviewFragment 레이아웃의 리사이클러뷰와 어댑터 연결
                                        adapter = new ReviewFragmentAdapter(all_review, homeActivity);
                                        review_list.setAdapter(adapter);

                                    //데이터 유무에 따라 보이는 리사이클러뷰 다름
                                    if (all_review.isEmpty()) {
                                        review_list.setVisibility(View.INVISIBLE);  // 리사이클러뷰 잠깐 안 보이게 설정
                                        lin_no_review.setVisibility(View.VISIBLE);      // lin_no_result 레이아웃을 보이게 설정
                                    }
                                    // 있을 땐, 리사이클러뷰가 보이게 !
                                    else {
                                        review_list.setVisibility(View.VISIBLE);    // 리사이클러뷰 보이게
                                        lin_no_review.setVisibility(View.INVISIBLE);    // lin_no_result 레이아웃 안 보이게
                                    }

                                    //스피너 아이템 선택했을 때
                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            if (i == 0) {//최신순
                                                Comparator<ReviewMainData> timeDesc = new Comparator<ReviewMainData>() {
                                                    @Override
                                                    public int compare(ReviewMainData item1, ReviewMainData item2) {
                                                        return item2.getTv_review_date().compareTo(item1.getTv_review_date());
                                                    }
                                                };
                                                Collections.sort(all_review, timeDesc);
                                                adapter.setItems(all_review);
                                            } else if (i == 1) {//이름순
                                                Comparator<ReviewMainData> textAsc = new Comparator<ReviewMainData>() {
                                                    @Override
                                                    public int compare(ReviewMainData item1, ReviewMainData item2) {
                                                        return item1.getTv_name().compareTo(item2.getTv_name());
                                                    }
                                                };
                                                Collections.sort(all_review, textAsc);
                                                adapter.setItems(all_review);
                                            } else if (i == 2) { //별점높은순
                                                Comparator<ReviewMainData> starDesc = new Comparator<ReviewMainData>() {
                                                    int ret; //변수 선언은 함수 밖에서 해줘야 함

                                                    @Override
                                                    public int compare(ReviewMainData d1, ReviewMainData d2) {
                                                        if (Double.valueOf(d1.getTv_my_rate()) < Double.valueOf(d2.getTv_my_rate()))
                                                            ret = 1;
                                                        else if (d1.getTv_my_rate() == d2.getTv_my_rate())
                                                            ret = 0;
                                                        else ret = -1;
                                                        return ret;
                                                    }
                                                };
                                                Collections.sort(all_review, starDesc);
                                                adapter.setItems(all_review);
                                            } else if (i == 3) { //별점낮은순
                                                Comparator<ReviewMainData> starAsc = new Comparator<ReviewMainData>() {
                                                    int ret; //변수 선언은 함수 밖에서 해줘야 함

                                                    @Override
                                                    public int compare(ReviewMainData d1, ReviewMainData d2) {
                                                        if (Double.valueOf(d1.getTv_my_rate()) < Double.valueOf(d2.getTv_my_rate()))
                                                            ret = -1;
                                                        else if (d1.getTv_my_rate() == d2.getTv_my_rate())
                                                            ret = 0;
                                                        else ret = 1;
                                                        return ret;
                                                    }
                                                };
                                                Collections.sort(all_review, starAsc);
                                                adapter.setItems(all_review);
                                            }
                                        }

                                        //아무것도 선택되지 않았을 때
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                            //바뀌는 것 없음
                                        }
                                    });
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
                //review 작성 안했을 경우
                //리뷰데이터가 없으면 감상평 등록 레이아웃
                catch (Exception e) {
                    System.out.println("review Fragement Error");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //편집 버튼 눌렀을 때-> 편집모드로 바꿈
        btn_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                changeMode(1);
            }
        });

        //취소 버튼 눌렀을 때 -> 기본모드로 바꿈
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode(0);
            }
        });

        //삭제 버튼 눌렀을 때 -> 알림창 띄우고 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                ArrayList<ReviewMainData> RevList = ((ReviewFragmentAdapter) adapter).getArrayList();
                int count = adapter.getSelectedItemCount();
                for (int i = 0; i < RevList.size(); i++) {
                    if (RevList.get(i).isSelected() == true) {
                        deleteReview(i);
                        all_review.remove(i);
                        i--; //삭제된 인덱스가 없어지기 때문에 i--처리를 해주지 않으면 바로 다음 아이템 건너뛰게 됨
                    }
                }
                adapter.notifyDataSetChanged(); //리스트 갱신
                //데이터 유무에 따라 보이는 리사이클러뷰 다름
                if (RevList.isEmpty()) {
                    review_list.setVisibility(View.INVISIBLE);    // 리사이클러뷰 보이게
                    lin_no_review.setVisibility(View.VISIBLE);    // lin_no_result 레이아웃 안 보이게
                }
                Toast.makeText(getActivity(), count + "개 삭제됨", Toast.LENGTH_SHORT).show();
                //삭제시, 홈프래그먼트로 이동
                //homeActivity.onFragmentChange(3, null);
            }
        });

        return rootView;
    }

    //일반모드or편집모드 변경
    private void changeMode(int n) {
        adapter.updateCheckbox(n);

        if (n == 1) { //편집 모드
            btn_edit.setVisibility(View.GONE);
            btn_delete.setVisibility(View.VISIBLE);
            btn_back.setVisibility(View.VISIBLE);
            editMode = 1;
        } else {
            btn_edit.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.GONE);
            btn_back.setVisibility(View.GONE);
        }

    }

    /* * String형을 BitMap으로 변환시켜주는 함수 * */
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void deleteReview(int num){
        //user Token 받아오기
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();
        String delete_title = all_review.get(num).getTv_name();
        System.out.println("delete " + all_review.get(num).getTv_name());

//        // 로딩중 표시
//        progressDialog = new ProgressDialog(homeActivity);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();      // 시작

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference();
        userReference.child("user").child(userUid).child(delete_title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
    }

}

