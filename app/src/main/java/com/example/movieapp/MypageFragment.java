package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageFragment extends Fragment {
    HomeActivity activity;

//    ImageButton ib_more = (ImageButton) view.findViewById(R.id.ib_more);

    //firebase로 로그인한 사용자 정보 불러오기
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference();

    public static MypageFragment newInstance() {
        return new MypageFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (HomeActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
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
        //Firebase 로그인한 사용자 정보
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();
        readUser(userUid);
        modifyUser(userUid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        Thread mThread = new Thread();
        mThread.start();

        ArrayList<UserAccount> data_userinfo = new ArrayList<>();

        TextView tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
//        tv_nickname.setText(user.getDisplayName());
        return view;
    }

    //로그인 user의 name, mbti읽기
    private void readUser(String userUid){
        userReference.child("user").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);

                //각각의 값 가져오기
                //이거 화면에 옮기면 됨
                String name = user.getName();
                String mbti = user.getMbti();

                Log.d("name", name);
                Log.d("mbti", mbti);
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
                //나중에 text 받아서 괄호안에 값 바꿔야됨
                user.setName("abc");
                user.setMbti("ENFP");

                //값 바뀐거 확인 완료되면 지우기
                String name = user.getName();
                String mbti = user.getMbti();
                Log.d("name", name);
                Log.d("mbti", mbti);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}