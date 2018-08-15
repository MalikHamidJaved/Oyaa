package com.verbosetech.yoohoo.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.camera.CameraModule;
import com.esafirm.imagepicker.features.camera.ImmediateCameraModule;
import com.esafirm.imagepicker.features.camera.OnImageReadyListener;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kevalpatel.ringtonepicker.RingtonePickerDialog;
import com.kevalpatel.ringtonepicker.RingtonePickerListener;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.sinch.android.rtc.calling.Call;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.adapters.MessageAdapter;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.DownloadFileEvent;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.MyString;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.RestartServiceReceiver;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.ConfirmationDialogFragment;
import com.verbosetech.yoohoo.utils.DownloadUtil;
import com.verbosetech.yoohoo.utils.FileUtils;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.ImageCompressorUtil;
import com.verbosetech.yoohoo.utils.KeyboardUtil;
import com.verbosetech.yoohoo.viewHolders.BaseMessageViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentRecordingViewHolder;
import com.verbosetech.yoohoo.viewHolders.RequestPermissionHandler;
import com.verbosetech.yoohoo.views.DialogCallSelection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import de.hdodenhof.circleimageview.CircleImageView;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;

public class SecretChatActivityActivity extends BaseActivity implements OnMessageItemClick, MessageAttachmentRecordingViewHolder.RecordingViewInteractor,
        DialogCallSelection.CallbackDialog, View.OnClickListener
//        , SinchService.StartFailedListener
{
    @Override
    void userAdded(User valueUser) {

    }

    @Override
    void groupAdded(Group valueGroup) {

    }

    @Override
    public void userUpdated(User valueUser) {

    }

    @Override
    void groupUpdated(Group valueGroup) {

    }

    @Override
    public void onClick(View view) {

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
    public void SaveButton(boolean Issave) {

    }
//
//    private static final int REQUEST_CODE_CONTACT = 1;
//    private static final int REQUEST_PLACE_PICKER = 2;
//    private static final int REQUEST_CODE_PLAY_SERVICES = 3;
//    private static final int REQUEST_CAMERA = 4;
//    private static final int REQUEST_CODE_PICKER = 1234;
//    private static final int REQUEST_CODE_UPDATE_USER = 753;
//    private static final int REQUEST_CODE_UPDATE_GROUP = 357;
//    private static final int REQUEST_PERMISSION_RECORD = 159;
//    private static String EXTRA_DATA_USER = "extradatauser";
//    private static String EXTRA_DATA_GROUP = "extradatagroup";
//    private static String EXTRA_DATA_LIST = "extradatalist";
//    private static String DELETE_TAG = "deletetag";
//    private MessageAdapter messageAdapter;
//    private ArrayList<Message> dataList = new ArrayList<>();
//    private RealmResults<Chat> queryResult;
//    private String chatChild, userOrGroupId;
//    private ExitActivityTransition exitTransition;
//    private int countSelected = 0;
//
//    private Handler recordWaitHandler, recordTimerHandler;
//    private Runnable recordRunnable, recordTimerRunnable;
//    private MediaRecorder mRecorder = null;
//    private String recordFilePath;
//    private float displayWidth;
//    private String[] recordPermissions = {Manifest.permission.VIBRATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//
//    private ArrayList<Integer> adapterPositions = new ArrayList<>();
//
//    private MediaPlayer mediaPlayer = new MediaPlayer();
//    private String currentlyPlaying = "";
//
//
//    private ImageView iv_call;
//    private ImageView iv_videocall;
//    static Boolean isGroup;
//
//
////    OnCheckAudioVideoButton onCheckAudioVideoButton;
//
//
//    @BindView(R.id.chatToolbar)
//    Toolbar toolbar;
//    @BindView(R.id.chatToolbarContent)
//    RelativeLayout toolbarContent;
//    @BindView(R.id.selectedCount)
//    TextView selectedCount;
//    @BindView(R.id.add_attachment_layout)
//    TableLayout addAttachmentLayout;
//    @BindView(R.id.users_image)
//    CircleImageView usersImage;
//    @BindView(R.id.emotion)
//    TextView status;
//    @BindView(R.id.user_name)
//    TextView userName;
//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;
//    @BindView(R.id.new_message)
//    EmojiEditText newMessage;
//    @BindView(R.id.add_attachment)
//    ImageView addAttachment;
//    @BindView(R.id.send)
//    ImageView sendMessage;
//    @BindView(R.id.sendContainer)
//    LinearLayout sendContainer;
//    @BindView(R.id.rootView)
//    LinearLayout rootView;
//    @BindView(R.id.attachment_emoji)
//    ImageView attachment_emoji;
////    @BindView(R.id.iv_call)
////    ImageView iv_call;
//
//
//    private String cameraPhotoPath;
//    private CameraModule cameraModule;
//    private EmojiPopup emojIcon;
//
//    private RequestPermissionHandler mRequestPermissionHandler;
//
//    //Download complete listener
//    BroadcastReceiver onComplete = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            switch (intent.getAction()) {
//                case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
//                    if (adapterPositions.size() > 0 && messageAdapter != null)
//                        for (int pos : adapterPositions)
//                            if (pos != -1)
//                                messageAdapter.notifyItemChanged(pos);
//                    adapterPositions.clear();
//                    break;
//            }
//        }
//    };
//
//    @Override
//    void userAdded(User valueUser) {
//        //do nothing
//    }
//
//    @Override
//    void groupAdded(Group valueGroup) {
//        //do nothing
//    }
//
//    @Override
//    public void userUpdated(User valueUser) {
//        if (user != null && user.getId().equals(valueUser.getId())) {
//            valueUser.setNameInPhone(user.getNameInPhone());
//            user = valueUser;
//
//            userName.setCompoundDrawablesWithIntrinsicBounds(user.isOnline() ? R.drawable.ring_green : 0, 0, 0, 0);
//            status.setText(user.getStatus());
//            showTyping(user.isTyping());//Show typing
//        }
//    }
//
//    @Override
//    void groupUpdated(Group valueGroup) {
//        if (group != null && group.getId().equals(valueGroup.getId())) {
//            group = valueGroup;
//            checkIfChatAllowed();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chats);
//        ButterKnife.bind(this);
//
//
//        mRequestPermissionHandler = new RequestPermissionHandler();
//        mRequestPermissionHandler.requestPermission(this, new String[]{
//                Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE
//        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
//            @Override
//            public void onSuccess() {
////                Toast.makeText(ChatDetailActivity.this, "request permission success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailed() {
////                Toast.makeText(ChatDetailActivity.this, "request permission failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        iv_call = (ImageView) findViewById(R.id.iv_call);
//        iv_call.setOnClickListener(this);
//        iv_videocall = (ImageView) findViewById(R.id.iv_videocall);
//        iv_videocall.setOnClickListener(this);
//
//        sendBroadcast(RestartServiceReceiver.newIntent(this));//start main chat service(responsible for fetching user's updates and Chat updates)
//
//
//        Intent intent = getIntent();
//
//
//        if (Build.VERSION.SDK_INT < 21) {
//            exitTransition = ActivityTransition.with(intent).to(usersImage).start(savedInstanceState);
//        }
//
//        setSupportActionBar(toolbar);
//
//        if (intent.hasExtra(EXTRA_DATA_USER)) {
//            user = intent.getParcelableExtra(EXTRA_DATA_USER);
//
//            Helper.CURRENT_CHAT_ID = user.getId();
//        } else if (intent.hasExtra(EXTRA_DATA_GROUP)) {
//            group = intent.getParcelableExtra(EXTRA_DATA_GROUP);
//            Helper.CURRENT_CHAT_ID = group.getId();
//        } else {
//            finish();//temporary fix
//        }
//
//        if (isGroup) {
//            iv_videocall.setVisibility(View.GONE);
//            iv_call.setVisibility(View.GONE);
//        } else {
//            iv_videocall.setVisibility(View.VISIBLE);
//            iv_call.setVisibility(View.VISIBLE);
//        }
//
//        //set basic user info
//        String nameText = null, statusText = null, imageUrl = null;
//        if (user != null) {
//            nameText = user.getNameToDisplay();
//            statusText = user.getStatus();
//            imageUrl = user.getImage();
//        } else if (group != null) {
//            nameText = group.getName();
//            statusText = group.getStatus();
//            imageUrl = group.getImage();
//        }
//        userName.setText(nameText);
////        userName.setCompoundDrawablesWithIntrinsicBounds(user.isOnline() ? R.drawable.ring_green : R.drawable.ring_blue, 0, 0, 0);
//        status.setText(statusText);
//        Glide.with(this).load(imageUrl).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(usersImage);
//
//        animateToolbarViews();
//
//        //setup chat child reference of logged in user and selected user
//        chatChild = user != null ? Helper.getChatChild(user.getId(), userMe.getId()) : group.getId();
//        userOrGroupId = user != null ? user.getId() : group.getId();
//
//        //setup recycler view
//        messageAdapter = new MessageAdapter(this, dataList, userMe.getId(), newMessage);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(messageAdapter);
//        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (bottom < oldBottom) {
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
//                        }
//                    }, 100);
//                }
//            }
//        });
//
//        emojIcon = EmojiPopup.Builder.fromRootView(rootView).setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
//            @Override
//            public void onEmojiPopupShown() {
//                if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
//                    addAttachmentLayout.setVisibility(View.GONE);
//                    addAttachment.animate().setDuration(400).rotationBy(-45).start();
//                }
//            }
//        }).build(newMessage);
////        emojIcon.setUseSystemEmoji(true);
////        newMessage.setUseSystemDefault(true);
////        newMessage.setEmojiconSize(getResources().getDimensionPixelSize(R.dimen.emoji_icon_size));
//
//        displayWidth = Helper.getDisplayWidth(this);
//        sendMessage.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                int x = (int) event.getX();
//                int y = (int) event.getY();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i("TAG", "touched down");
//                        if (newMessage.getText().toString().trim().isEmpty()) {
//                            if (recordWaitHandler == null)
//                                recordWaitHandler = new Handler();
//                            recordRunnable = new Runnable() {
//                                @Override
//                                public void run() {
//                                    recordingStart();
//                                }
//                            };
//                            recordWaitHandler.postDelayed(recordRunnable, 600);
//                        }
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i("TAG", "moving: (" + displayWidth + ", " + x + ")");
//                        if (mRecorder != null && newMessage.getText().toString().trim().isEmpty()) {
//                            if (Math.abs(event.getX()) / displayWidth > 0.35f) {
//                                recordingStop(false);
//                                Toast.makeText(ChatActivity.this, "Recording cancelled", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i("TAG", "touched up");
//                        if (recordWaitHandler != null && newMessage.getText().toString().trim().isEmpty())
//                            recordWaitHandler.removeCallbacks(recordRunnable);
//                        if (mRecorder != null && newMessage.getText().toString().trim().isEmpty()) {
//                            recordingStop(true);
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
//
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                notifyRecordingPlaybackCompletion();
//            }
//        });
//
//        //Query out chat from existing chats whose owner is logged in user and the user is selected user
//        RealmQuery<Chat> query = Helper.getChat(rChatDb, userMe.getId(), userOrGroupId);//rChatDb.where(Chat.class).equalTo("myId", userMe.getId()).equalTo("userId", user.getId());
//        queryResult = query.findAll();
//        queryResult.addChangeListener(realmChangeListener);//register change listener
//        Chat prevChat = query.findFirst();
//        //Add all messages from queried chat into recycler view
//        if (prevChat != null) {
//            dataList.addAll(prevChat.getMessages());
//            messageAdapter.notifyDataSetChanged();
//            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
//        }
//        registerUserUpdates();
//        checkAndForward();
//    }
//
//    private void recordingStop(boolean send) {
//        try {
//            mRecorder.stop();
//            mRecorder.release();
//            mRecorder = null;
//        } catch (IllegalStateException ex) {
//            mRecorder = null;
//        }
//        recordTimerStop();
//        if (send) {
//            newFileUploadTask(recordFilePath, AttachmentTypes.RECORDING, null);
//        } else {
//            new File(recordFilePath).delete();
//        }
//    }
//
//    private void recordingStart() {
//        if (recordPermissionsAvailable()) {
//            File recordFile = new File(Environment.getExternalStorageDirectory(), "/" + getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(AttachmentTypes.RECORDING) + "/.sent/");
//            boolean dirExists = recordFile.exists();
//            if (!dirExists)
//                dirExists = recordFile.mkdirs();
//            if (dirExists) {
//                try {
//                    recordFile = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(AttachmentTypes.RECORDING) + "/.sent/", System.currentTimeMillis() + ".mp3");
//                    if (!recordFile.exists())
//                        recordFile.createNewFile();
//                    recordFilePath = recordFile.getAbsolutePath();
//                    mRecorder = new MediaRecorder();
//                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//                    mRecorder.setOutputFile(recordFilePath);
//                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//                    mRecorder.prepare();
//                    mRecorder.start();
//                    recordTimerStart(System.currentTimeMillis());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    mRecorder = null;
//                } catch (IllegalStateException ex) {
//                    ex.printStackTrace();
//                    mRecorder = null;
//                }
//            }
//        } else {
//            ActivityCompat.requestPermissions(this, recordPermissions, REQUEST_PERMISSION_RECORD);
//        }
//    }
//
//    private void recordTimerStart(final long currentTimeMillis) {
//        Toast.makeText(this, "Recording...", Toast.LENGTH_SHORT).show();
//        recordTimerRunnable = new Runnable() {
//            public void run() {
//                Long elapsedTime = System.currentTimeMillis() - currentTimeMillis;
//                newMessage.setHint(Helper.timeFormater(elapsedTime) + " (Slide left to cancel)");
//                recordTimerHandler.postDelayed(this, 1000);
//            }
//        };
//        if (recordTimerHandler == null)
//            recordTimerHandler = new Handler();
//        recordTimerHandler.post(recordTimerRunnable);
//        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        if (v != null) v.vibrate(100);
//    }
//
//    private void recordTimerStop() {
//        recordTimerHandler.removeCallbacks(recordTimerRunnable);
//        newMessage.setHint("Type your message");
//        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        if (v != null) v.vibrate(100);
//    }
//
//    private boolean recordPermissionsAvailable() {
//        boolean available = true;
//        for (String permission : recordPermissions) {
//            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//                available = false;
//                break;
//            }
//        }
//        return available;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(onComplete);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//        mediaPlayer.release();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (Helper.CHAT_CAB)
//            undoSelectionPrepared();
//        queryResult.removeChangeListener(realmChangeListener);
//        Helper.CURRENT_CHAT_ID = null;
//        markAllReadForThisUser();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (Helper.CHAT_CAB)
//            undoSelectionPrepared();
//        else {
//            KeyboardUtil.getInstance(this).closeKeyboard();
//            if (Build.VERSION.SDK_INT > 21) {
//                finishAfterTransition();
//            } else if (exitTransition != null) {
//                exitTransition.exit(this);
//            } else {
//                finish();
//            }
//        }
//    }
//
//    private void markAllReadForThisUser() {
//        Chat thisChat = Helper.getChat(rChatDb, userMe.getId(), userOrGroupId).findFirst();
//        if (thisChat != null) {
//            rChatDb.beginTransaction();
//            thisChat.setRead(true);
//            rChatDb.commitTransaction();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.action_copy:
//                StringBuilder stringBuilder = new StringBuilder("");
//                for (Message message : dataList) {//Get all selected messages in a String
//                    if (message.isSelected() && !TextUtils.isEmpty(message.getBody())) {
//                        stringBuilder.append(Helper.getTime(message.getDate()));
//                        stringBuilder.append(" ");
//                        if (message.getSenderId() != null && user != null && userMe != null && user.getId() != null && user.getName() != null && userMe.getName() != null)
//                            stringBuilder.append(message.getSenderId().equals(user.getId()) ? user.getName() : userMe.getName());
//                        stringBuilder.append(" : ");
//                        stringBuilder.append(message.getBody());
//                        stringBuilder.append("\n");
//                    }
//                }
//                //Add String in clipboard
//                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("simple text", stringBuilder.toString());
//                clipboard.setPrimaryClip(clip);
//                Toast.makeText(this, "Messages copied", Toast.LENGTH_SHORT).show();
//                undoSelectionPrepared();
//                break;
//            case R.id.action_delete:
//                FragmentManager manager = getSupportFragmentManager();
//                Fragment frag = manager.findFragmentByTag(DELETE_TAG);
//                if (frag != null) {
//                    manager.beginTransaction().remove(frag).commit();
//                }
//
//                ConfirmationDialogFragment confirmationDialogFragment = ConfirmationDialogFragment.newInstance("Delete messages",
//                        "Continue deleting selected messages?",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ArrayList<String> idsToDelete = new ArrayList<>();
//                                for (Message msg : dataList) {//Delete all selected messages
//                                    if (msg.isSelected()) {
//                                        try {
//                                            chatRef.child(chatChild).child(msg.getId()).removeValue();
//                                        } catch (DatabaseException de) {
//                                            Log.e("DatabaseException", de.getMessage());
//                                            if (msg.getAttachment() != null) {
//                                                String idToCompare = "loading" + msg.getAttachment().getBytesCount() + msg.getAttachment().getName();
//                                                idsToDelete.add(idToCompare);
//                                            }
//                                        }
//                                    }
//                                }
//                                for (String idToCompare : idsToDelete) {
//                                    Helper.deleteMessageFromRealm(rChatDb, idToCompare);
//                                }
//                                toolbar.getMenu().clear();
//                                selectedCount.setVisibility(View.GONE);
//                                toolbarContent.setVisibility(View.VISIBLE);
//                                Helper.CHAT_CAB = false;
//                            }
//                        },
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                undoSelectionPrepared();
//                            }
//                        });
//                confirmationDialogFragment.show(manager, DELETE_TAG);
//                break;
//            case R.id.action_forward:
//                ArrayList<Message> forwardList = new ArrayList<>();
//                for (Message msg : dataList)
//                    if (msg.isSelected())
//                        forwardList.add(rChatDb.copyFromRealm(msg));
//                Intent resultIntent = new Intent();
//                resultIntent.putParcelableArrayListExtra("FORWARD_LIST", forwardList);
//                setResult(Activity.RESULT_OK, resultIntent);
//                finish();
//                //undoSelectionPrepared();
//                break;
//        }
//        return true;
//    }
//
//    private void registerUserUpdates() {
//        //Publish logged in user's typing status
//        newMessage.addTextChangedListener(new TextWatcher() {
//            CountDownTimer timer = null;
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                sendMessage.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this, s.length() == 0 ? R.drawable.ic_keyboard_voice_24dp : R.drawable.ic_send));
//
//                if (user != null) {
//                    if (timer != null) {
//                        timer.cancel();
//                        usersRef.child(userMe.getId()).child("typing").setValue(true);
//                    }
//                    timer = new CountDownTimer(1500, 1000) {
//                        public void onTick(long millisUntilFinished) {
//                        }
//
//                        public void onFinish() {
//                            usersRef.child(userMe.getId()).child("typing").setValue(false);
//                        }
//                    }.start();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//
//    private boolean checkIfChatAllowed() {
//        if (group == null)
//            return true;
//        boolean allowed = group.getUserIds().contains(new MyString(userMe.getId()));
//        if (!allowed) {
//            sendContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_gray));
//            newMessage.setText("");
//            newMessage.setHint("You were removed from this group");
//            newMessage.setEnabled(false);
//            addAttachment.setClickable(false);
//            sendMessage.setClickable(false);
//        }
//        return allowed;
//    }
//
//    private void checkAndForward() {
//        Intent intent = getIntent();
//        if (intent.hasExtra(EXTRA_DATA_LIST) && checkIfChatAllowed()) {
//            ArrayList<Message> toForward = intent.getParcelableArrayListExtra(EXTRA_DATA_LIST);
//            if (!toForward.isEmpty()) {
//                for (Message msg : toForward)
//                    sendMessage(msg.getBody(), msg.getAttachmentType(), msg.getAttachment());
//            }
//        }
//    }
//
//    private void showTyping(boolean typing) {
//        if (dataList != null && dataList.size() > 0 && dataList.get(dataList.size() - 1).isValid()) {
//            boolean lastIsTyping = dataList.get(dataList.size() - 1).getAttachmentType() == AttachmentTypes.NONE_TYPING;
//            if (typing && !lastIsTyping) {//if last message is not Typing
//                dataList.add(new Message(AttachmentTypes.NONE_TYPING));
//                messageAdapter.notifyItemInserted(dataList.size() - 1);
//                recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
//            } else if (lastIsTyping && dataList.size() > 0) {//If last is typing and there is a message in list
//                dataList.remove(dataList.size() - 1);
//                messageAdapter.notifyItemRemoved(dataList.size());
//            }
//        }
//    }
//
//    private void animateToolbarViews() {
//        Animation emotionAnimation = AnimationUtils.makeInChildBottomAnimation(this);
//        emotionAnimation.setDuration(400);
//        status.startAnimation(emotionAnimation);
//        Animation nameAnimation = AnimationUtils.makeInChildBottomAnimation(this);
//        nameAnimation.setDuration(420);
//        userName.startAnimation(nameAnimation);
//    }
//
//    private RealmChangeListener<RealmResults<Chat>> realmChangeListener = new RealmChangeListener<RealmResults<Chat>>() {
//        @Override
//        public void onChange(RealmResults<Chat> element) {
//            if (element != null && element.isValid() && element.size() > 0) {
//                RealmList<Message> updatedList = element.get(0).getMessages();//updated list of messages
//                if (updatedList.size() < dataList.size()) {//if updated items after deletion
//                    dataList.clear();
//                    dataList.addAll(element.get(0).getMessages());
//                    messageAdapter.notifyDataSetChanged();
//                } else {// either new or updated message items
//                    showTyping(false);//hide typing indicator
//                    int lastPos = dataList.size() - 1;
//                    Message newMessage = updatedList.get(updatedList.size() - 1);
//                    if (lastPos >= 0 && dataList.get(lastPos).getId().equals(newMessage.getId())) {//Updated message
//                        dataList.set(lastPos, newMessage);
//                        messageAdapter.notifyItemChanged(lastPos);
//                    } else {//new message
//                        dataList.add(newMessage);
//                        messageAdapter.notifyItemInserted(lastPos + 1);
//                    }
//                    recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);//scroll to latest message
//                }
//            }
//        }
//    };
//
//    @OnTouch(R.id.new_message)
//    public boolean onNewMessageTouched() {
//        if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
//            addAttachmentLayout.setVisibility(View.GONE);
//            addAttachment.animate().setDuration(400).rotationBy(-45).start();
//        }
//        return false;
//    }
//
//    @OnClick({R.id.back_button, R.id.add_attachment, R.id.send, R.id.chatToolbarContent, R.id.iv_call})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.back_button:
//                Helper.closeKeyboard(this, view);
//                onBackPressed();
//                break;
//            case R.id.add_attachment:
//                Helper.closeKeyboard(this, view);
//                if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
//                    addAttachmentLayout.setVisibility(View.GONE);
//                    addAttachment.animate().setDuration(400).rotationBy(-45).start();
//                } else {
//                    addAttachmentLayout.setVisibility(View.VISIBLE);
//                    addAttachment.animate().setDuration(400).rotationBy(45).start();
//                    emojIcon.dismiss();
//                }
//                break;
//            case R.id.send:
//                if (!TextUtils.isEmpty(newMessage.getText().toString().trim())) {
//                    sendMessage(newMessage.getText().toString(), AttachmentTypes.NONE_TEXT, null);
//                    newMessage.setText("");
//                }
//                break;
//            case R.id.chatToolbarContent:
//                if (toolbarContent.getVisibility() == View.VISIBLE) {
//                    if (user != null) {
//                        startActivityForResult(ChatDetailActivity.newIntent(this, user), REQUEST_CODE_UPDATE_USER);
//                    } else if (group != null)
//                        startActivityForResult(ChatDetailActivity.newIntent(this, group), REQUEST_CODE_UPDATE_GROUP);
//                }
////                break;
////            case R.id.iv_call:
////                DialogCallSelection dialogCallSelection = new DialogCallSelection(getApplicationContext());
////                dialogCallSelection.Set(this);
////                dialogCallSelection.show();
//
////                Call call = getSinchServiceInterface().callUser(user.getId(), null);
////                String callId = call.getCallId();
////
////                Intent callScreen = new Intent(getApplicationContext(), CallScreenActivity.class);
////                callScreen.putExtra(SinchService.CALL_ID, callId);
////                callScreen.putExtra("name", user.getNameInPhone());
////                startActivity(callScreen);
//
////                break;
//        }
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_call:
////                if (user.isOnline()) {
//                Call call = getSinchServiceInterface().callUser(user.getId(), null);
//                String callId = call.getCallId();
//
//                Intent callScreen = new Intent(getApplicationContext(), CallScreenActivity.class);
//                callScreen.putExtra(SinchService.CALL_ID, callId);
//                callScreen.putExtra("name", user.getNameInPhone());
////                onCheckAudioVideoButton.OnAudioVideoClick(false);
//                if (user.getImage() != null) {
//                    callScreen.putExtra("image", user.getImage());
//                }
//                callScreen.putExtra("video", false);
//                startActivity(callScreen);
////                DialogCallSelection dialogCallSelection = new DialogCallSelection(ChatActivity.this);
////                dialogCallSelection.Set(ChatActivity.this);
////                dialogCallSelection.show();
////                } else {
////                    Toast.makeText(this, "User is Offline You not able to call.", Toast.LENGTH_SHORT).show();
////                }
//                break;
//            case R.id.iv_videocall:
////                if (user.isOnline()) {
//                Call call_video = getSinchServiceInterface().callUserVideo(user.getId());
//                String callId_video = call_video.getCallId();
//
//                Intent callScreen_video = new Intent(getApplicationContext(), CallScreenActivity.class);
//                callScreen_video.putExtra(SinchService.CALL_ID, callId_video);
//                callScreen_video.putExtra("name", user.getNameInPhone());
//                callScreen_video.putExtra("video", true);
////                onCheckAudioVideoButton.OnAudioVideoClick(true);
//                startActivity(callScreen_video);
////                } else {
////                    Toast.makeText(this, "User is Offline You not able to Video call.", Toast.LENGTH_SHORT).show();
////                }
//                break;
//        }
//
//    }
//
//    @Override
//    public void SaveButton(boolean Issave) {
////        if (Issave) {
////            Call call = getSinchServiceInterface().callUser(user.getId(), null);
////            String callId = call.getCallId();
////
////            Intent callScreen = new Intent(getApplicationContext(), CallScreenActivity.class);
////            callScreen.putExtra(SinchService.CALL_ID, callId);
////            callScreen.putExtra("name", user.getNameInPhone());
////            startActivity(callScreen);
////        } else {
////            Toast.makeText(this, "video", Toast.LENGTH_SHORT).show();
////        }
//    }
//
//    private void prepareMessage(String body, int attachmentType, Attachment attachment) {
//        Message message = new Message();
//        message.setAttachmentType(attachmentType);
//        message.setAttachment(attachment);
//        message.setBody(body);
//        message.setDate(System.currentTimeMillis());
//        message.setSenderId(userMe.getId());
//        message.setSenderName(userMe.getName());
//        message.setSent(false);
//        message.setDelivered(false);
//        message.setRecipientId(userOrGroupId);
//        message.setId(attachment.getUrl() + attachment.getBytesCount() + attachment.getName());
//
//        Helper.deleteMessageFromRealm(rChatDb, message.getId());
//
//        //Loading attachment message
//
//        String userId = message.getRecipientId();
//        String myId = message.getSenderId();
//        Chat chat = Helper.getChat(rChatDb, myId, userId).findFirst();//rChatDb.where(Chat.class).equalTo("myId", myId).equalTo("userId", userId).findFirst();
//        rChatDb.beginTransaction();
//        if (chat == null) {
//            chat = rChatDb.createObject(Chat.class);
//            chat.setMessages(new RealmList<Message>());
//            chat.setLastMessage(message.getBody());
//            chat.setMyId(myId);
//            chat.setTimeUpdated(message.getDate());
//            if (user != null) {
//                chat.setUser(rChatDb.copyToRealm(user));
//                chat.setUserId(userId);
//            } else {
//                chat.setGroupId(group.getId());
//                chat.setGroup(rChatDb.copyToRealm(group));
//            }
//        }
//        chat.setTimeUpdated(message.getDate());
//        chat.getMessages().add(message);
//        chat.setLastMessage(message.getBody());
//        rChatDb.commitTransaction();
//    }
//
//    private void sendMessage(String messageBody, @AttachmentTypes.AttachmentType int attachmentType, Attachment attachment) {
//        //Create message object
//        Message message = new Message();
//        message.setAttachmentType(attachmentType);
//        if (attachmentType != AttachmentTypes.NONE_TEXT)
//            message.setAttachment(attachment);
//        else
//            BaseMessageViewHolder.animate = true;
//        message.setBody(messageBody);
//        message.setDate(System.currentTimeMillis());
//        message.setSenderId(userMe.getId());
//        message.setSenderName(userMe.getName());
//        message.setSent(true);
//        message.setDelivered(false);
//        message.setRecipientId(userOrGroupId);
//        message.setId(chatRef.child(chatChild).push().getKey());
//
//        //Add messages in chat child
//        chatRef.child(chatChild).child(message.getId()).setValue(message);
//
//    }
//
//    @OnClick({R.id.attachment_contact, R.id.attachment_emoji, R.id.attachment_gallery, R.id.attachment_audio, R.id.attachment_location, R.id.attachment_document})
//    public void onAttachmentClicked(View view) {
//        switch (view.getId()) {
//            case R.id.attachment_contact:
//                ChatActivityPermissionsDispatcher.openContactPickerWithCheck(this);
//                break;
////            case R.id.attachment_camera:
////                ChatActivityPermissionsDispatcher.dispatchTakePictureIntentWithCheck(this);
////                break;
//            case R.id.attachment_emoji:
//                emojIcon.toggle();
//                break;
//            case R.id.attachment_gallery:
//                ChatActivityPermissionsDispatcher.selectPicWithCheck(this);
//                break;
//            case R.id.attachment_audio:
//                ChatActivityPermissionsDispatcher.openAudioPickerWithCheck(this);
//                break;
//            case R.id.attachment_location:
//                openPlacePicker();
//                break;
//            case R.id.attachment_document:
//                ChatActivityPermissionsDispatcher.selectDocumentWithCheck(this);
//                break;
//        }
//    }
//
//    private void checkAndCopy(String directory, File source) {
//        //Create and copy file content
//        File file = new File(Environment.getExternalStorageDirectory(), directory);
//        boolean dirExists = file.exists();
//        if (!dirExists)
//            dirExists = file.mkdirs();
//        if (dirExists) {
//            try {
//                file = new File(Environment.getExternalStorageDirectory() + directory, Uri.fromFile(source).getLastPathSegment());
//                boolean fileExists = file.exists();
//                if (!fileExists)
//                    fileExists = file.createNewFile();
//                if (fileExists && file.length() == 0) {
//                    FileUtils.copyFile(source, file);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void openPlacePicker() {
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        try {
//            startActivityForResult(builder.build(this), REQUEST_PLACE_PICKER);
//        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
//            GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//            googleApiAvailability.showErrorDialogFragment(this, googleApiAvailability.isGooglePlayServicesAvailable(this), REQUEST_CODE_PLAY_SERVICES);
//            e.printStackTrace();
//        }
//    }
//
//    @NeedsPermission({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.RECORD_AUDIO})
//    void openContactPicker() {
//        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//        startActivityForResult(contactPickerIntent, REQUEST_CODE_CONTACT);
//    }
//
//    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    void openAudioPicker() {
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                new RingtonePickerDialog.Builder(getApplicationContext(), getSupportFragmentManager())
//                        .setTitle("Select audio")
//                        .addRingtoneType(RingtonePickerDialog.Builder.TYPE_MUSIC)
//                        .setPositiveButtonText("OK")
//                        .setCancelButtonText("CANCEL")
//                        .setPlaySampleWhileSelection(true)
//                        .setListener(new RingtonePickerListener() {
//                            @Override
//                            public void OnRingtoneSelected(String ringtoneName, Uri ringtoneUri) {
//                                newFileUploadTask(FileUtils.getPath(getApplicationContext(), ringtoneUri), AttachmentTypes.AUDIO, null);
//                            }
//                        })
//                        .show();
//
//            }
//        });
//    }
//
//    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
//    public void selectPic() {
//        ImagePicker.create(this)
//                .folderMode(true)
//                .showCamera(true)
//                .theme(R.style.AppTheme)
//                .single()
//                .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
//    }
//
//    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    public void selectDocument() {
//        FilePickerBuilder.getInstance().setMaxCount(1)
//                .setSelectedFiles(new ArrayList<String>())
//                .setActivityTheme(R.style.AppThemeWithActionBar)
//                .pickFile(this);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // NOTE: delegate the permission handling to generated method
//        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
//                grantResults);
//        ChatActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
//
//    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
//    void showRationaleForCamera(PermissionRequest request) {
//        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
//        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
//        showRationaleDialog(R.string.permission_read_ext_storage_rationale, request);
//    }
//
//    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
//    void showDeniedForCamera() {
//        Toast.makeText(this, R.string.permission_read_ext_storage_denied, Toast.LENGTH_SHORT).show();
//    }
//
//    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
//    void showNeverAskForCamera() {
//        Toast.makeText(this, R.string.permission_read_ext_storage_never_askagain, Toast.LENGTH_SHORT).show();
//    }
//
//    @OnShowRationale(Manifest.permission.READ_CONTACTS)
//    void showRationaleForContact(PermissionRequest request) {
//        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
//        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
//        showRationaleDialog(R.string.permission_read_contact_rationale, request);
//    }
//
//    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
//    void showDeniedForContact() {
//        Toast.makeText(this, R.string.permission_read_contact_denied, Toast.LENGTH_SHORT).show();
//    }
//
//    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
//    void showNeverAskForContact() {
//        Toast.makeText(this, R.string.permission_read_contact_never_askagain, Toast.LENGTH_SHORT).show();
//    }
//
//    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    void showRationaleForDownloadingFile(PermissionRequest request) {
//        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
//        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
//        showRationaleDialog(R.string.permission_write_ext_storage_rationale, request);
//    }
//
//    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    void showDeniedForDownloadingFile() {
//        Toast.makeText(this, R.string.permission_write_ext_storage_denied, Toast.LENGTH_SHORT).show();
//    }
//
//    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    void showNeverAskForDownloadingFile() {
//        Toast.makeText(this, R.string.permission_write_ext_storage_never_askagain, Toast.LENGTH_SHORT).show();
//    }
//
//    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
//        new AlertDialog.Builder(this)
//                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(@NonNull DialogInterface dialog, int which) {
//                        request.proceed();
//                    }
//                })
//                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(@NonNull DialogInterface dialog, int which) {
//                        request.cancel();
//                    }
//                })
//                .setCancelable(false)
//                .setMessage(messageResId)
//                .show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_CODE_UPDATE_USER:
//                    user = data.getParcelableExtra(EXTRA_DATA_USER);
//                    userUpdated(user);
//                    break;
//                case REQUEST_CODE_UPDATE_GROUP:
//                    group = data.getParcelableExtra(EXTRA_DATA_GROUP);
//                    groupUpdated(group);
//                    break;
//                case REQUEST_CODE_PICKER:
//                    if (data != null) {
//                        ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
//                        String filePath = images.get(0).getPath();
//                        String mimeType = FileUtils.getMimeType(new File(filePath));
//                        if (mimeType.contains("video")) uploadThumbnail(filePath);
//                        else uploadImage(filePath);
//                    }
//                    break;
//                case FilePickerConst.REQUEST_CODE_DOC:
//                    if (data != null) {
//                        ArrayList<String> filePaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);
//                        if (filePaths != null && filePaths.size() > 0) {
//                            newFileUploadTask(filePaths.get(0), AttachmentTypes.DOCUMENT, null);
//                            // TODO: 10/5/17 upload images to firebase
//                        }
//                    }
//                    break;
//                case REQUEST_CODE_CONTACT:
//                    getSendVCard(data.getData());
//                    break;
//                case REQUEST_PLACE_PICKER:
//                    Place place = PlacePicker.getPlace(this, data);
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("address", place.getAddress().toString());
//                        jsonObject.put("latitude", place.getLatLng().latitude);
//                        jsonObject.put("longitude", place.getLatLng().longitude);
//                        Attachment attachment = new Attachment();
//                        attachment.setData(jsonObject.toString());
//                        sendMessage(null, AttachmentTypes.LOCATION, attachment);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case REQUEST_CODE_PLAY_SERVICES:
//                    openPlacePicker();
//                    break;
//                case REQUEST_CAMERA:
//                    getCameraModule().getImage(this, data, new OnImageReadyListener() {
//                        @Override
//                        public void onImageReady(List<Image> resultImages) {
//                            if (resultImages != null)
//                                uploadImage(Environment.getExternalStorageDirectory() + "/Pictures/Camera/" + resultImages.get(0).getName());
//                        }
//                    });
//                    break;
//            }
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        cameraPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
//    void dispatchTakePictureIntent() {
//        startActivityForResult(getCameraModule().getCameraIntent(this), REQUEST_CAMERA);
////        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        // Ensure that there's a camera activity to handle the intent
////        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
////            // Create the File where the photo should go
////            File photoFile = null;
////            try {
////                photoFile = createImageFile();
////            } catch (IOException ex) {
////                // Error occurred while creating the File
////                ex.printStackTrace();
////            }
////            // Continue only if the File was successfully created
////            if (photoFile != null && photoFile.exists()) {
////                //Uri photoURI = MyFileProvider.getUriForFile(this, getString(R.string.authority), photoFile);
////                Uri photoURI = Uri.parse(cameraPhotoPath);
////                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
////                takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
////            }
////        }
//    }
//
//    private ImmediateCameraModule getCameraModule() {
//        if (cameraModule == null) {
//            cameraModule = new ImmediateCameraModule();
//        }
//        return (ImmediateCameraModule) cameraModule;
//    }
//
//    private void getSendVCard(Uri contactsData) {
//        AsyncTask<Cursor, Void, File> task = new AsyncTask<Cursor, Void, File>() {
//            String vCardData;
//
//            @Override
//            protected File doInBackground(Cursor... params) {
//                Cursor cursor = params[0];
//                File toSend = new File(Environment.getExternalStorageDirectory(), "/" + getString(R.string.app_name) + "/Contact/.sent/");
//                if (cursor != null && !cursor.isClosed()) {
//                    cursor.getCount();
//                    if (cursor.moveToFirst()) {
//                        String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
//                        String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
//                        try {
//                            AssetFileDescriptor assetFileDescriptor = getContentResolver().openAssetFileDescriptor(uri, "r");
//                            if (assetFileDescriptor != null) {
//                                FileInputStream inputStream = assetFileDescriptor.createInputStream();
//                                boolean dirExists = toSend.exists();
//                                if (!dirExists)
//                                    dirExists = toSend.mkdirs();
//                                if (dirExists) {
//                                    try {
//                                        toSend = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/Contact/.sent/", name + ".vcf");
//                                        boolean fileExists = toSend.exists();
//                                        if (!fileExists)
//                                            fileExists = toSend.createNewFile();
//                                        if (fileExists) {
//                                            OutputStream stream = new BufferedOutputStream(new FileOutputStream(toSend, false));
//                                            byte[] buffer = readAsByteArray(inputStream);
//                                            vCardData = new String(buffer);
//                                            stream.write(buffer);
//                                            stream.close();
//                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        } catch (FileNotFoundException e) {
//                            Log.e(ChatActivity.class.getSimpleName(), "Vcard for the contact " + lookupKey + " not found", e);
//                        } catch (IOException e) {
//                            Log.e(ChatActivity.class.getSimpleName(), "Problem creating stream from the assetFileDescriptor.", e);
//                        } finally {
//                            cursor.close();
//                        }
//                    }
//                }
//                return toSend;
//            }
//
//            @Override
//            protected void onPostExecute(File f) {
//                super.onPostExecute(f);
//                if (f != null && !TextUtils.isEmpty(vCardData)) {
//                    Attachment attachment = new Attachment();
//                    attachment.setData(vCardData);
//                    newFileUploadTask(f.getAbsolutePath(), AttachmentTypes.CONTACT, attachment);
//                }
//            }
//        };
//        task.execute(getContentResolver().query(contactsData, null, null, null, null));
//    }
//
//    public byte[] readAsByteArray(InputStream ios) throws IOException {
//        ByteArrayOutputStream ous = null;
//        try {
//            byte[] buffer = new byte[4096];
//            ous = new ByteArrayOutputStream();
//            int read = 0;
//            while ((read = ios.read(buffer)) != -1) {
//                ous.write(buffer, 0, read);
//            }
//        } finally {
//            try {
//                if (ous != null)
//                    ous.close();
//            } catch (IOException e) {
//            }
//
//            try {
//                if (ios != null)
//                    ios.close();
//            } catch (IOException e) {
//            }
//        }
//        return ous.toByteArray();
//    }
//
//    private void uploadImage(String filePath) {
//        //Compress image
//        File fileToUpload = new File(filePath);
//        fileToUpload = ImageCompressorUtil.compressImage(this, fileToUpload);
//        filePath = fileToUpload.getAbsolutePath();
//        newFileUploadTask(filePath, AttachmentTypes.IMAGE, null);
//    }
//
//    private void uploadThumbnail(final String filePath) {
//        Toast.makeText(this, "Just a moment..", Toast.LENGTH_LONG).show();
//        File file = new File(filePath);
//        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE)
//                .child(getString(R.string.app_name)).child("video").child("thumbnail").child(file.getName() + ".jpg");
//        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                //If thumbnail exists
//                Attachment attachment = new Attachment();
//                attachment.setData(uri.toString());
//                newFileUploadTask(filePath, AttachmentTypes.VIDEO, attachment);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                AsyncTask<String, Void, Bitmap> thumbnailTask = new AsyncTask<String, Void, Bitmap>() {
//                    @Override
//                    protected Bitmap doInBackground(String... params) {
//                        //Create thumbnail
//                        return ThumbnailUtils.createVideoThumbnail(params[0], MediaStore.Video.Thumbnails.MINI_KIND);
//                    }
//
//                    @Override
//                    protected void onPostExecute(Bitmap bitmap) {
//                        super.onPostExecute(bitmap);
//                        if (bitmap != null) {
//                            //Upload thumbnail and then upload video
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                            byte[] data = baos.toByteArray();
//                            UploadTask uploadTask = storageReference.putBytes(data);
//                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Attachment attachment = new Attachment();
//                                    //noinspection VisibleForTests
//                                    attachment.setData(taskSnapshot.getDownloadUrl().toString());
//                                    newFileUploadTask(filePath, AttachmentTypes.VIDEO, attachment);
//                                }
//                            });
//                            uploadTask.addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    newFileUploadTask(filePath, AttachmentTypes.VIDEO, null);
//                                }
//                            });
//                        } else
//                            newFileUploadTask(filePath, AttachmentTypes.VIDEO, null);
//                    }
//                };
//                thumbnailTask.execute(filePath);
//            }
//        });
//    }
//
//    private void newFileUploadTask(String filePath, @AttachmentTypes.AttachmentType final int attachmentType, final Attachment attachment) {
//        if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
//            addAttachmentLayout.setVisibility(View.GONE);
//            addAttachment.animate().setDuration(400).rotationBy(-45).start();
//        }
//
//        final File fileToUpload = new File(filePath);
//        final String fileName = Uri.fromFile(fileToUpload).getLastPathSegment();
//
//        Attachment preSendAttachment = attachment;//Create/Update attachment
//        if (preSendAttachment == null) preSendAttachment = new Attachment();
//        preSendAttachment.setName(fileName);
//        preSendAttachment.setBytesCount(fileToUpload.length());
//        preSendAttachment.setUrl("loading");
//        prepareMessage(null, attachmentType, preSendAttachment);
//
//        checkAndCopy("/" + getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(attachmentType) + "/.sent/", fileToUpload);//Make a copy
//
//        Intent intent = new Intent(Helper.UPLOAD_AND_SEND);
//        intent.putExtra("attachment", attachment);
//        intent.putExtra("attachment_type", attachmentType);
//        intent.putExtra("attachment_file_path", filePath);
//        intent.putExtra("attachment_file_path", filePath);
//        intent.putExtra("attachment_recipient_id", userOrGroupId);
//        intent.putExtra("attachment_chat_child", chatChild);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//
////        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE)
////                .child(getString(R.string.app_name)).child(AttachmentTypes.getTypeName(attachmentType)).child(fileName);
////        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////            @Override
////            public void onSuccess(Uri uri) {
////                //If file is already uploaded
////                Attachment attachment1 = attachment;
////                if (attachment1 == null) attachment1 = new Attachment();
////                attachment1.setName(fileName);
////                attachment1.setUrl(uri.toString());
////                attachment1.setBytesCount(fileToUpload.length());
////                sendMessage(null, attachmentType, attachment1);
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception exception) {
////                //Elase upload and then send message
////                UploadTask uploadTask = storageReference.putFile(Uri.fromFile(fileToUpload));
////                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                    @Override
////                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                        //noinspection VisibleForTests
////                        StorageMetadata storageMetadata = taskSnapshot.getMetadata();
////                        //noinspection VisibleForTests
////                        Attachment attachment1 = attachment;
////                        if (attachment1 == null) attachment1 = new Attachment();
////                        attachment1.setName(storageMetadata.getName());
////                        attachment1.setUrl(storageMetadata.getDownloadUrl().toString());
////                        attachment1.setBytesCount(storageMetadata.getSizeBytes());
////                        sendMessage(null, attachmentType, attachment1);
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Log.e("DatabaseException", e.getMessage());
////                    }
////                });
////            }
////        });
//    }
//
//    @Subscribe
//    public void downloadFileEvent(DownloadFileEvent downloadFileEvent) {
//        ChatActivityPermissionsDispatcher.downloadFileWithCheck(this, downloadFileEvent);
//    }
//
//    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
//    public void downloadFile(DownloadFileEvent downloadFileEvent) {
//        new DownloadUtil().checkAndLoad(this, downloadFileEvent);
//        adapterPositions.add(downloadFileEvent.getPosition());
//    }
//
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
//        toolbarContent.setVisibility(View.GONE);
//        Helper.CHAT_CAB = true;
//    }
//
//    private void undoSelectionPrepared() {
//        for (Message msg : dataList) {
//            msg.setSelected(false);
//        }
//        messageAdapter.notifyDataSetChanged();
//        toolbar.getMenu().clear();
//        selectedCount.setVisibility(View.GONE);
//        toolbarContent.setVisibility(View.VISIBLE);
//        Helper.CHAT_CAB = false;
//    }
//
//    public static Intent newIntent(Context context, ArrayList<Message> forwardMessages, User user, Boolean IsGroup) {
//        //intent contains user to chat with and message forward list if any.
//        Intent intent = new Intent(context, ChatActivity.class);
//        intent.putExtra(EXTRA_DATA_USER, user);
//        isGroup = IsGroup;
//        //intent.removeExtra(EXTRA_DATA_GROUP);
//        if (forwardMessages == null)
//            forwardMessages = new ArrayList<>();
//        intent.putParcelableArrayListExtra(EXTRA_DATA_LIST, forwardMessages);
//        return intent;
//    }
//
//    public static Intent newIntent(Context context, ArrayList<Message> forwardMessages, Group group, Boolean IsGroup) {
//        //intent contains user to chat with and message forward list if any.
//        Intent intent = new Intent(context, ChatActivity.class);
//        intent.putExtra(EXTRA_DATA_GROUP, group);
//        isGroup = IsGroup;
//        //intent.removeExtra(EXTRA_DATA_USER);
//        if (forwardMessages == null)
//            forwardMessages = new ArrayList<>();
//        intent.putParcelableArrayListExtra(EXTRA_DATA_LIST, forwardMessages);
//        return intent;
//    }
//
//    @Override
//    public boolean isRecordingPlaying(String fileName) {
//        return isMediaPlayerPlaying() && currentlyPlaying.equals(fileName);
//    }
//
//    private boolean isMediaPlayerPlaying() {
//        try {
//            return mediaPlayer.isPlaying();
//        } catch (IllegalStateException ex) {
//            return false;
//        }
//    }
//
//    @Override
//    public void playRecording(File file, String fileName, int position) {
//        if (recordPermissionsAvailable()) {
//            if (isMediaPlayerPlaying()) {
//                mediaPlayer.stop();
//                notifyRecordingPlaybackCompletion();
//                if (!fileName.equals(currentlyPlaying)) {
//                    if (startPlayback(file)) {
//                        currentlyPlaying = fileName;
//                        messageAdapter.notifyItemChanged(position);
//                    }
//                }
//            } else {
//                if (startPlayback(file)) {
//                    currentlyPlaying = fileName;
//                    messageAdapter.notifyItemChanged(position);
//                }
//            }
//        } else {
//            ActivityCompat.requestPermissions(this, recordPermissions, REQUEST_PERMISSION_RECORD);
//        }
//    }
//
//    private boolean startPlayback(File file) {
//        boolean started = true;
//        resetMediaPlayer();
//        try {
//            FileInputStream is = new FileInputStream(file);
//            FileDescriptor fd = is.getFD();
//            mediaPlayer.setDataSource(fd);
//            is.close();
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//            started = false;
//        }
//        return started;
//    }
//
//    private void resetMediaPlayer() {
//        try {
//            mediaPlayer.reset();
//        } catch (IllegalStateException ex) {
//            mediaPlayer = new MediaPlayer();
//        }
//    }
//
//    private void notifyRecordingPlaybackCompletion() {
//        if (recyclerView != null && messageAdapter != null) {
//            int total = dataList.size();
//            for (int i = total - 1; i >= 0; i--) {
//                if (dataList.get(i).getAttachment() != null
//                        &&
//                        dataList.get(i).getAttachment().getName().equals(currentlyPlaying)) {
//                    messageAdapter.notifyItemChanged(i);
//                    break;
//                }
//            }
//        }
//    }
//
////    @Override
////    public void onStartFailed(SinchError error) {
////
////    }
////
////    @Override
////    public void onStarted() {
////
////    }
//
//
////    @Override
////    protected void onServiceConnected() {
////        getSinchServiceInterface().setStartListener(this);
////        getSinchServiceInterface().startClient(userMe.getId());
////    }
//
////    @Override
////    public void onStartFailed(SinchError error) {
////
////    }
////
////    @Override
////    public void onStarted() {
////
////    }
}
