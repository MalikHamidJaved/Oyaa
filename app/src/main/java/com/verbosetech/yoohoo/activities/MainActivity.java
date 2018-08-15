package com.verbosetech.yoohoo.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.adapters.MenuUsersRecyclerAdapter;
import com.verbosetech.yoohoo.adapters.ViewPagerAdapter;
import com.verbosetech.yoohoo.fragments.CallsFragment;
import com.verbosetech.yoohoo.fragments.FriendsFragment;
import com.verbosetech.yoohoo.fragments.GroupCreateDialogFragment;
import com.verbosetech.yoohoo.fragments.HomeFragment;
import com.verbosetech.yoohoo.fragments.MoreFragment;
import com.verbosetech.yoohoo.fragments.MyGroupsFragment;
import com.verbosetech.yoohoo.fragments.MyUsersFragment;
import com.verbosetech.yoohoo.fragments.OptionsFragment;
import com.verbosetech.yoohoo.fragments.ShopFragment;
import com.verbosetech.yoohoo.fragments.UserSelectDialogFragment;
import com.verbosetech.yoohoo.interfaces.ContextualModeInteractor;
import com.verbosetech.yoohoo.interfaces.HomeIneractor;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.interfaces.OnUserGroupItemClick;
import com.verbosetech.yoohoo.interfaces.Test;
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
import com.verbosetech.yoohoo.utils.ConstantMethod;
import com.verbosetech.yoohoo.utils.FetchMyUsersTask;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.ImageCompressorUtil;
import com.verbosetech.yoohoo.utils.OnTextChangeTextview;
import com.verbosetech.yoohoo.utils.SoftKeyboardStateWatcher;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentRecordingViewHolder;
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

public class MainActivity extends BaseActivity implements HomeIneractor, OnUserGroupItemClick, View.OnClickListener, FetchMyUsersTask.FetchMyUsersTaskListener, ContextualModeInteractor, UserGroupSelectionDismissListener,
        SinchService.StartFailedListener, OnMessageItemClick, MessageAttachmentRecordingViewHolder.RecordingViewInteractor, SoftKeyboardStateWatcher.SoftKeyboardStateListener {

    //
//        HomeIneractor, OnUserGroupItemClick, View.OnClickListener, FetchMyUsersTask.FetchMyUsersTaskListener, ContextualModeInteractor,
//        UserGroupSelectionDismissListener,
//        SinchService.StartFailedListener
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE_CHAT_FORWARD = 99;
    private static final int REQUEST_CODE_MEDIA_PERMISSION = 999;
    private static final int REQUEST_CODE_PICKER = 1234;
    private static String USER_SELECT_TAG = "userselectdialog";
    private static String OPTIONS_MORE = "optionsmore";
    private static String GROUP_CREATE_TAG = "groupcreatedialog";
    private static String CONFIRM_TAG = "confirmtag";


//    private ArrayList<Message> dataList = new ArrayList<>();
//
//    @BindView(R.id.users_image)
//    CircleImageView usersImage;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.menu_recycler_view)
//    RecyclerView menuRecyclerView;
//    @BindView(R.id.menu_recycler_view_swipe_refresh)
//    SwipeRefreshLayout swipeMenuRecyclerView;
//    @BindView(R.id.menu_layout)
//    FlowingMenuLayout menuLayout;
//    @BindView(R.id.drawer_layout)
//    FlowingDrawer drawerLayout;
//    @BindView(R.id.searchContact)
//    EditText searchContact;
//    @BindView(R.id.invite)
//    TextView invite;
//    @BindView(R.id.toolbarContainer)
//    RelativeLayout toolbarContainer;
//    @BindView(R.id.cabContainer)
//    RelativeLayout cabContainer;
//    @BindView(R.id.selectedCount)
//    TextView selectedCount;
////
//    OnTextChangeTextview onTextChangeTextview;
//
//    @BindView(R.id.tabLayout)
//    TabLayout tabLayout;
//    @BindView(R.id.viewPager)
//    ViewPager viewPager;
//
//    @BindView(R.id.addConversation)
//    FloatingActionButton floatingActionButton;
//    @BindView(R.id.coordinatorLayout)
//    CoordinatorLayout coordinatorLayout;
//
//    @BindView(R.id.back_button)
//    ImageView backImage;
//
//    RelativeLayout toolbarContent;
//    @BindView(R.id.selectedCount)
//
//
////    CircleImageView dialogUserImage;
////    ProgressBar dialogUserImageProgress;
////    private FrameLayout frame_container;
//
//
////    private MenuUsersRecyclerAdapter menuUsersRecyclerAdapter;
////    private final int CONTACTS_REQUEST_CODE = 321;
//     ArrayList<Contact> contactsData = new ArrayList<>();
//     ArrayList<User> myUsers = new ArrayList<>();
//     ArrayList<Group> myGroups = new ArrayList<>();
//     ArrayList<Message> messageForwardList = new ArrayList<>();
//     UserSelectDialogFragment userSelectDialogFragment;
//     ViewPagerAdapter adapter;
////
//    Map<String, ArrayList<String>> multimapUser = new HashMap<String, ArrayList<String>>();
//    Map<String, ArrayList<String>> multimapUserfinal = new HashMap<String, ArrayList<String>>();
//    Map<String, ArrayList<String>> multimapUserfinal1 = new HashMap<String, ArrayList<String>>();
//    Map<String, ArrayList<String>> multimapUserfinal2 = new HashMap<String, ArrayList<String>>();
//    Map<String, ArrayList<String>> linkhmap = new LinkedHashMap<String, ArrayList<String>>();
//    ArrayList<String> userValue = new ArrayList<String>();
//
//    private RequestPermissionHandler mRequestPermissionHandler;
//
//    MessageAdapter messageAdapter;
//    private int countSelected = 0;

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
    public static ArrayList<Contact> contactsData = new ArrayList<>();
    public static ArrayList<User> myUsers = new ArrayList<>();
    private ArrayList<Group> myGroups = new ArrayList<>();
    public static ArrayList<Message> messageForwardList = new ArrayList<>();
    private UserSelectDialogFragment userSelectDialogFragment;
    private ViewPagerAdapter adapter;

    Map<String, ArrayList<String>> multimapUser = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal1 = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> multimapUserfinal2 = new HashMap<String, ArrayList<String>>();
    public static Map<String, ArrayList<String>> linkhmap = new LinkedHashMap<String, ArrayList<String>>();
    ArrayList<String> userValue = new ArrayList<String>();

    private RequestPermissionHandler mRequestPermissionHandler;

    private RadioGroup rg_bottom_navigation, rg_bottom_text;
    private RadioButton rb_message, rb_message_text, rb_call, rb_call_text, rb_friends, rb_friends_text, rb_shop, rb_shop_text, rb_more, rb_more_text;
    private RelativeLayout rl_bottom_navigation, rl_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        rb_message = findViewById(R.id.rb_message);
        rb_call = findViewById(R.id.rb_call);
        rb_friends = findViewById(R.id.rb_friends);
        rb_shop = findViewById(R.id.rb_shop);
        rb_more = findViewById(R.id.rb_more);
        rb_message_text = findViewById(R.id.rb_message_text);
        rb_call_text = findViewById(R.id.rb_call_text);
        rb_friends_text = findViewById(R.id.rb_friends_text);
        rb_shop_text = findViewById(R.id.rb_shop_text);
        rb_more_text = findViewById(R.id.rb_more_text);

        rb_message.setChecked(true);
        rb_message_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        sendBroadcast(RestartServiceReceiver.newIntent(getApplicationContext()));
        Home();


//        frame_container = findViewById(R.id.frame_container);

        rl_bottom_navigation = findViewById(R.id.rl_bottom_navigation);
        rl_main = findViewById(R.id.rl_main);
        rg_bottom_navigation = findViewById(R.id.rg_bottom_navigation);
        rg_bottom_navigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_message:
                        Home();
                        rb_message_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        rb_call_text.setTextColor(getResources().getColor(R.color.black));
                        rb_friends_text.setTextColor(getResources().getColor(R.color.black));
                        rb_shop_text.setTextColor(getResources().getColor(R.color.black));
                        rb_more_text.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case R.id.rb_call:
                        Calls();
                        rb_call_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        rb_friends_text.setTextColor(getResources().getColor(R.color.black));
                        rb_shop_text.setTextColor(getResources().getColor(R.color.black));
                        rb_more_text.setTextColor(getResources().getColor(R.color.black));
                        rb_message_text.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case R.id.rb_friends:
//                        test.TestClick(contactsData, myUsers, linkhmap);
                        Friends();
                        rb_friends_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        rb_shop_text.setTextColor(getResources().getColor(R.color.black));
                        rb_more_text.setTextColor(getResources().getColor(R.color.black));
                        rb_message_text.setTextColor(getResources().getColor(R.color.black));
                        rb_call_text.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case R.id.rb_shop:
                        Shop();
                        rb_shop_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        rb_more_text.setTextColor(getResources().getColor(R.color.black));
                        rb_message_text.setTextColor(getResources().getColor(R.color.black));
                        rb_call_text.setTextColor(getResources().getColor(R.color.black));
                        rb_friends_text.setTextColor(getResources().getColor(R.color.black));

                        break;
                    case R.id.rb_more:
//                        Options();
                        More();

                        rb_more_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        rb_message_text.setTextColor(getResources().getColor(R.color.black));
                        rb_call_text.setTextColor(getResources().getColor(R.color.black));
                        rb_friends_text.setTextColor(getResources().getColor(R.color.black));
                        rb_shop_text.setTextColor(getResources().getColor(R.color.black));
                        break;
                }
            }
        });
        rg_bottom_text = findViewById(R.id.rg_bottom_text);
        rg_bottom_text.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_message_text:
                        break;
                    case R.id.rb_call_text:
                        break;
                    case R.id.rb_friends_text:
                        break;
                    case R.id.rb_shop_text:
                        break;
                    case R.id.rb_more_text:
                        break;
                }
            }
        });

//


        SoftKeyboardStateWatcher soft_keyboard = new SoftKeyboardStateWatcher(rl_main, this);
        soft_keyboard.addSoftKeyboardStateListener(this);

        drawerLayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        //setup recyclerview in drawer layout
        setupMenu();


        //If its a url then load it, else Make a text drawable of user's name
        setProfileImage(usersImage);
        usersImage.setOnClickListener(this);
        backImage.setOnClickListener(this);
        invite.setOnClickListener(this);
        findViewById(R.id.action_delete).setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.setVisibility(View.GONE);

        setupViewPager();
        sendBroadcast(RestartServiceReceiver.newIntent(this));//start main chat service(responsible for fetching user's updates and Chat updates)
        fetchContacts();
        markOnline(true);

//        messageAdapter = new MessageAdapter(this, dataList, userMe.getId(), null);
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MyUsersFragment(), "Chats");
        adapter.addFrag(new MyGroupsFragment(), "Groups");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupMenu() {

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuUsersRecyclerAdapter = new MenuUsersRecyclerAdapter(this, myUsers, contactsData, linkhmap);
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
                    sendBroadcast(RestartServiceReceiver.newIntent(this));//start main chat service(responsible for fetching user's updates and Chat updates)
                fetchContacts();
                mRequestPermissionHandler = new RequestPermissionHandler();
                mRequestPermissionHandler.requestPermission(this, new String[]{
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            if (!swipeMenuRecyclerView.isRefreshing())
                swipeMenuRecyclerView.setRefreshing(true);
            //this returns users registerd on platform and in phone through callbacks
            new FetchMyUsersTask(this, userMe.getId()).execute();
            contactsData.clear();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS}, CONTACTS_REQUEST_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Helper.CHAT_NOTIFY = true;
        markOnline(false);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            int index = getSupportFragmentManager().getBackStackEntryCount() - 2;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            super.onBackPressed();
            String tag = backEntry.getName();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null) {
                Class name = fragment.getClass();
                if (name == HomeFragment.class) {
                    Home();
//                    bnve.setSelectedItemId(R.id.action_home);
                    rb_message.setChecked(true);
                } else if (name == CallsFragment.class) {
                    Calls();
//                    bnve.setSelectedItemId(R.id.action_business);
                    rb_call.setChecked(true);
                } else if (name == FriendsFragment.class) {
                    Friends();
//                    bnve.setSelectedItemId(R.id.action_setting);
                    rb_friends.setChecked(true);
                } else if (name == ShopFragment.class) {
                    Shop();
//                    bnve.setSelectedItemId(R.id.action_analytics);
                    rb_shop.setChecked(true);
                } else if (name == MoreFragment.class) {
//                    Options();
                    More();
                    rb_more.setChecked(true);
                }

//                String nameeeee = backEntry.getName();
//
//                if (nameeeee.equals("midlalpro.midlalpro.midlalpro.Fragment.BusinessEditFragment"))
//                {
//                    if (name == BusinessDetailsFragment.class)
//                    {
//                        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else if (name == ChangePasswordFragment.class || name == ProfileSettingsFragment.class) {
//                }
//                else if (name == BusinessEditFragment.class || name == BusinessSettingsFragment.class
//                        || name == BusinessListingDetailFragment.class || name == MapEditFragment.class ||
//                        name == ContactInfoFragment.class || name == WorkingHoursFragment.class)
//                {
//                    bnve.setSelectedItemId(R.id.action_business);
//                }
            }
        } else {
            finish();
        }
//        if (ElasticDrawer.STATE_CLOSED != drawerLayout.getDrawerState())
//            drawerLayout.closeMenu(true);
//        else if (isContextualMode()) {
//            disableContextualMode();
//        } else if (viewPager.getCurrentItem() != 0) {
//            viewPager.post(new Runnable() {
//                @Override
//                public void run() {
//                    viewPager.setCurrentItem(0);
//                }
//            });
//        } else {
//            super.onBackPressed();
//        }
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
                    userSelectDialogFragment = UserSelectDialogFragment.newInstance(this, myUsers);
                    FragmentManager manager = getSupportFragmentManager();
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
        fileToUpload = ImageCompressorUtil.compressImage(this, fileToUpload);
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
                Glide.with(MainActivity.this).load(fileToUpload).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(dialogUserImage);
                Glide.with(MainActivity.this).load(fileToUpload).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(usersImage);
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
    void userAdded(User value) {
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
    void groupAdded(Group group) {
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
    void groupUpdated(Group group) {
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
    public void OnUserClick(final User user, int position, View userImage) {
        if (ElasticDrawer.STATE_CLOSED != drawerLayout.getDrawerState()) {
            drawerLayout.closeMenu(true);
        }
        if (userImage == null) {
            userImage = usersImage;
        }


//        Chat(messageForwardList, user, false);

        Intent intent = ChatActivity.newIntent(this, messageForwardList, user, false);
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, userImage, "backImage");
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD, options.toBundle());
        } else {
            Bundle transitionBundle = ActivityTransitionLauncher.with(this).from(userImage).createBundle();
            intent.putExtras(transitionBundle);
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD);
            overridePendingTransition(0, 0);
        }

//        if (userSelectDialogFragment != null)
//            userSelectDialogFragment.dismiss();
    }

    @Override
    public void OnGroupClick(Group group, int position, View userImage) {
        Intent intent = ChatActivity.newIntent(this, messageForwardList, group, true);
//        if (userImage == null) {
//            userImage = usersImage;
//        }
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, userImage, "backImage");
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD, options.toBundle());

        } else {
            Bundle transitionBundle = ActivityTransitionLauncher.with(this).from(userImage).createBundle();
            intent.putExtras(transitionBundle);
            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD);
            overridePendingTransition(0, 0);
        }

//        if (userSelectDialogFragment != null)
//            userSelectDialogFragment.dismiss();
    }

    private void refreshUsers(int pos) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(USER_SELECT_TAG);
        if (frag != null) {
            userSelectDialogFragment.refreshUsers(pos);
        }
    }

    private void markOnline(boolean b) {
        //Mark online boolean as b in firebase
        usersRef.child(userMe.getId()).child("online").setValue(b);
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
                        GroupCreateDialogFragment.newInstance(this, userMe, myUsers).show(getSupportFragmentManager(), GROUP_CREATE_TAG);
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
                    OptionFragment();
//                new OptionsFragment().show(getSupportFragmentManager(), OPTIONS_MORE);
                break;
            case R.id.invite:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "YooHoo invitation");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.invitation_text), getPackageName()));
                    startActivity(Intent.createChooser(shareIntent, "Share using.."));
                } catch (Exception ignored) {

                }
                break;
            case R.id.action_delete:
                FragmentManager manager = getSupportFragmentManager();
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
        ImagePicker.create(this)
                .folderMode(true)
                .theme(R.style.AppTheme)
                .single()
                .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
    }

    private List<String> mediaPermissions() {
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : PERMISSIONS_STORAGE) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
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
    protected void onResume() {
        super.onResume();


    }


    @Override
    public ArrayList<Contact> getLocalContacts() {
        return contactsData;
    }

    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
        getSinchServiceInterface().startClient(userMe.getId());

    }


//    public void Chat(ArrayList<Message> forwardMessages, User user, Boolean IsGroup) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(Constant.FORWARD_MESSAGE, forwardMessages);
//        bundle.putParcelable(Constant.USER, user);
//        bundle.putBoolean(Constant.IS_GROUP, IsGroup);
//
//        ChatFragment chatFragment = ChatFragment.newInstance(forwardMessages, user, IsGroup);
//
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(chatFragment.getClass().getName());
//
//        if (fragment != null) {
//            getSupportFragmentManager().popBackStackImmediate(chatFragment.getClass().getName(), 0);
//            ((ChatFragment) fragment).Update(bundle);
//        } else
//            ConstantMethod.replaceFragment(getSupportFragmentManager(), chatFragment, R.id.frame_container);
//
//    }

//    @Override
//    public void OnMessageClick(Message message, int position) {
//        if (Helper.CHAT_CAB && message.isValid()) {
//            message.setSelected(!message.isSelected());//Toggle message selection
//            messageAdapter.notifyItemChanged(position);//Notify changes
//
//            if (message.isSelected())
//                countSelected++;
//            else
//                countSelected--;
//
//            selectedCount.setText(String.valueOf(countSelected));//Update count
//            if (countSelected == 0)
//                undoSelectionPrepared();//If count is zero then reset selection
//        }
//
//    }
//
//
//    private void undoSelectionPrepared() {
//        for (Message msg : dataList) {
//            msg.setSelected(false);
//        }
//        messageAdapter.notifyDataSetChanged();
//        toolbar.getMenu().clear();
//        selectedCount.setVisibility(View.GONE);
//        toolbarContainer.setVisibility(View.VISIBLE);
//        Helper.CHAT_CAB = false;
//    }
//
//    @Override
//    public void OnMessageLongClick(Message message, int position) {
//        if (!Helper.CHAT_CAB && message.isValid()) {//Prepare selection if not in selection mode
//            prepareToSelect();
//            message.setSelected(true);
//            messageAdapter.notifyItemChanged(position);
//            countSelected++;
//            selectedCount.setText(String.valueOf(countSelected));
//        }
//    }
//
//    private void prepareToSelect() {
//        toolbar.getMenu().clear();
//        toolbar.inflateMenu(R.menu.menu_chat_cab);
//        getSupportActionBar().setTitle("");
//        selectedCount.setText("1");
//        selectedCount.setVisibility(View.VISIBLE);
//        toolbarContainer.setVisibility(View.GONE);
//        Helper.CHAT_CAB = true;
//    }


    public void OptionFragment() {
        OptionsFragment optionsFragment = OptionsFragment.newInstance();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(optionsFragment.getClass().getName());
        if (fragment != null) {
            getSupportFragmentManager().popBackStackImmediate(optionsFragment.getClass().getName(), 0);
        } else
            ConstantMethod.replaceFragment(getSupportFragmentManager(), optionsFragment, R.id.frame_container);
    }

    public void Friends() {
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(Constant.USER_LIST, myUsers);
//        bundle.putParcelableArrayList(Constant.CONTACT_LIST, (ArrayList<? extends Parcelable>) contactsData);
//        bundle.putParcelableArrayList(Constant.LINKMAP_LIST, (ArrayList<? extends Parcelable>) linkhmap);
        FriendsFragment friendsFragment = FriendsFragment.newInstance();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(friendsFragment.getClass().getName());
        if (fragment != null) {
            getSupportFragmentManager().popBackStackImmediate(friendsFragment.getClass().getName(), 0);
//            ((FriendsFragment) fragment).Update(bundle);
        } else
            ConstantMethod.replaceFragment(getSupportFragmentManager(), friendsFragment, R.id.frame_container);
    }

    public void Home() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(homeFragment.getClass().getName());
        if (fragment != null) {
            getSupportFragmentManager().popBackStackImmediate(homeFragment.getClass().getName(), 0);
        } else
            ConstantMethod.replaceFragment(getSupportFragmentManager(), homeFragment, R.id.frame_container);
    }

    public void Options() {
        OptionsFragment optionsFragment = OptionsFragment.newInstance();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(optionsFragment.getClass().getName());
        if (fragment != null) {
            getSupportFragmentManager().popBackStackImmediate(optionsFragment.getClass().getName(), 0);
        } else
            ConstantMethod.replaceFragment(getSupportFragmentManager(), optionsFragment, R.id.frame_container);
    }

    public void Calls() {
        CallsFragment callsFragment = CallsFragment.newInstance(userMe);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(callsFragment.getClass().getName());
        if (fragment != null) {
            getSupportFragmentManager().popBackStackImmediate(callsFragment.getClass().getName(), 0);
        } else
            ConstantMethod.replaceFragment(getSupportFragmentManager(), callsFragment, R.id.frame_container);
    }

    public void Shop() {
        ShopFragment shopFragment = ShopFragment.newInstance("", "");
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(shopFragment.getClass().getName());
        if (fragment != null) {
            getSupportFragmentManager().popBackStackImmediate(shopFragment.getClass().getName(), 0);
        } else
            ConstantMethod.replaceFragment(getSupportFragmentManager(), shopFragment, R.id.frame_container);
    }

    public void More() {
        MoreFragment moreFragment = MoreFragment.newInstance("", "");
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(moreFragment.getClass().getName());
        if (fragment != null) {
            getSupportFragmentManager().popBackStackImmediate(moreFragment.getClass().getName(), 0);
        } else
            ConstantMethod.replaceFragment(getSupportFragmentManager(), moreFragment, R.id.frame_container);
    }


    @Override
    public User getUserMe() {
        return userMe;
    }


    @Override
    public void selectionDismissed() {

    }

    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void OnMessageClick(Message message, int position) {

    }

    @Override
    public void OnMessageLongClick(Message message, int position) {

    }

    @Override
    public boolean isRecordingPlaying(String fileName) {
        return false;
    }

    @Override
    public void playRecording(File file, String fileName, int position) {

    }


    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        rl_bottom_navigation.setVisibility(View.GONE);

    }

    @Override
    public void onSoftKeyboardClosed() {
        rl_bottom_navigation.setVisibility(View.VISIBLE);

    }
}
