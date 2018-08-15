package com.verbosetech.yoohoo;


import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;
import com.sinch.android.rtc.SinchError;
import com.verbosetech.yoohoo.activities.ChatActivity;
import com.verbosetech.yoohoo.adapters.MenuUsersRecyclerAdapter;
import com.verbosetech.yoohoo.adapters.ViewPagerAdapter;
import com.verbosetech.yoohoo.fragments.BaseFragment;
import com.verbosetech.yoohoo.fragments.GroupCreateDialogFragment;
import com.verbosetech.yoohoo.fragments.MyGroupsFragment;
import com.verbosetech.yoohoo.fragments.MyUsersFragment;
import com.verbosetech.yoohoo.fragments.UserSelectDialogFragment;
import com.verbosetech.yoohoo.interfaces.ContextualModeInteractor;
import com.verbosetech.yoohoo.interfaces.HomeIneractor;
import com.verbosetech.yoohoo.interfaces.OnUserGroupItemClick;
import com.verbosetech.yoohoo.interfaces.UserGroupSelectionDismissListener;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.RestartServiceReceiver;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.ConfirmationDialogFragment;
import com.verbosetech.yoohoo.utils.FetchMyUsersTask;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.ImageCompressorUtil;
import com.verbosetech.yoohoo.utils.OnTextChangeTextview;
import com.verbosetech.yoohoo.viewHolders.RequestPermissionHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MessageFragment extends BaseFragment implements HomeIneractor, OnUserGroupItemClick, View.OnClickListener, FetchMyUsersTask.FetchMyUsersTaskListener, ContextualModeInteractor, UserGroupSelectionDismissListener,
        SinchService.StartFailedListener {

    private View rootView;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE_CHAT_FORWARD = 99;
    private static final int REQUEST_CODE_MEDIA_PERMISSION = 999;
    private static final int REQUEST_CODE_PICKER = 1234;
    private static String USER_SELECT_TAG = "userselectdialog";
    private static String OPTIONS_MORE = "optionsmore";
    private static String GROUP_CREATE_TAG = "groupcreatedialog";
    private static String CONFIRM_TAG = "confirmtag";

    @BindView(R.id.users_image)
    CircleImageView usersImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.menu_recycler_view)
    RecyclerView menuRecyclerView;
    @BindView(R.id.menu_recycler_view_swipe_refresh)
    SwipeRefreshLayout swipeMenuRecyclerView;
    @BindView(R.id.menu_layout)
    FlowingMenuLayout menuLayout;
    @BindView(R.id.drawer_layout)
    FlowingDrawer drawerLayout;
    @BindView(R.id.searchContact)
    EditText searchContact;
    @BindView(R.id.invite)
    TextView invite;
    @BindView(R.id.toolbarContainer)
    RelativeLayout toolbarContainer;
    @BindView(R.id.cabContainer)
    RelativeLayout cabContainer;
    @BindView(R.id.selectedCount)
    TextView selectedCount;

    OnTextChangeTextview onTextChangeTextview;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.addConversation)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.back_button)
    ImageView backImage;

    CircleImageView dialogUserImage;
    ProgressBar dialogUserImageProgress;

    private MenuUsersRecyclerAdapter menuUsersRecyclerAdapter;
    private final int CONTACTS_REQUEST_CODE = 321;
    private ArrayList<Contact> contactsData = new ArrayList<>();
    private ArrayList<User> myUsers = new ArrayList<>();
    private ArrayList<Group> myGroups = new ArrayList<>();
    private ArrayList<Message> messageForwardList = new ArrayList<>();
    private UserSelectDialogFragment userSelectDialogFragment;
    private ViewPagerAdapter adapter;

    Map<String, ArrayList<String>> multimapUser = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal1 = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal2 = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> linkhmap = new LinkedHashMap<String, ArrayList<String>>();
    ArrayList<String> userValue = new ArrayList<String>();

    private RequestPermissionHandler mRequestPermissionHandler;


    public MessageFragment() {

    }


    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
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
            rootView = inflater.inflate(R.layout.fragment_message, container, false);
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
    public void groupAdded(Group group) {
        if (!myGroups.contains(group)) {
            myGroups.add(group);
            sortMyGroupsByName();
        }

    }

    @Override
    public void userUpdated(User value) {
        if (value.getId().equals(userMe.getId())) {
            userMe = value;
            setProfileImage(usersImage);
        } else if (helper.getCacheMyUsers() != null && helper.getCacheMyUsers().containsKey(value.getId())) {
            value.setNameInPhone(helper.getCacheMyUsers().get(value.getId()).getNameToDisplay());
            updateUser(value);
        } else {
            for (Contact savedContact : contactsData) {
                if (Helper.contactMatches(value.getId(), savedContact.getPhoneNumber())) {
                    value.setNameInPhone(savedContact.getName());
                    updateUser(value);
                    helper.setCacheMyUsers(myUsers);
                    break;
                }
            }
        }

    }

    private void updateUser(User value) {
        int existingPos = myUsers.indexOf(value);
        if (existingPos != -1) {
            myUsers.set(existingPos, value);
            menuUsersRecyclerAdapter.notifyItemChanged(existingPos);
            refreshUsers(existingPos);
        }
    }

    @Override
    public void groupUpdated(Group group) {
        int existingPos = myGroups.indexOf(group);
        if (existingPos != -1) {
            myGroups.set(existingPos, group);
            //menuUsersRecyclerAdapter.notifyItemChanged(existingPos);
            //refreshUsers(existingPos);
        }

    }

    private void addUser(User value) {
        if (!myUsers.contains(value)) {
            myUsers.add(value);
            sortMyUsersByName();
            menuUsersRecyclerAdapter.notifyDataSetChanged();
            refreshUsers(-1);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        actionBar.setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

//


        drawerLayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        //setup recyclerview in drawer layout
        setupMenu();


        //If its a url then load it, else Make a text drawable of user's name
        setProfileImage(usersImage);
        usersImage.setOnClickListener(this);
        backImage.setOnClickListener(this);
        invite.setOnClickListener(this);
        view.findViewById(R.id.action_delete).setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.setVisibility(View.VISIBLE);

        setupViewPager();
        getActivity().sendBroadcast(RestartServiceReceiver.newIntent(getActivity()));//start main chat service(responsible for fetching user's updates and Chat updates)
        fetchContacts();
        markOnline(true);
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new MyUsersFragment(), "Chats");
        adapter.addFrag(new MyGroupsFragment(), "Groups");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupMenu() {

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuUsersRecyclerAdapter = new MenuUsersRecyclerAdapter(getActivity(), myUsers, contactsData, linkhmap);
        menuRecyclerView.setAdapter(menuUsersRecyclerAdapter);
        swipeMenuRecyclerView.setColorSchemeResources(R.color.colorAccent);
        swipeMenuRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchContacts();
            }
        });
        searchContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filtertext = searchContact.getText().toString().trim().toLowerCase();
                if (menuUsersRecyclerAdapter != null) {
                    menuUsersRecyclerAdapter.filter(filtertext);
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

    private void setProfileImage(CircleImageView imageView) {
        Glide.with(this).load(userMe.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(imageView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CONTACTS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getActivity().sendBroadcast(RestartServiceReceiver.newIntent(getActivity()));//start main chat service(responsible for fetching user's updates and Chat updates)
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
            case REQUEST_CODE_MEDIA_PERMISSION:
                pickProfileImage();
                break;
        }

        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    private void fetchContacts() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            if (!swipeMenuRecyclerView.isRefreshing())
                swipeMenuRecyclerView.setRefreshing(true);
            //this returns users registerd on platform and in phone through callbacks
            new FetchMyUsersTask(getActivity(), userMe.getId()).execute();
            contactsData.clear();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, CONTACTS_REQUEST_CODE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Helper.CHAT_NOTIFY = true;
        markOnline(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_CODE_CHAT_FORWARD):
                if (resultCode == Activity.RESULT_OK) {
                    //show forward dialog to choose users
                    messageForwardList.clear();
                    ArrayList<Message> temp = data.getParcelableArrayListExtra("FORWARD_LIST");
                    messageForwardList.addAll(temp);
                    userSelectDialogFragment = UserSelectDialogFragment.newInstance(getActivity(), myUsers);
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Fragment frag = manager.findFragmentByTag(USER_SELECT_TAG);
                    if (frag != null) {
                        manager.beginTransaction().remove(frag).commit();
                    }
                    userSelectDialogFragment.show(manager, USER_SELECT_TAG);
                }
                break;

            case REQUEST_CODE_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    updateUserProfileImage(images.get(0).getPath());
                }
                break;
        }
    }

    private void updateUserProfileImage(String path) {
        //compress image
        File fileToUpload = new File(path);
        fileToUpload = ImageCompressorUtil.compressImage(getActivity(), fileToUpload);
        userImageUploadTask(fileToUpload, AttachmentTypes.IMAGE, null);
    }

    private void userImageUploadTask(final File fileToUpload, @AttachmentTypes.AttachmentType final int attachmentType, final Attachment attachment) {
        dialogUserImageProgress.setVisibility(View.VISIBLE);

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE).child(getString(R.string.app_name)).child("ProfileImage").child(userMe.getId());
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(fileToUpload));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialogUserImageProgress.setVisibility(View.GONE);
                //noinspection VisibleForTests
                StorageMetadata storageMetadata = taskSnapshot.getMetadata();
                Glide.with(getActivity()).load(fileToUpload).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(dialogUserImage);
                Glide.with(getActivity()).load(fileToUpload).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(usersImage);
                userMe.setImage(storageMetadata.getDownloadUrl().toString());
                usersRef.child(userMe.getId()).setValue(userMe);
                helper.setLoggedInUser(userMe);
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialogUserImageProgress.setVisibility(View.GONE);
            }
        });
    }

    private void sortMyGroupsByName() {
        Collections.sort(myGroups, new Comparator<Group>() {
            @Override
            public int compare(Group group1, Group group2) {
                return group1.getName().compareToIgnoreCase(group2.getName());
            }
        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                drawerLayout.openMenu(true);
                break;
            case R.id.addConversation:
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        drawerLayout.openMenu(true);
                        break;
                    case 1:
                        GroupCreateDialogFragment.newInstance(this, userMe, myUsers).show(getActivity().getSupportFragmentManager(), GROUP_CREATE_TAG);
//                        groupSelectDialogFragment = GroupSelectDialogFragment.newInstance(this, myGroups);
//                        FragmentManager manager = getSupportFragmentManager();
//                        Fragment frag = manager.findFragmentByTag(GROUP_SELECT_TAG);
//                        if (frag != null) {
//                            manager.beginTransaction().remove(frag).commit();
//                        }
//                        groupSelectDialogFragment.show(manager, GROUP_SELECT_TAG);
                        break;
                }
                break;
            case R.id.users_image:
                if (userMe != null)
//                    new OptionsFragment().show(getActivity().getSupportFragmentManager(), OPTIONS_MORE);
                break;
            case R.id.invite:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "YooHoo invitation");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.invitation_text), getActivity().getPackageName()));
                    startActivity(Intent.createChooser(shareIntent, "Share using.."));
                } catch (Exception ignored) {

                }
                break;
            case R.id.action_delete:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Fragment frag = manager.findFragmentByTag(CONFIRM_TAG);
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                ConfirmationDialogFragment confirmationDialogFragment = ConfirmationDialogFragment.newInstance("Delete chat",
                        "Continue deleting selected chats?",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((MyUsersFragment) adapter.getItem(0)).deleteSelectedChats();
                                ((MyGroupsFragment) adapter.getItem(1)).deleteSelectedChats();
                                disableContextualMode();
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                disableContextualMode();
                            }
                        });
                confirmationDialogFragment.show(manager, CONFIRM_TAG);
                break;
        }

    }

    private void pickProfileImage() {
        ImagePicker.create(getActivity())
                .folderMode(true)
                .theme(R.style.AppTheme)
                .single()
                .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
    }

    private List<String> mediaPermissions() {
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : PERMISSIONS_STORAGE) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
    }

    @Override
    public void enableContextualMode() {
        cabContainer.setVisibility(View.VISIBLE);
        toolbarContainer.setVisibility(View.GONE);

    }

    @Override
    public boolean isContextualMode() {
        return cabContainer.getVisibility() == View.VISIBLE;
    }

    @Override
    public void updateSelectedCount(int count) {
        if (count > 0) {
            selectedCount.setText(String.format("%d selected", count));
        } else {
            disableContextualMode();
        }

    }

    @Override
    public User getUserMe() {
        return userMe;
    }

    @Override
    public ArrayList<Contact> getLocalContacts() {
        return contactsData;
    }

    @Override
    public void OnUserClick(User user, int position, View userImage) {
        if (ElasticDrawer.STATE_CLOSED != drawerLayout.getDrawerState()) {
            drawerLayout.closeMenu(true);
        }
        if (userImage == null) {
            userImage = usersImage;
        }
        Intent intent = ChatActivity.newIntent(getActivity(), messageForwardList, user, false);
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), userImage, "backImage");
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD, options.toBundle());
        } else {
            Bundle transitionBundle = ActivityTransitionLauncher.with(getActivity()).from(userImage).createBundle();
            intent.putExtras(transitionBundle);
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD);
            getActivity().overridePendingTransition(0, 0);
        }

        if (userSelectDialogFragment != null)
            userSelectDialogFragment.dismiss();

    }

    @Override
    public void OnGroupClick(Group group, int position, View userImage) {
        Intent intent = ChatActivity.newIntent(getActivity(), messageForwardList, group, true);
        if (userImage == null) {
            userImage = usersImage;
        }
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), userImage, "backImage");
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD, options.toBundle());
        } else {
            Bundle transitionBundle = ActivityTransitionLauncher.with(getActivity()).from(userImage).createBundle();
            intent.putExtras(transitionBundle);
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD);
            getActivity().overridePendingTransition(0, 0);
        }

        if (userSelectDialogFragment != null)
            userSelectDialogFragment.dismiss();

    }

    private void refreshUsers(int pos) {
        Fragment frag = getActivity().getSupportFragmentManager().findFragmentByTag(USER_SELECT_TAG);
        if (frag != null) {
            userSelectDialogFragment.refreshUsers(pos);
        }
    }

    private void markOnline(boolean b) {
        //Mark online boolean as b in firebase
        usersRef.child(userMe.getId()).child("online").setValue(b);
    }


    @Override
    public void onUserGroupSelectDialogDismiss() {
        messageForwardList.clear();
//        if (helper.getSharedPreferenceHelper().getBooleanPreference(Helper.GROUP_CREATE, false)) {
//            helper.getSharedPreferenceHelper().setBooleanPreference(Helper.GROUP_CREATE, false);
//            GroupCreateDialogFragment.newInstance(this, userMe, myUsers).show(getSupportFragmentManager(), GROUP_CREATE_TAG);
//        }

    }

    @Override
    public void selectionDismissed() {

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
        menuUsersRecyclerAdapter.notifyDataSetChanged();
        swipeMenuRecyclerView.setRefreshing(false);

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
//        setupMenu();

    }

    public void disableContextualMode() {
        cabContainer.setVisibility(View.GONE);
        toolbarContainer.setVisibility(View.VISIBLE);
        ((MyUsersFragment) adapter.getItem(0)).disableContextualMode();
        ((MyGroupsFragment) adapter.getItem(1)).disableContextualMode();
    }

    @Override
    protected void onServiceConnected() {

    }

    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }
}
