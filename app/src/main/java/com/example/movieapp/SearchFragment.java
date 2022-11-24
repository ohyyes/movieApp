package com.example.movieapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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



    private RecyclerView rec_search_list;  // 리사이클러 뷰
    private LinearLayout lin_no_result; //검색결과 없음 레이아웃
    private ImageButton ib_search;  // 검색 버튼
    private EditText et_search; // 검색창 input 값


    //리스트 선언
    private ArrayList<MainData> original_list; //모든 영화가 담긴 리스트
    private ArrayList<MainData> search_list; //검색 시 담을 아이템 리스트


    //어댑터 선언
    private  MainAdapter mainAdapter;
    //리사이클러 뷰에서 사용할
    private LinearLayoutManager linearLayoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // rootView 객체에 fragment_search.xml 과 연결한 것을 담기.
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search, container, false);

        //여기서부터 만짐//
        rec_search_list = (RecyclerView) rootView.findViewById(R.id.rec_search_list);
        lin_no_result = (LinearLayout) rootView.findViewById(R.id.lin_no_result);
        ib_search = (ImageButton) rootView.findViewById(R.id.ib_search);
        et_search = (EditText)  rootView.findViewById(R.id.et_search);

        linearLayoutManager = new LinearLayoutManager(getContext()); //???
        rec_search_list.setLayoutManager(linearLayoutManager);

        //리스트 생성
        original_list = new ArrayList<>();
        search_list = new ArrayList<>();

        //어댑터 생성
        mainAdapter = new MainAdapter(search_list);

        // 테스트용 임시 original_list
        MainData mainData1 = new MainData(R.drawable.movie_avatar, "avatar", "10", "100", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData1);
        MainData mainData2 = new MainData(R.drawable.movie_minari, "minari", "20", "200", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData2);
        MainData mainData3 = new MainData(R.drawable.movie_black, "black", "30", "300", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData3);
        MainData mainData4 = new MainData(R.mipmap.ic_launcher, "1234", "40", "400", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData4);
        MainData mainData5 = new MainData(R.mipmap.ic_launcher, "12345", "50", "500", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData5);
        MainData mainData6 = new MainData(R.mipmap.ic_launcher, "123456", "60", "600", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData6);
        MainData mainData7 = new MainData(R.mipmap.ic_launcher, "1234567", "70", "700", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData7);
        MainData mainData8 = new MainData(R.mipmap.ic_launcher, "12345678", "80", "700", "2019", "139분"); //아이템 추가하는 코드
        original_list.add(mainData8);


        // [돋보기] 버튼을 누르면 해당 키워드를 포함하는 아이템 검색해서 보여줌
        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String search_keyword = et_search.getText().toString();  //search_keyword 변수에 EditText 에 입력된 값 담기
                search_list.clear(); //search_list 초기화 (기존에 검색된 값이 있을 수 있으므로)


                //original_list에 있는 모든 아이템을 for 문으로 돌면서 검색
                for(int i=0; i<original_list.size(); i++){
                    //키워드를 포함하면 search_list에 해당 아이템 (= MainData 객체) 을 추가함
                    if(original_list.get(i).getTv_name().contains(search_keyword)){
                        search_list.add(original_list.get(i));
                    }
                }

                //어댑터의 data 를 search_list 로 갱신 (setItems()는 MainAdapter.java 에 구현 되어있음)
                mainAdapter.setItems(search_list);


                // 검색된 결과가 없을 때 -> "죄송합니다 해당 키워드가 없습니다" 레이아웃을 보이게 !
                if(search_list.isEmpty()){
                    rec_search_list.setVisibility(View.INVISIBLE);  // 리사이클러뷰 잠깐 안 보이게 설정
                    lin_no_result.setVisibility(View.VISIBLE);      // lin_no_result 레이아웃을 보이게 설정
                }
                // 있을 땐, 리사이클러뷰가 보이게 !
                else{
                    rec_search_list.setVisibility(View.VISIBLE);    // 리사이클러뷰 보이게
                    lin_no_result.setVisibility(View.INVISIBLE);    // lin_no_result 레이아웃 안 보이게
                }
            }
        });

        // 리사이클러뷰에게 어댑터 객체를 전송한다.
        // (검색결과가 없을 때도 리사이클러뷰에게 어댑터를 기본적으로 전송하도록 짜둠.)
        rec_search_list.setAdapter(mainAdapter);

        return rootView;
    }
}