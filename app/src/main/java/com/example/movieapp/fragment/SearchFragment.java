package com.example.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.activity.HomeActivity;
import com.example.movieapp.adapter.SearchFragmentAdapter;
import com.example.movieapp.data.MovieMainData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

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
    private ArrayList<MovieMainData> original_list; //모든 영화가 담긴 리스트
    private ArrayList<MovieMainData> search_list; //검색 시 담을 아이템 리스트


    //어댑터 선언
    private SearchFragmentAdapter adapter;
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
        adapter = new SearchFragmentAdapter(original_list, homeActivity);



        // 테스트용 임시 original_list
        MovieMainData mainData1 = new MovieMainData(R.drawable.testdata_spiderman, "스파이더맨", "9.27", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData1);
        MovieMainData mainData2 = new MovieMainData(R.drawable.testdata_spiderman2, "스파이더맨2", "8.32", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData2);
        MovieMainData mainData3 = new MovieMainData(R.drawable.testdata_spiderman3, "스파이더맨3", "7.08", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData3);
        MovieMainData mainData4 = new MovieMainData(R.drawable.testdata_the_amazing_spiderman, "어메이징 스파이더맨", "8.02", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData4);
        MovieMainData mainData5 = new MovieMainData(R.drawable.testdata_the_amazing_spiderman2, "어메이징 스파이더맨 2", "7.71", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData5);
        MovieMainData mainData6 = new MovieMainData(R.drawable.testdata_spiderman_homecomming, "스파이더맨 : 홈 커밍", "8.94", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData6);
        MovieMainData mainData7 = new MovieMainData(R.drawable.testdata_spiderman_farfromhome, "스파이더맨 : 파 프롬 홈", "8.36", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData7);
        MovieMainData mainData8 = new MovieMainData(R.drawable.testdata_spiderman_nowayhome, "스파이더맨 : 노 웨이 홈", "9.54", "2002", "121분","슈퍼히어로","d","d","D"); //아이템 추가하는 코드
        original_list.add(mainData8);


        // [돋보기] 버튼을 누르면 해당 키워드를 포함하는 아이템 검색해서 보여줌
        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String search_keyword = et_search.getText().toString();  //search_keyword 변수에 EditText 에 입력된 값 담기
                search_list.clear(); //search_list 초기화 (기존에 검색된 값이 있을 수 있으므로)


                if(search_keyword.length() > 0){
                    //original_list에 있는 모든 아이템을 for 문으로 돌면서 검색
                    for(int i=0; i<original_list.size(); i++){
                        //키워드를 포함하면 search_list에 해당 아이템 (= SearchFragmentMainData 객체) 을 추가함
                        if(original_list.get(i).getTv_name().contains(search_keyword)){
                            search_list.add(original_list.get(i));
                        }
                    }
                }

                //어댑터의 data 를 search_list 로 갱신 (setItems()는 SearchFragmentAdapter.java 에 구현 되어있음)
                adapter.setItems(search_list);


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
        rec_search_list.setAdapter(adapter);

        return rootView;
    }
}