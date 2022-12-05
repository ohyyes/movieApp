package com.example.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    // private ImageButton ib_back;

    // 선택된 영화 객체
    private SearchFragmentMainData movieData;
    // 화면에 띄울 뷰
    private ImageView iv_poster;
    private TextView tv_name, tv_rating, tv_date, tv_running_time, tv_genre, tv_director, tv_actor, tv_summary;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // 선택된 영화 객체 받아오기
        if (getArguments() != null) {
            movieData = (SearchFragmentMainData) getArguments().getSerializable("fromSearchFragment");
        }

        // fragment_movie_detail.xml 의 뷰 가져오기
        iv_poster = (ImageView) rootView.findViewById(R.id.iv_poster);
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_rating = (TextView) rootView.findViewById(R.id.tv_rating);
        tv_date = (TextView) rootView.findViewById(R.id.tv_date);
        tv_running_time = (TextView) rootView.findViewById(R.id.tv_running_time);
        tv_genre = (TextView) rootView.findViewById(R.id.tv_genre);
        tv_director = (TextView) rootView.findViewById(R.id.tv_director);
        tv_actor = (TextView) rootView.findViewById(R.id.tv_actor);
        tv_summary = (TextView) rootView.findViewById(R.id.tv_summary);

        // 영화 데이터를 뷰에 설정
        iv_poster.setImageBitmap(movieData.getPoster());
        tv_name.setText(movieData.getTitle());
        tv_rating.setText(movieData.getUserRating());
        tv_date.setText(movieData.getOpenYear());
        tv_running_time.setText(movieData.getRunningTime());
        tv_genre.setText(movieData.getGenre());
        tv_name.setText(movieData.getTitle());
        tv_director.setText(movieData.getDirector());
        tv_actor.setText(movieData.getActors());
        tv_summary.setText(movieData.getStory());

        return rootView;
    }
}