package com.verbosetech.yoohoo.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.ConfirmationDialogFragment;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.ImageCompressorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by a_man on 01-01-2018.
 */

public class OptionsFragment extends BaseFullDialogFragment {
    private View rootView;
    private User userMe;

    private static String CONFIRM_TAG = "confirmtag";
    private static String PRIVACY_TAG = "privacytag";

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE_MEDIA_PERMISSION = 999;
    private static final int REQUEST_CODE_PICKER = 4321;
    private CircleImageView userImage;
    private Helper helper;
    private DatabaseReference usersRef;

    public static OptionsFragment newInstance() {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(getContext());
        userMe = helper.getLoggedInUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference(Helper.REF_USER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_options, container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userImage = view.findViewById(R.id.userImage);
        final EditText userName = view.findViewById(R.id.userName);
        final EditText userStatus = view.findViewById(R.id.userStatus);
        userName.setText(userMe.getNameToDisplay());
        userStatus.setText(userMe.getStatus());
        Glide.with(this).load(userMe.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);
        view.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.closeKeyboard(getContext(), view);
                updateUserNameAndStatus(userName.getText().toString().trim(), userStatus.getText().toString().trim());
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPermissions().isEmpty()) {
                    pickProfileImage();
                } else {
                    requestPermissions(mediaPermissions().toArray(new String[mediaPermissions().size()]), REQUEST_CODE_MEDIA_PERMISSION);
                }
            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.closeKeyboard(getContext(), view);
                dismiss();
            }
        });
        view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.openShareIntent(getContext(), null, "Download YooHoo now");
            }
        });
        view.findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.openPlayStore(getContext());
            }
        });
        view.findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.openSupportMail(getContext());
            }
        });
        view.findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PrivacyPolicyDialogFragment().show(getChildFragmentManager(), PRIVACY_TAG);
            }
        });
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmationDialogFragment confirmationDialogFragment = ConfirmationDialogFragment.newInstance("Logout",
                        "Are you sure you want to logout?",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                helper.logout();
                                getActivity().finish();
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                confirmationDialogFragment.show(getChildFragmentManager(), CONFIRM_TAG);
            }
        });
    }

    private void updateUserNameAndStatus(String updatedName, String updatedStatus) {
        if (TextUtils.isEmpty(updatedName)) {
            Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(updatedStatus)) {
            Toast.makeText(getContext(), "Status cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!userMe.getName().equals(updatedName) || !userMe.getStatus().equals(updatedStatus)) {
            userMe.setName(updatedName);
            userMe.setStatus(updatedStatus);
            usersRef.child(userMe.getId()).setValue(userMe).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if (helper != null)
                        helper.setLoggedInUser(userMe);
                    toast("Updated!");
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_MEDIA_PERMISSION) {
            if (mediaPermissions().isEmpty()) {
                pickProfileImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && data != null) {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            Glide.with(this).load(images.get(0).getPath()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);

            File fileToUpload = new File(images.get(0).getPath());
            fileToUpload = ImageCompressorUtil.compressImage(getContext(), fileToUpload);
            userImageUploadTask(fileToUpload, AttachmentTypes.IMAGE, null);
        }
    }

    private void userImageUploadTask(final File fileToUpload, @AttachmentTypes.AttachmentType final int attachmentType, final Attachment attachment) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE).child(getString(R.string.app_name)).child("ProfileImage").child(userMe.getId());
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(fileToUpload));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageMetadata storageMetadata = taskSnapshot.getMetadata();
                userMe.setImage(storageMetadata.getDownloadUrl().toString());
                helper.setLoggedInUser(userMe);
                usersRef.child(userMe.getId()).setValue(userMe).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (helper != null)
                            helper.setLoggedInUser(userMe);
                        toast("Profile image updated");
                    }
                });
            }
        });
    }

    private void toast(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
            if (ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
    }
}
