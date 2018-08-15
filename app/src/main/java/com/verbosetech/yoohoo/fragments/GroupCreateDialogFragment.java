package com.verbosetech.yoohoo.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.MainActivity;
import com.verbosetech.yoohoo.adapters.GroupNewParticipantsAdapter;
import com.verbosetech.yoohoo.interfaces.OnUserGroupItemClick;
import com.verbosetech.yoohoo.interfaces.UserGroupSelectionDismissListener;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.MyString;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.ImageCompressorUtil;
import com.verbosetech.yoohoo.views.MyRecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmList;

/**
 * Created by a_man on 31-12-2017.
 */

public class GroupCreateDialogFragment extends BaseFullDialogFragment implements UserGroupSelectionDismissListener {
    private ArrayList<User> myUsers, selectedUsers;
    private static final int REQUEST_CODE_PICKER = 4321;
    private static final int REQUEST_CODE_MEDIA_PERMISSION = 999;

    private CircleImageView groupImage;
    private EditText groupName, groupStatus;
    private TextView participantsCount;
    private ProgressBar groupImageProgress;
    private GroupNewParticipantsAdapter selectedParticipantsAdapter;
    private User userMe;

    private String groupImagePath = "";
    private String groupId;
    private OnUserGroupItemClick onUserGroupItemClick;
    private View done;

    public GroupCreateDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_new, container);
        groupImage = view.findViewById(R.id.groupImage);
        groupName = view.findViewById(R.id.groupName);
        groupStatus = view.findViewById(R.id.groupStatus);
        participantsCount = view.findViewById(R.id.participantsCount);
        groupImageProgress = view.findViewById(R.id.groupImageProgress);
        groupImageProgress.setVisibility(View.GONE);

        RecyclerView participantsRecycler = view.findViewById(R.id.participants);
        participantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        selectedParticipantsAdapter = new GroupNewParticipantsAdapter(this, selectedUsers, false);
        participantsRecycler.setAdapter(selectedParticipantsAdapter);

        view.findViewById(R.id.groupImageContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> permissions = Helper.mediaPermissions(getContext());
                if (permissions.isEmpty()) {
                    ImagePicker.create(GroupCreateDialogFragment.this)
                            .folderMode(true)
                            .theme(R.style.AppTheme)
                            .single()
                            .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
                } else {
                    requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_MEDIA_PERMISSION);
                }
            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        done = view.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });
        view.findViewById(R.id.participantsAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myUsers.isEmpty())
                    Toast.makeText(getContext(), R.string.empty_contact_list_for_group, Toast.LENGTH_SHORT).show();
                else
                    GroupMembersSelectDialogFragment.newInstance(GroupCreateDialogFragment.this, selectedUsers, myUsers).show(getChildFragmentManager(), "selectgroupmembers");
            }
        });
        setCancelable(false);
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_MEDIA_PERMISSION) {
            if (Helper.mediaPermissions(getContext()).isEmpty()) {
                ImagePicker.create(GroupCreateDialogFragment.this)
                        .folderMode(true)
                        .theme(R.style.AppTheme)
                        .single()
                        .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && data != null) {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            groupImagePath = images.get(0).getPath();
            Glide.with(this).load(groupImagePath).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(groupImage);
        }
    }

    private void done() {
        if (selectedParticipantsAdapter.getItemCount() == 0) {
            Toast.makeText(getContext(), "No participants selected", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(groupName.getText().toString().trim())) {
            Toast.makeText(getContext(), "User name can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(groupStatus.getText().toString().trim())) {
            Toast.makeText(getContext(), "Give this group a short description", Toast.LENGTH_SHORT).show();
            return;
        }

        done.setClickable(false);
        done.setFocusable(false);

        groupId = Helper.GROUP_PREFIX + "_" + userMe.getId() + "_" + System.currentTimeMillis();

        if (TextUtils.isEmpty(groupImagePath)) {
            createGroup("");
        } else {
            File fileToUpload = new File(groupImagePath);
            fileToUpload = ImageCompressorUtil.compressImage(getContext(), fileToUpload);
            userImageUploadTask(fileToUpload, AttachmentTypes.IMAGE, null);
        }

    }

    private void userImageUploadTask(final File fileToUpload, @AttachmentTypes.AttachmentType final int attachmentType, final Attachment attachment) {
        groupImageProgress.setVisibility(View.VISIBLE);

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE)
                .child(getString(R.string.app_name))
                .child("ProfileImage")
                .child(groupId);
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(fileToUpload));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                groupImageProgress.setVisibility(View.GONE);
                StorageMetadata storageMetadata = taskSnapshot.getMetadata();
                createGroup(storageMetadata.getDownloadUrl().toString());
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Unable to upload image.", Toast.LENGTH_SHORT).show();
                groupImageProgress.setVisibility(View.GONE);
                createGroup("");
            }
        });
    }

    private void createGroup(String groupImageUrl) {
        final Group group = new Group();
        group.setId(groupId);
        group.setName(groupName.getText().toString());
        group.setStatus(groupStatus.getText().toString());
        group.setImage(groupImageUrl);
        ArrayList<MyString> userIds = new ArrayList<>();
        userIds.add(new MyString(userMe.getId()));
        for (User user : selectedUsers) {
            userIds.add(new MyString(user.getId()));
        }
        group.setUserIds(userIds);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference groupRef = firebaseDatabase.getReference(Helper.REF_GROUP).child(groupId);
        groupRef.setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Group created", Toast.LENGTH_SHORT).show();
                onUserGroupItemClick.OnGroupClick(group, -1, null);
                dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Unable to process request at this time", Toast.LENGTH_SHORT).show();
                done.setClickable(true);
                done.setFocusable(true);
            }
        });
    }

    public static GroupCreateDialogFragment newInstance(OnUserGroupItemClick onUserGroupItemClick, User user, ArrayList<User> myUsers) {
        GroupCreateDialogFragment dialogFragment = new GroupCreateDialogFragment();
        dialogFragment.userMe = user;
        dialogFragment.myUsers = myUsers;
        dialogFragment.onUserGroupItemClick = onUserGroupItemClick;
        dialogFragment.selectedUsers = new ArrayList<>();
        return dialogFragment;
    }

    @Override
    public void onUserGroupSelectDialogDismiss() {
        //do nothing
    }

    @Override
    public void selectionDismissed() {
        if (selectedParticipantsAdapter != null) {
            selectedParticipantsAdapter.notifyDataSetChanged();
            participantsCount.setText(String.format("Participants (%d)", selectedParticipantsAdapter.getItemCount()));
        }
    }
}
