package com.verbosetech.yoohoo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.views.WorkingInProgressDialog;


public class MoreFragment extends BaseFullDialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private LinearLayout ll_profile, ll_privacy_setting, ll_blocked_list, ll_terms_privacy, ll_nearby;
    private User userMe;
    private Helper helper;
    TextView tvHeader;
    private DatabaseReference usersRef;
    private static String PRIVACY_TAG = "privacytag";
    private static String PROFILE_TAG = "profiletag";
    private static String PRIVACY_SETTING_TAG = "privacysettingtag";


    private String mParam1;
    private String mParam2;


    public MoreFragment() {

    }


    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
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
        try {
            rootView = inflater.inflate(R.layout.fragment_more, container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new Helper(getContext());
        userMe = helper.getLoggedInUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference(Helper.REF_USER);

        tvHeader = view.findViewById(R.id.tv_toolbar);

        ll_profile = view.findViewById(R.id.ll_profile);
        ll_profile.setOnClickListener(this);

        ll_privacy_setting = view.findViewById(R.id.ll_privacy_setting);
        ll_privacy_setting.setOnClickListener(this);

        ll_blocked_list = view.findViewById(R.id.ll_blocked_list);
        ll_blocked_list.setOnClickListener(this);

        ll_terms_privacy = view.findViewById(R.id.ll_terms_privacy);
        ll_terms_privacy.setOnClickListener(this);

        ll_nearby = view.findViewById(R.id.ll_nearby);
        ll_nearby.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_profile:
                new MyProfileDialogFragment().show(getChildFragmentManager(), PROFILE_TAG);
                break;
            case R.id.ll_privacy_setting:
                new PrivacySettingDialogFragment().show(getChildFragmentManager(),PRIVACY_SETTING_TAG);
                break;
            case R.id.ll_blocked_list:
                break;
            case R.id.ll_terms_privacy:
                new PrivacyPolicyDialogFragment().show(getChildFragmentManager(), PRIVACY_TAG);
                break;
            case R.id.ll_nearby:

                changeFragment();
                break;

        }

    }

    private void changeFragment() {
        new WorkingInProgressDialog(getContext()).show();

//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        tvHeader.setText("Secret Chats");
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack if needed
//        transaction.replace(R.id.fragment_container, SecretChatFragment.newInstance(userMe));
//        transaction.addToBackStack(null);
//
//// Commit the transaction
//        transaction.commit();
    }
}
