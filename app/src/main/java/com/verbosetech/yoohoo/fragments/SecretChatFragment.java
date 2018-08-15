package com.verbosetech.yoohoo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.adapters.ChatAdapter;
import com.verbosetech.yoohoo.adapters.SecretChatAdapter;
import com.verbosetech.yoohoo.interfaces.HomeIneractor;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.SecretChat;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.views.MyRecyclerView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


public class SecretChatFragment extends Fragment {
    private MyRecyclerView recyclerView;
    private SecretChatAdapter chatAdapter;

    private Realm rChatDb;
    private User userMe;
    private RealmResults<SecretChat> resultList;
    private ArrayList<SecretChat> chatDataList = new ArrayList<>();

    private RealmChangeListener<RealmResults<SecretChat>> chatListChangeListener = new RealmChangeListener<RealmResults<SecretChat>>() {
        @Override
        public void onChange(RealmResults<SecretChat> element) {
            if (element != null && element.isValid() && element.size() > 0) {
                chatDataList.clear();
                chatDataList.addAll(rChatDb.copyFromRealm(element));
                setUserNamesAsInPhone();
            }
        }
    };
    private HomeIneractor homeInteractor;
    private Helper helper;


    public SecretChatFragment() {

    }


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


    public static SecretChatFragment newInstance(User user) {
        SecretChatFragment fragment = new SecretChatFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userMe = getArguments().getParcelable("user");
        }
        helper = new Helper(getContext());
//        userMe = homeInteractor.getUserMe();
        Realm.init(getContext());
        rChatDb = Helper.getRealmInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_recycler, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setEmptyView(view.findViewById(R.id.emptyView));
        recyclerView.setEmptyImageView(((ImageView) view.findViewById(R.id.emptyImage)));
        recyclerView.setEmptyTextView(((TextView) view.findViewById(R.id.emptyText)));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RealmQuery<SecretChat> query = rChatDb.where(SecretChat.class).equalTo("myId", userMe.getId());//Query from chats whose owner is logged in user
        resultList = query.isNotNull("user").findAllSorted("timeUpdated", Sort.DESCENDING);//ignore forward list of messages and get rest sorted according to time

        chatDataList.clear();
        chatDataList.addAll(rChatDb.copyFromRealm(resultList));
        chatAdapter = new SecretChatAdapter(getActivity(), chatDataList);
        recyclerView.setAdapter(chatAdapter);

        resultList.addChangeListener(chatListChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (resultList != null)
            resultList.removeChangeListener(chatListChangeListener);
    }

    //Display user's name as saved in phone!
    public void setUserNamesAsInPhone() {
        if (chatDataList != null) {
            for (SecretChat chat : chatDataList) {
                User user = chat.getUser();
                if (user != null) {
                    if (helper.getCacheMyUsers() != null && helper.getCacheMyUsers().containsKey(user.getId())) {
                        user.setNameInPhone(helper.getCacheMyUsers().get(user.getId()).getNameToDisplay());
                    } else {
                        for (Contact savedContact : homeInteractor.getLocalContacts()) {
                            if (Helper.contactMatches(user.getId(), savedContact.getPhoneNumber())) {
                                if (user.getNameInPhone() == null || !user.getNameInPhone().equals(savedContact.getName())) {
                                    user.setNameInPhone(savedContact.getName());
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (chatAdapter != null)
            chatAdapter.notifyDataSetChanged();
    }

    public void deleteSelectedChats() {
        rChatDb.beginTransaction();
        for (SecretChat chat : chatDataList) {
            if (chat.isSelected()) {
                Chat chatToDelete = rChatDb.where(Chat.class).equalTo("myId", userMe.getId()).equalTo("userId", chat.getUserId()).findFirst();
                if (chatToDelete != null) {
                    chatToDelete.deleteFromRealm();
                }
            }
        }
        rChatDb.commitTransaction();
    }

    public void disableContextualMode() {
        chatAdapter.disableContextualMode();
    }


}
