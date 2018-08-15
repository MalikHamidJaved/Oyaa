package com.verbosetech.yoohoo.fragments;


import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.sinch.android.rtc.SinchError;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.ChatActivity;
import com.verbosetech.yoohoo.activities.MainActivity;
import com.verbosetech.yoohoo.adapters.ConnectAdapter;
import com.verbosetech.yoohoo.adapters.ViewPagerAdapter;
import com.verbosetech.yoohoo.interfaces.HomeIneractor;
import com.verbosetech.yoohoo.interfaces.OnUserGroupItemClick;
import com.verbosetech.yoohoo.interfaces.Test;
import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.RestartServiceReceiver;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.FetchMyUsersTask;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.viewHolders.RequestPermissionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FriendsFragment extends BaseFragment
        implements FetchMyUsersTask.FetchMyUsersTaskListener, Test, OnUserGroupItemClick, SinchService.StartFailedListener, HomeIneractor {

    private View rootView;
    //    private MenuUsersRecyclerAdapter menuUsersRecyclerAdapter;
    private ConnectAdapter connectAdapterl;
    private SwipeRefreshLayout menu_recycler_view_swipe_refresh;
    private RecyclerView menu_recycler_view;
    private EditText et_search_contact;
    private ArrayList<Contact> contactsData = new ArrayList<>();
    private ArrayList<User> myUsers = new ArrayList<>();
    private final int CONTACTS_REQUEST_CODE = 321;
    private static String USER_SELECT_TAG = "userselectdialog";

    Map<String, ArrayList<String>> multimapUser = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal1 = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal2 = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> linkhmap = new LinkedHashMap<String, ArrayList<String>>();

    private RequestPermissionHandler mRequestPermissionHandler;
    private UserSelectDialogFragment userSelectDialogFragment;
    ArrayList<String> userValue = new ArrayList<String>();
    private ViewPagerAdapter adapter;
    private static final int REQUEST_CODE_CHAT_FORWARD = 99;


    public FriendsFragment() {

    }


    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
//        args.putParcelableArrayList(Constant.USER_LIST,myUsers);
//        args.putParcelableArrayList(Constant.CONTACT_LIST, (ArrayList<? extends Parcelable>) contactsData);
//        args.putStringArrayList(Constant.LINKMAP_LIST,linkhmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void userAdded(User value) {
        if (value.getId().equals(userMe.getId()))
            return;
        else if (helper.getCacheMyUsers() != null && helper.getCacheMyUsers().containsKey(value.getId())) {
            value.setNameInPhone(helper.getCacheMyUsers().get(value.getId()).getNameToDisplay());
            addUser(value);
        } else {
            for (Contact savedContact : contactsData) {
                if (Helper.contactMatches(value.getId(), savedContact.getPhoneNumber())) {
                    value.setNameInPhone(savedContact.getName());
                    addUser(value);
                    helper.setCacheMyUsers(myUsers);
                    break;
                }
            }
        }

    }

    @Override
    public void groupAdded(Group valueGroup) {

    }

    @Override
    public void userUpdated(User valueUser) {

    }

    @Override
    public void groupUpdated(Group valueGroup) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_search_contact = view.findViewById(R.id.et_search_contact);
        menu_recycler_view = view.findViewById(R.id.menu_recycler_view);
        menu_recycler_view_swipe_refresh = view.findViewById(R.id.menu_recycler_view_swipe_refresh);
        setupMenu();
        getActivity().sendBroadcast(RestartServiceReceiver.newIntent(getContext()));
        fetchContacts();
    }

    private void refreshUsers(int pos) {
        Fragment frag = getChildFragmentManager().findFragmentByTag(USER_SELECT_TAG);
        if (frag != null) {
            userSelectDialogFragment.refreshUsers(pos);
//            menu_recycler_view_swipe_refresh.setRefreshing(false);
        }
    }

    private void addUser(User value) {
        if (!myUsers.contains(value)) {
            myUsers.add(value);
            sortMyUsersByName();
            connectAdapterl.notifyDataSetChanged();
            refreshUsers(-1);
        }
    }

    private void sortMyUsersByName() {
        Collections.sort(myUsers, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getNameToDisplay().compareToIgnoreCase(user2.getNameToDisplay());
            }
        });
    }

    @Override
    public void fetchMyUsersResult(ArrayList<User> myUsers) {
        helper.setCacheMyUsers(myUsers);
        this.myUsers.clear();
        this.myUsers.addAll(myUsers);
        refreshUsers(-1);
        for (int i = 0; i < myUsers.size(); i++) {
            userValue = new ArrayList<String>();
            userValue.add(myUsers.get(i).getNameInPhone());
            userValue.add(myUsers.get(i).getName());
            userValue.add(String.valueOf(myUsers.get(i).isOnline()));
            userValue.add("0");
            multimapUser.put(myUsers.get(i).getNameInPhone(), userValue);
        }


        Set<Map.Entry<String, ArrayList<String>>> entries = multimapUser.entrySet();
        List<Map.Entry<String, ArrayList<String>>> listentry = new ArrayList<Map.Entry<String, ArrayList<String>>>(entries);
        Collections.sort(listentry, new Comparator<Map.Entry<String, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<String, ArrayList<String>> o1, Map.Entry<String, ArrayList<String>> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });


        for (Map.Entry<String, ArrayList<String>> map : listentry) {
            linkhmap.put(map.getKey(), map.getValue());
        }
        List<String> keyss = new ArrayList<>(linkhmap.keySet());
        Object[] keys = linkhmap.keySet().toArray();
        for (int k = 0; k < linkhmap.size(); k++) {
            if (linkhmap.get(keys[k]).get(3) == "0") {
                multimapUserfinal.put(String.valueOf(keys[k]), linkhmap.get(keys[k]));
                Object obj = linkhmap.remove(keyss.get(k));
                System.out.print("0");
            }
        }
        Object[] keys1 = linkhmap.keySet().toArray();

        for (int k = 0; k < linkhmap.size(); k++) {
            if (linkhmap.get(keys1[k]).get(3) == "1") {
                multimapUserfinal1.put(String.valueOf(keys1[k]), linkhmap.get(keys1[k]));
                System.out.print("0");
            }
        }

        linkhmap.putAll(multimapUserfinal);
        linkhmap.putAll(multimapUserfinal1);
//        for (Map.Entry<String, ArrayList<String>> entry : multimapUserfinal.entrySet())
//        {
//            linkhmap.put(entry.getKey(),entry.getValue());
//        }
//        for (Map.Entry<String, ArrayList<String>> entry : multimapUserfinal1.entrySet())
//        {
//            linkhmap.put(entry.getKey(),entry.getValue());
//        }
//        Log.d("hello+++++++++++", "" + linkhmap);
        for (int i = 0; i < myUsers.size(); i++) {
            for (int j = 0; j < contactsData.size(); j++) {
                if (myUsers.get(i).getName().equals(contactsData.get(j).getPhoneNumber())) {
                    contactsData.remove(j);
                }
            }
        }
        setupMenu();
        connectAdapterl.notifyDataSetChanged();
        menu_recycler_view_swipe_refresh.setRefreshing(false);

    }

    @Override
    public void fetchMyContactsResult(ArrayList<Contact> myContacts) {
        contactsData.clear();
        Set<Contact> contactset = new HashSet<>();
        contactset.addAll(myContacts);
        myContacts.clear();
        myContacts.addAll(contactset);
        Collections.sort(myContacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });


        contactsData.addAll(myContacts);
        for (int j = 0; j < contactsData.size(); j++) {
            userValue = new ArrayList<String>();
            userValue.add(contactsData.get(j).getName());
            userValue.add(contactsData.get(j).getPhoneNumber());
            userValue.add("");
            userValue.add("1");
//            userValue.add("0");
//            userValue.add("");
//            userValue.add("1");
//            userValue.add(contactsData.get(j).getPhoneNumber());
            multimapUser.put(contactsData.get(j).getName(), userValue);

        }
        MyUsersFragment myUsersFragment = ((MyUsersFragment) adapter.getItem(0));
        if (myUsersFragment != null) myUsersFragment.setUserNamesAsInPhone();

    }

    private void setupMenu() {
        menu_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        connectAdapterl = new ConnectAdapter(this, MainActivity.myUsers, MainActivity.contactsData, MainActivity.linkhmap);
        menu_recycler_view.setAdapter(connectAdapterl);
        menu_recycler_view_swipe_refresh.setColorSchemeResources(R.color.colorAccent);
        menu_recycler_view_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchContacts();
                menu_recycler_view_swipe_refresh.setRefreshing(false);
            }
        });
        et_search_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filtertext = et_search_contact.getText().toString().trim().toLowerCase();
                if (connectAdapterl != null) {
                    connectAdapterl.filter(filtertext);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

//                Log.d("ffff", "" + searchContact.getText().toString().length());
//                if (MainActivity.this instanceof OnTextChangeTextview) {
//                    ((OnTextChangeTextview) MainActivity.this).TextChangeTextview("" + searchContact.getText().toString().length(),
//                            searchContact.getText().toString().trim());
//                }

//                onTextChangeTextview.TextChangeTextview(""+searchContact.getText().toString().length());

//                menuUsersRecyclerAdapter.getFilter().filter(editable.toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CONTACTS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getActivity().sendBroadcast(RestartServiceReceiver.newIntent(getContext()));//start main chat service(responsible for fetching user's updates and Chat updates)
                fetchContacts();
                mRequestPermissionHandler = new RequestPermissionHandler();
                mRequestPermissionHandler.requestPermission(getActivity(), new String[]{
                        Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 123, new RequestPermissionHandler.RequestPermissionListener() {
                    @Override
                    public void onSuccess() {
//                Toast.makeText(ChatDetailActivity.this, "request permission success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed() {
//                Toast.makeText(ChatDetailActivity.this, "request permission failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
//            case REQUEST_CODE_MEDIA_PERMISSION:
//                pickProfileImage();
//                break;
        }

        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    private void fetchContacts() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            if (!menu_recycler_view_swipe_refresh.isRefreshing())
                menu_recycler_view_swipe_refresh.setRefreshing(false);
            //this returns users registerd on platform and in phone through callbacks
            new FetchMyUsersTask(getContext(), userMe.getId()).execute();
            contactsData.clear();
            setupMenu();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, CONTACTS_REQUEST_CODE);
        }
    }

    @Override
    public void TestClick(ArrayList<Contact> contactsData, ArrayList<User> myUsers, Map<String, ArrayList<String>> linkhmap) {

    }

    @Override
    public void OnUserClick(User user, int position, View userImage) {
//        if (userImage == null) {
//            userImage = usersImage;
//        }


//        Chat(messageForwardList, user, false);

        Intent intent = ChatActivity.newIntent(getContext(), MainActivity.messageForwardList, user, false);
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), userImage, "backImage");
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD, options.toBundle());
        } else {
            Bundle transitionBundle = ActivityTransitionLauncher.with(getActivity()).from(userImage).createBundle();
            intent.putExtras(transitionBundle);
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD);
            getActivity().overridePendingTransition(0, 0);
        }

    }

    @Override
    public void OnGroupClick(Group group, int position, View userImage) {

    }

    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public User getUserMe() {
        return userMe;
    }

    @Override
    public ArrayList<Contact> getLocalContacts() {
        return contactsData;
    }
}
