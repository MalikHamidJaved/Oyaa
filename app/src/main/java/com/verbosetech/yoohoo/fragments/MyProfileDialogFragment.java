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
import android.widget.Button;
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
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.ImageCompressorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileDialogFragment extends BaseFullDialogFragment {

    Button btn_status_edit, btn_et_phone;
    EditText et_status;
    User userMe;
    Helper helper;
    private DatabaseReference usersRef;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE_PICKER = 4321;
    private static final int REQUEST_CODE_MEDIA_PERMISSION = 999;
    CircleImageView civ_profile;
    Button btn_status_save;
    EditText et_phone;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_profile, container);
        civ_profile = view.findViewById(R.id.civ_profile);
//        btn_et_phone = view.findViewById(R.id.btn_et_phone);
        btn_status_edit = view.findViewById(R.id.btn_status_edit);
        et_status = view.findViewById(R.id.et_status);
        btn_status_save = view.findViewById(R.id.btn_status_save);
        et_phone = view.findViewById(R.id.et_phone);
        et_phone.setText(userMe.getId());
        if (!userMe.getStatus().equals("")) {
            et_status.setText(userMe.getStatus());
        } else {
            et_status.setText("Yoohoo Status!");
        }
        Glide.with(this).load(userMe.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(civ_profile);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.ib_profile_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPermissions().isEmpty()) {
                    pickProfileImage();
                } else {
                    requestPermissions(mediaPermissions().toArray(new String[mediaPermissions().size()]), REQUEST_CODE_MEDIA_PERMISSION);
                }


            }
        });

        btn_status_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_status.setEnabled(true);
                et_status.setFocusable(true);
                btn_status_edit.setVisibility(View.GONE);
                btn_status_save.setVisibility(View.VISIBLE);
            }
        });

        btn_status_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.closeKeyboard(getContext(), view);
                updateUserNameAndStatus(et_status.getText().toString().trim());
            }
        });
        return view;
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

    private void toast(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && data != null) {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            Glide.with(this).load(images.get(0).getPath()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(civ_profile);

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

    private void updateUserNameAndStatus(String updatedStatus) {
        if (TextUtils.isEmpty(updatedStatus)) {
            Toast.makeText(getContext(), "Status cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!userMe.getStatus().equals(updatedStatus)) {
            et_status.setEnabled(false);
            btn_status_edit.setVisibility(View.VISIBLE);
            btn_status_save.setVisibility(View.GONE);
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

}
