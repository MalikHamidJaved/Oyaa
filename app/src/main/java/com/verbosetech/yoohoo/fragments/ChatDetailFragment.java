package com.verbosetech.yoohoo.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.CallScreenActivity;
import com.verbosetech.yoohoo.adapters.GroupNewParticipantsAdapter;
import com.verbosetech.yoohoo.adapters.MediaSummaryAdapter;
import com.verbosetech.yoohoo.interfaces.OnUserDetailFragmentInteraction;
import com.verbosetech.yoohoo.interfaces.UserGroupSelectionDismissListener;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.MyString;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.ConfirmationDialogFragment;
import com.verbosetech.yoohoo.utils.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChatDetailFragment extends BlankFragmentService implements GroupNewParticipantsAdapter.ParticipantClickListener {
    private static final int CALL_REQUEST_CODE = 911;
    private OnUserDetailFragmentInteraction mListener;

    private View mediaSummaryContainer, mediaSummaryViewAll, userDetailContainer, groupDetailContainer;
    private RecyclerView mediaSummary;
    private TextView userPhone, userStatus;
    private ImageView userPhoneClick;
    private ImageView userapptoappcall, userapptoappvideocall;
    private SwitchCompat muteNotificationToggle;

    private ArrayList<Message> attachments;

    private User user, userMe;
    private Group group;
    private Helper helper;

    Call call;

    private GroupNewParticipantsAdapter selectedParticipantsAdapter;
    private TextView participantsCount, participantsAdd;
    private ProgressBar participantsProgress;
    private ArrayList<User> groupUsers, groupNewUsers;
    private String CONFIRM_TAG = "confirm";

    private DatabaseReference groupRef, userRef;
    private ArrayList<User> myUsers;

    public ChatDetailFragment() {
        // Required empty public constructor
    }

    public static ChatDetailFragment newInstance(User user) {
        ChatDetailFragment fragment = new ChatDetailFragment();
        fragment.user = user;
        return fragment;
    }

    public static ChatDetailFragment newInstance(Group group) {
        ChatDetailFragment fragment = new ChatDetailFragment();
        fragment.group = group;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(getContext());
        userMe = helper.getLoggedInUser();

        if (group != null) {
            myUsers = new ArrayList<User>();
            HashMap<String, User> userHashMap = helper.getCacheMyUsers();
            if (userHashMap != null)
                myUsers.addAll(userHashMap.values());
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        groupRef = firebaseDatabase.getReference(Helper.REF_GROUP);
        userRef = firebaseDatabase.getReference(Helper.REF_USER);
    }


    @Override
    protected void onServiceConnected() {
        Log.d("uname is :", getSinchServiceInterface().getUserName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        userDetailContainer = view.findViewById(R.id.userDetailContainer);
        groupDetailContainer = view.findViewById(R.id.groupDetailContainer);

        mediaSummaryContainer = view.findViewById(R.id.mediaSummaryContainer);
        mediaSummaryViewAll = view.findViewById(R.id.mediaSummaryAll);
        mediaSummary = (RecyclerView) view.findViewById(R.id.mediaSummary);

        if (user != null) {
            userDetailContainer.setVisibility(View.VISIBLE);
            groupDetailContainer.setVisibility(View.GONE);

            userPhone = view.findViewById(R.id.userPhone);
            userPhoneClick = view.findViewById(R.id.userPhoneClick);

            userapptoappcall = view.findViewById(R.id.userapptoappcall);
            userapptoappvideocall = view.findViewById(R.id.userapptoappvideocall);

            userStatus = view.findViewById(R.id.userStatus);
            muteNotificationToggle = view.findViewById(R.id.muteNotificationSwitch);
        } else {
            userDetailContainer.setVisibility(View.GONE);
            groupDetailContainer.setVisibility(View.VISIBLE);

            participantsCount = view.findViewById(R.id.participantsCount);
            participantsAdd = view.findViewById(R.id.participantsAdd);
            participantsProgress = view.findViewById(R.id.participantsProgress);
            RecyclerView participantsRecycler = view.findViewById(R.id.participants);
            participantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            groupUsers = new ArrayList<>();
            selectedParticipantsAdapter = new GroupNewParticipantsAdapter(this, groupUsers, userMe);
            participantsRecycler.setAdapter(selectedParticipantsAdapter);
            participantsAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<User> myUsersLeftToAdd = new ArrayList<>();
                    myUsersLeftToAdd.addAll(myUsers);
                    myUsersLeftToAdd.removeAll(groupUsers);
                    if (myUsersLeftToAdd.isEmpty()) {
                        Toast.makeText(getContext(), "No new members to add", Toast.LENGTH_SHORT).show();
                    } else {
                        groupNewUsers = new ArrayList<>();
                        GroupMembersSelectDialogFragment.newInstance(new UserGroupSelectionDismissListener() {
                            @Override
                            public void onUserGroupSelectDialogDismiss() {
                                if (!groupNewUsers.isEmpty()) {
                                    showAddMemberConfirmationDialog();
                                }
                            }

                            @Override
                            public void selectionDismissed() {

                            }
                        }, groupNewUsers, myUsersLeftToAdd).show(getChildFragmentManager(), "selectgroupmembers");
                    }
                }
            });
        }

        return view;
    }


    private void showAddMemberConfirmationDialog() {
        ConfirmationDialogFragment confirmationDialogFragment = ConfirmationDialogFragment.newInstance("Add member" + (groupNewUsers.size() == 1 ? "" : "s"),
                String.format("Are you sure you want to add %s in this group?",
                        (groupNewUsers.size() == 1 ? groupNewUsers.get(0).getNameToDisplay() : groupNewUsers.size() + " members")),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (User userToAdd : groupNewUsers)
                            group.getUserIds().add(new MyString(userToAdd.getId()));
                        groupRef.child(group.getId()).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Member" + (groupNewUsers.size() == 1 ? " added" : "s added"), Toast.LENGTH_SHORT).show();
                                groupNewUsers.clear();
                            }
                        });
                        groupUsers.addAll(groupNewUsers);
                        selectedParticipantsAdapter.notifyDataSetChanged();
                        participantsCount.setText(String.format("Participants (%d)", selectedParticipantsAdapter.getItemCount()));
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        confirmationDialogFragment.show(getChildFragmentManager(), CONFIRM_TAG);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mListener != null)
            mListener.getAttachments();
        if (user != null) {
            setData();
            muteNotificationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    helper.setUserMute(user.getId(), b);
                }
            });
            mediaSummaryViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.switchToMediaFragment();
                }
            });
            userPhoneClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callPhone(true, user.getId());
                }
            });

            userapptoappcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (user.isOnline()) {
//                    LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    Location lastLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    Double longitude = lastLoc.getLongitude();
//                    Double latitude = lastLoc.getLatitude();
//
//                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                    List<Address> addresses = null;
//                    try {
//                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    Map<String, String> headers = new HashMap<String, String>();
//                    headers.put("location", addresses.get(0).getAddressLine(0));
//
//                    String userName = user.getId();
//                    if (userName.isEmpty()) {
//                        Toast.makeText(getContext(), "Please enter a user to call", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
                        Call call = getSinchServiceInterface().callUser(user.getId(), null);
                        String callId = call.getCallId();

                        Intent callScreen = new Intent(getContext(), CallScreenActivity.class);
                        callScreen.putExtra(SinchService.CALL_ID, callId);
                        callScreen.putExtra("video", false);
                        startActivity(callScreen);
//                    } else {
//                        Toast.makeText(getContext(), "User is Offline You not able to call.", Toast.LENGTH_SHORT).show();
//                    }


                }
            });

            userapptoappvideocall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (user.isOnline()) {
                        Call call = getSinchServiceInterface().callUserVideo(user.getId());
                        String callId = call.getCallId();

                        Intent callScreen = new Intent(getContext(), CallScreenActivity.class);
                        callScreen.putExtra(SinchService.CALL_ID, callId);
                        callScreen.putExtra("video", true);
                        startActivity(callScreen);
//                    } else {
//                        Toast.makeText(getContext(), "User is Offline You not able to Video call.", Toast.LENGTH_SHORT).show();
//                    }
                }
            });
        } else {
            setParticipants();
        }
    }


    private void setParticipants() {
        groupUsers.clear();
        participantsProgress.setVisibility(View.VISIBLE);
        for (MyString memberId : group.getUserIds()) {
            if (helper.getCacheMyUsers() != null && helper.getCacheMyUsers().containsKey(memberId.getString()))
                groupUsers.add(helper.getCacheMyUsers().get(memberId.getString()));
        }
        if (group.getUserIds().size() == groupUsers.size()) {
            participantsProgress.setVisibility(View.GONE);
            selectedParticipantsAdapter.notifyDataSetChanged();
            participantsCount.setText(String.format("Participants (%d)", selectedParticipantsAdapter.getItemCount()));
        } else {
            loadMembers();
        }
    }

    public void notifyGroupUpdated(Group valueGroup) {
        if (group != null && group.getId().equals(valueGroup.getId())) {
            boolean isMember = valueGroup.getUserIds().contains(new MyString(userMe.getId()));
            if (!isMember) {
                participantsAdd.setOnClickListener(null);
                participantsAdd.setVisibility(View.GONE);
            }
            if (group.getUserIds().size() != valueGroup.getUserIds().size()) {
                group = valueGroup;
                setParticipants();
            }
        }
    }

    private void loadMembers() {
        for (MyString memberId : group.getUserIds()) {
            if (!groupUsers.contains(new User(memberId.getString(), "", "", ""))) {
                userRef.child(memberId.getString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            User user = dataSnapshot.getValue(User.class);
                            if (User.validate(user)) {
                                if (group.getUserIds().size() == groupUsers.size()) {
                                    sortGroupUsers();
                                    participantsProgress.setVisibility(View.GONE);
                                    selectedParticipantsAdapter.notifyDataSetChanged();
                                    participantsCount.setText(String.format("Participants (%d)", selectedParticipantsAdapter.getItemCount()));
                                } else {
                                    groupUsers.add(user);
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("USER", "invalid user");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    private void sortGroupUsers() {
        ArrayList<User> sorted = new ArrayList<>();
        for (MyString userId : group.getUserIds()) {
            int index = groupUsers.indexOf(new User(userId.getString(), "", "", ""));
            sorted.add(groupUsers.get(index));
            groupUsers.remove(index);
        }
        groupUsers.clear();
        groupUsers.addAll(sorted);
    }

    private void callPhone(boolean dial, String phoneNumber) {
        if (dial) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(getContext(), "To dial number automatically for you we need this permission", Toast.LENGTH_LONG).show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
                }
            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
            }
        } else {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    callPhone(true, user.getId());
                else
                    callPhone(false, user.getId());
                break;
        }
    }

    private void setData() {
        userStatus.setText(user.getStatus());
        userPhone.setText(user.getId());
        muteNotificationToggle.setChecked(helper.isUserMute(user.getId()));
    }

    public void setupMediaSummary(ArrayList<Message> attachments) {
        if (attachments.size() > 0) {
            this.attachments = attachments;
            mediaSummaryContainer.setVisibility(View.VISIBLE);
            mediaSummary.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mediaSummary.setAdapter(new MediaSummaryAdapter(getContext(), attachments, false, userMe.getId()));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserDetailFragmentInteraction) {
            mListener = (OnUserDetailFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserDetailFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onParticipantClick(final int pos, final User participant) {
        ConfirmationDialogFragment confirmationDialogFragment = ConfirmationDialogFragment.newInstance("Remove user",
                String.format("Are you sure you want to remove %s from this group?", participant.getNameToDisplay()),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeParticipant(participant.getId());
                        groupUsers.remove(pos);
                        selectedParticipantsAdapter.notifyItemRemoved(pos);
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        confirmationDialogFragment.show(getChildFragmentManager(), CONFIRM_TAG);
    }

    private void removeParticipant(String id) {
        ArrayList<MyString> userIds = new ArrayList<>();
        for (MyString userId : group.getUserIds())
            if (!userId.getString().equals(id))
                userIds.add(userId);
        group.setUserIds(userIds);

        groupRef.child(group.getId()).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Member removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
