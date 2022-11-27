package com.example.movieapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieRecoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieRecoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieRecoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieRecoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieRecoFragment newInstance(String param1, String param2) {
        MovieRecoFragment fragment = new MovieRecoFragment();
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

    static FirebaseAuth mAuth;
    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference userReference = database.getReference();

    RecyclerView mRecyclerView;

    public List<List<String>> dataList;
    public ArrayList<String> resultList; // 추천 알고리즘 결과값
    public ArrayList<String> MBTIList; // 사용자 엠비티아이 리스트
    private ArrayList<MovieItem> movieList; // MovieItem 타입의 리스트 for 리사이클러 뷰

    private RecoAdapter recoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_reco, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerViewReco);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        resultList = new ArrayList<>();
        MBTIList = new ArrayList<>();
        movieList = new ArrayList<>();

        dataList = DataListReady.data_list;

        readMbti();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recoAdapter = new RecoAdapter(movieList);

        System.out.println("2 resultList" + resultList);
        for (int i = 0; i < resultList.size(); i++) {
            movieList.add(new MovieItem(R.drawable.movie_black, resultList.get(i)));
        }
        System.out.println("3 movieList" + movieList);
        mRecyclerView.setAdapter(recoAdapter);

        return rootView;
    }

    private void readMbti(){
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();

        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);

                String mbti = user.getMbti();
                mbti = mbti.replace("[", "");
                mbti = mbti.replace("]", "");

                MBTIList = new ArrayList<>(Arrays.asList(mbti.split(",")));

                int cnt = 0;
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).get(1).equals(MBTIList.get(0))) cnt++;
                    if (dataList.get(i).get(2).equals(MBTIList.get(1))) cnt++;
                    if (dataList.get(i).get(3).equals(MBTIList.get(2))) cnt++;

                    if (dataList.get(i).get(4).equals(MBTIList.get(3))) cnt++;

                    if (cnt >= 3) resultList.add(dataList.get(i).get(0));
                }
                System.out.println("1 resultList" + resultList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class Thread1 extends Thread {
        int idx;

        public Thread1 (int idx) {
            this.idx = idx;
        }

        public void run() {
            if (idx == 1) {
                readMbti();
            }
            else if (idx == 2) {
                recoAdapter = new RecoAdapter(movieList);

                System.out.println("2 resultList" + resultList);
                for (int i = 0; i < resultList.size(); i++) {
                    movieList.add(new MovieItem(R.drawable.movie_black, resultList.get(i)));
                }
                System.out.println("3 movieList" + movieList);
                mRecyclerView.setAdapter(recoAdapter);
            }
        }
    }
}