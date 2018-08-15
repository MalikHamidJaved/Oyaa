package com.verbosetech.yoohoo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.MainActivity;
import com.verbosetech.yoohoo.adapters.ChatAdapter;
import com.verbosetech.yoohoo.interfaces.HomeIneractor;
import com.verbosetech.yoohoo.interfaces.OnUserGroupItemClick;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.views.MyRecyclerView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by a_man on 30-12-2017.
 */

public class MyGroupsFragment extends Fragment implements View.OnClickListener, OnUserGroupItemClick {
    private MyRecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private Realm rChatDb;
    private User userMe;
    private FloatingActionButton addConversation;
    private static String GROUP_CREATE_TAG = "groupcreatedialog";

    private RealmResults<Chat> resultList;
    private ArrayList<Chat> chatDataList = new ArrayList<>();

    private RealmChangeListener<RealmResults<Chat>> chatListChangeListener = new RealmChangeListener<RealmResults<Chat>>() {
        @Override
        public void onChange(RealmResults<Chat> element) {
            if (element != null && element.isValid() && element.size() > 0) {
                chatDataList.clear();
                chatDataList.addAll(rChatDb.copyFromRealm(element));
                chatAdapter.notifyDataSetChanged();
            }
        }
    };

    private HomeIneractor homeInteractor;

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

    public static MyGroupsFragment newInstance(User user) {
        MyGroupsFragment fragment = new MyGroupsFragment();
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
        Realm.init(getContext());
        rChatDb = Helper.getRealmInstance();
//        userMe = homeInteractor.getUserMe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_recycler, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setEmptyView(view.findViewById(R.id.emptyView));
        recyclerView.setEmptyImageView(((ImageView) view.findViewById(R.id.emptyImage)));
        TextView emptyTextView = view.findViewById(R.id.emptyText);
        emptyTextView.setText(getString(R.string.empty_group_chat_list));
        recyclerView.setEmptyTextView(emptyTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addConversation = view.findViewById(R.id.addConversation);
        addConversation.setVisibility(View.VISIBLE);
        addConversation.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RealmQuery<Chat> query = rChatDb.where(Chat.class).equalTo("myId", userMe.getId());//Query from chats whose owner is logged in user
        resultList = query.isNotNull("group").findAllSorted("timeUpdated", Sort.DESCENDING);//ignore forward list of messages and get rest sorted according to time

        chatDataList.clear();
        chatDataList.addAll(rChatDb.copyFromRealm(resultList));

        chatAdapter = new ChatAdapter(getActivity(), chatDataList);
        recyclerView.setAdapter(chatAdapter);

        resultList.addChangeListener(chatListChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (resultList != null)
            resultList.removeChangeListener(chatListChangeListener);
    }

    public void deleteSelectedChats() {
        rChatDb.beginTransaction();
        for (Chat chat : chatDataList) {
            if (chat.isSelected()) {
                Chat chatToDelete = rChatDb.where(Chat.class).equalTo("myId", userMe.getId()).equalTo("groupId", chat.getGroupId()).findFirst();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addConversation:
                GroupCreateDialogFragment.newInstance(this, userMe, MainActivity.myUsers).show(getChildFragmentManager(), GROUP_CREATE_TAG);
                break;
        }
    }

    @Override
    public void OnUserClick(User user, int position, View userImage) {

    }

    @Override
    public void OnGroupClick(Group group, int position, View userImage) {

    }
}
