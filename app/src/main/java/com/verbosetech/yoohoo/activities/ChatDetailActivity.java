package com.verbosetech.yoohoo.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.fragments.ChatDetailFragment;
import com.verbosetech.yoohoo.fragments.UserMediaFragment;
import com.verbosetech.yoohoo.interfaces.OnUserDetailFragmentInteraction;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.MyString;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.GeneralUtils;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.ImageCompressorUtil;
import com.verbosetech.yoohoo.viewHolders.RequestPermissionHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChatDetailActivity extends BaseActivity implements OnUserDetailFragmentInteraction, SinchService.StartFailedListener {
    private Handler handler;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private CoordinatorLayout coordinatorLayout;
    private View userDetailsSummaryContainer, pickImage;
    private ImageView userImage;
    private EditText userStatus, userName;
    private ArrayList<Message> mediaMessages;

    private static final String TAG_DETAIL = "TAG_DETAIL", TAG_MEDIA = "TAG_MEDIA";
    private static String EXTRA_DATA_USER = "extradatauser";
    private static String EXTRA_DATA_GROUP = "extradatagroup";
    private static final int REQUEST_CODE_PICKER = 4321;
    private static final int REQUEST_CODE_MEDIA_PERMISSION = 999;
    private ChatDetailFragment fragmentUserDetail;
    private View done;

    private RequestPermissionHandler mRequestPermissionHandler;

    Call call;

    @Override
    void userAdded(User valueUser) {
        //doNothing
    }

    @Override
    void groupAdded(Group valueGroup) {
        //doNothing
    }

    @Override
    public void userUpdated(User valueUser) {
        if (user != null && user.getId().equals(valueUser.getId())) {
            valueUser.setNameInPhone(user.getNameInPhone());
            user = valueUser;
            setUserData();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_DATA_USER, user);
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    @Override
    void groupUpdated(Group valueGroup) {
        if (group != null && group.getId().equals(valueGroup.getId())) {
            group = valueGroup;
            if (fragmentUserDetail != null) {
                fragmentUserDetail.notifyGroupUpdated(group);
            }
            if (!group.getUserIds().contains(new MyString(userMe.getId()))) {
                userStatus.setText("You have been removed from this group..!!");
                userName.setEnabled(false);
                userStatus.setEnabled(false);
                done.setVisibility(View.GONE);
            } else {
                setUserData();
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_DATA_GROUP, group);
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

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


        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        userDetailsSummaryContainer = findViewById(R.id.userDetailsSummaryContainer);
        pickImage = findViewById(R.id.pickImage);
        setSupportActionBar(((Toolbar) findViewById(R.id.toolbar)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);
        userImage = findViewById(R.id.expandedImage);
        userName = findViewById(R.id.user_name);
        userStatus = findViewById(R.id.emotion);
        done = findViewById(R.id.done);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_DATA_USER)) {
            user = intent.getParcelableExtra(EXTRA_DATA_USER);
        } else if (intent.hasExtra(EXTRA_DATA_GROUP)) {
            group = intent.getParcelableExtra(EXTRA_DATA_GROUP);
        } else {
            finish();
        }
        handler = new Handler();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.closeKeyboard(ChatDetailActivity.this, view);
                updateGroupNameAndStatus(userName.getText().toString().trim(), userStatus.getText().toString().trim());
            }
        });
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> permissions = Helper.mediaPermissions(ChatDetailActivity.this);
                if (permissions.isEmpty()) {
                    ImagePicker.create(ChatDetailActivity.this)
                            .folderMode(true)
                            .showCamera(true)
                            .theme(R.style.AppTheme)
                            .single()
                            .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
                } else {
                    ActivityCompat.requestPermissions(ChatDetailActivity.this, permissions.toArray(new String[permissions.size()]), REQUEST_CODE_MEDIA_PERMISSION);
                }
            }
        });

        setupViews();
        getMediaInfo();
        loadFragment(TAG_DETAIL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        switch (requestCode) {
            case REQUEST_CODE_MEDIA_PERMISSION:
                if (Helper.mediaPermissions(this).isEmpty()) {
                    ImagePicker.create(this)
                            .folderMode(true)
                            .showCamera(true)
                            .theme(R.style.AppTheme)
                            .single()
                            .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    String groupImagePath = images.get(0).getPath();
                    Glide.with(this).load(groupImagePath).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder).optionalCenterCrop()).into(userImage);

                    File fileToUpload = new File(groupImagePath);
                    fileToUpload = ImageCompressorUtil.compressImage(this, fileToUpload);
                    userImageUploadTask(fileToUpload, AttachmentTypes.IMAGE, null);
                }
                break;
        }
    }

    private void userImageUploadTask(final File fileToUpload, @AttachmentTypes.AttachmentType final int attachmentType, final Attachment attachment) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE)
                .child(getString(R.string.app_name))
                .child("ProfileImage")
                .child(group.getId());
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(fileToUpload));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageMetadata storageMetadata = taskSnapshot.getMetadata();
                group.setImage(storageMetadata.getDownloadUrl().toString());
                groupRef.child(group.getId()).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChatDetailActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatDetailActivity.this, "Unable to upload image.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGroupNameAndStatus(String updatedName, String updatedStatus) {
        if (TextUtils.isEmpty(updatedName)) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(updatedStatus)) {
            Toast.makeText(this, "Status cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!group.getName().equals(updatedName) || !group.getStatus().equals(updatedStatus)) {
            group.setName(updatedName);
            group.setStatus(updatedStatus);
            groupRef.child(group.getId()).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ChatDetailActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getMediaInfo() {
        String myId = userMe.getId();
        Chat query = Helper.getChat(rChatDb, myId, user != null ? user.getId() : group.getId()).notEqualTo("messages.attachmentType", 6).findFirst();

        mediaMessages = new ArrayList<>();
        if (query != null) {
            for (Message m : query.getMessages()) {
                if (m.getAttachmentType() == AttachmentTypes.AUDIO
                        ||
                        m.getAttachmentType() == AttachmentTypes.IMAGE
                        ||
                        m.getAttachmentType() == AttachmentTypes.VIDEO
                        ||
                        m.getAttachmentType() == AttachmentTypes.DOCUMENT) {
                    if (m.getAttachmentType() != AttachmentTypes.IMAGE && !new File(Environment.getExternalStorageDirectory() + "/"
                            +
                            getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(m.getAttachmentType()) + (myId.equals(m.getSenderId()) ? "/.sent/" : "")
                            , m.getAttachment().getName()).exists()) {
                        continue;
                    }
                    mediaMessages.add(m);
                }
            }
        }
    }

    private void setupViews() {
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                setAppBarOffset(GeneralUtils.getDisplayMetrics().widthPixels / 2);
            }
        });

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    userDetailsSummaryContainer.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle(user != null ? user.getNameToDisplay() : group.getName());
                    isShow = true;
                } else if (isShow) {
                    userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        userName.setText(user != null ? user.getNameToDisplay() : group.getName());
        setUserData();
    }

    private void setUserData() {
        if (user != null) {
            userName.setCompoundDrawablesWithIntrinsicBounds(user.isOnline() ? R.drawable.ring_green : 0, 0, 0, 0);
        }
        userStatus.setText(user != null ? user.getStatus() : group.getStatus());
        Glide.with(this).load(user != null ? user.getImage() : group.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);

        if (group != null) {
            userName.setEnabled(true);
            userStatus.setEnabled(true);
            done.setVisibility(View.VISIBLE);
            pickImage.setVisibility(View.VISIBLE);
        } else {
            userName.setEnabled(false);
            userStatus.setEnabled(false);
            done.setVisibility(View.GONE);
            pickImage.setVisibility(View.GONE);
        }
    }


    private void loadFragment(final String fragmentTag) {
//        if (getSupportFragmentManager().findFragmentByTag(fragmentTag) != null)
//            return;
//
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location lastLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        Double longitude = lastLoc.getLongitude();
//        Double latitude = lastLoc.getLatitude();
//
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses = null;
//        try {
//            addresses = geocoder.getFromLocation(latitude, longitude, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        final Map<String, String> headers = new HashMap<String, String>();
//        headers.put("location", addresses.get(0).getAddressLine(0));

//        final String userName = user.getId();
//        if (userName.isEmpty()) {
//            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
//            return;
//        }


        handler.post(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = null;
                switch (fragmentTag) {
                    case TAG_DETAIL:
                        if (user != null)
//                            fragmentUserDetail = ChatDetailFragment.newInstance(user, getSinchServiceInterface().callUser(user.getId(), headers));
                            fragmentUserDetail = ChatDetailFragment.newInstance(user);
                        else
                            fragmentUserDetail = ChatDetailFragment.newInstance(group);
                        fragment = fragmentUserDetail;
                        break;
                    case TAG_MEDIA:
                        fragment = new UserMediaFragment();
                        break;
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frameLayout, fragment, fragmentTag);
                if (fragmentTag.equals(TAG_MEDIA)) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().findFragmentByTag(TAG_DETAIL) == null)
//            loadFragment(TAG_DETAIL);
//        else
//            super.onBackPressed();
//    }

    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0});
    }

    @Override
    public void getAttachments() {
        ChatDetailFragment fragment = ((ChatDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG_DETAIL));
        if (fragment != null) {
            fragment.setupMediaSummary(mediaMessages);
        }
    }

    @Override
    public ArrayList<Message> getAttachments(int tabPos) {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MEDIA) != null) {
            ArrayList<Message> toReturn = new ArrayList<>();
            switch (tabPos) {
                case 0:
                    for (Message msg : mediaMessages)
                        if (msg.getAttachmentType() == AttachmentTypes.IMAGE || msg.getAttachmentType() == AttachmentTypes.VIDEO)
                            toReturn.add(msg);
                    break;
                case 1:
                    for (Message msg : mediaMessages)
                        if (msg.getAttachmentType() == AttachmentTypes.AUDIO)
                            toReturn.add(msg);
                    break;
                case 2:
                    for (Message msg : mediaMessages)
                        if (msg.getAttachmentType() == AttachmentTypes.DOCUMENT)
                            toReturn.add(msg);
                    break;
            }
            return toReturn;
        } else
            return null;
    }

    @Override
    public void switchToMediaFragment() {
        appBarLayout.setExpanded(false, true);
        loadFragment(TAG_MEDIA);
    }

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, ChatDetailActivity.class);
        intent.putExtra(EXTRA_DATA_USER, user);
        return intent;
    }

    public static Intent newIntent(Context context, Group group) {
        Intent intent = new Intent(context, ChatDetailActivity.class);
        intent.putExtra(EXTRA_DATA_GROUP, group);
        return intent;
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
