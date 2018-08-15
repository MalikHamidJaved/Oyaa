package com.verbosetech.yoohoo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.MainActivity;
import com.verbosetech.yoohoo.adapters.CallHistoryAdapter;
import com.verbosetech.yoohoo.interfaces.HomeIneractor;
import com.verbosetech.yoohoo.interfaces.OnCallHistoryItemCleckListener;
import com.verbosetech.yoohoo.models.Call;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.views.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


public class CallsFragment extends Fragment implements OnCallHistoryItemCleckListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View rootView;


    private String mParam1;
    private String mParam2;
    private CallHistoryAdapter callHistoryAdapter;
    private RecyclerView rvContacts;

    private DatabaseReference rChatDb;
    private User userMe;

    private RealmResults<Call> resultList;
    private ArrayList<Call> chatDataList = new ArrayList<>();


    private HomeIneractor homeInteractor;
    private Helper helper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            homeInteractor = (HomeIneractor) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HomeIneractor");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        homeInteractor = null;
    }


    public CallsFragment() {

    }


    public static CallsFragment newInstance(User user) {
        CallsFragment fragment = new CallsFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userMe = getArguments().getParcelable("user");
        }

//        userMe = homeInteractor.getUserMe();
        Realm.init(getContext());
        rChatDb =  FirebaseDatabase.getInstance().getReference();

        rChatDb.child("calls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatDataList.clear();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren())
                {

                    Call note = noteDataSnapshot.getValue(Call.class);
                    String userId = ((MainActivity)getActivity()).getUserMe().getId();
                    if(note.getSenderId().equals(userId) || note.getRecipientId().equals(userId))
                    chatDataList.add(note);
                }


                callHistoryAdapter.setChecklists(chatDataList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calls, container, false);

        rvContacts = view.findViewById(R.id.rv_calls);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);
        rvContacts.setLayoutManager(layoutManager3);
        callHistoryAdapter = new CallHistoryAdapter( this);
        rvContacts.setAdapter(callHistoryAdapter);




    }

    @Override
    public void onCallHistoryItemClickListerner(int idx) {

    }
}
