package com.verbosetech.yoohoo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.models.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by a_man on 31-12-2017.
 */

public class GroupNewParticipantsAdapter extends RecyclerView.Adapter<GroupNewParticipantsAdapter.MyViewHolder> {
    private User userMe;
    private GroupNewParticipantsAdapter groupNewParticipantsAdapter;
    private Context context;
    private ArrayList<User> dataList;
    private boolean staggered;
    private ParticipantClickListener participantClickListener;

    public GroupNewParticipantsAdapter(Fragment fragment, ArrayList<User> selectedUsers) {
        this.context = fragment.getActivity();
        this.dataList = selectedUsers;
        this.staggered = true;
    }

    public GroupNewParticipantsAdapter(Fragment fragment, ArrayList<User> selectedUsers, GroupNewParticipantsAdapter groupNewParticipantsAdapter) {
        if (fragment instanceof ParticipantClickListener) {
            this.participantClickListener = (ParticipantClickListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString() + " must implement ParticipantClickListener");
        }

        this.context = fragment.getActivity();
        this.dataList = selectedUsers;
        this.staggered = false;
        this.groupNewParticipantsAdapter = groupNewParticipantsAdapter;
    }

    public GroupNewParticipantsAdapter(Fragment fragment, ArrayList<User> selectedUsers, boolean staggered) {
        this.context = fragment.getActivity();
        this.dataList = selectedUsers;
        this.staggered = staggered;
    }

    public GroupNewParticipantsAdapter(Fragment fragment, ArrayList<User> selectedUsers, User userMe) {
        if (fragment instanceof ParticipantClickListener) {
            this.participantClickListener = (ParticipantClickListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString() + " must implement ParticipantClickListener");
        }

        this.context = fragment.getActivity();
        this.dataList = selectedUsers;
        this.staggered = false;
        this.userMe = userMe;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(staggered ? R.layout.item_selected_user : R.layout.item_menu_user_group, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;


        private CircleImageView userImage;
        private AppCompatRadioButton userSelected;
        private ImageView removeUser;
        private TextView user_no;
        private TextView tv_invite;

        public MyViewHolder(View itemView) {
            super(itemView);
//            tv_invite = itemView.findViewById(R.id.tv_invite);
//            tv_invite.setVisibility(View.INVISIBLE);
//            user_no = itemView.findViewById(R.id.user_no);
//            user_no.setVisibility(View.INVISIBLE);

            user_no = (TextView) itemView.findViewById(R.id.user_no);
            tv_invite = (TextView) itemView.findViewById(R.id.tv_invite);

            userName = itemView.findViewById(R.id.user_name);
            if (!staggered) {
                userImage = itemView.findViewById(R.id.user_image);
                userSelected = itemView.findViewById(R.id.userSelected);
                if (userMe != null) {
                    removeUser = itemView.findViewById(R.id.removeUser);
                    removeUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int pos = getAdapterPosition();
                            participantClickListener.onParticipantClick(pos, dataList.get(pos));
                        }
                    });
                }
                if (groupNewParticipantsAdapter != null) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int pos = getAdapterPosition();
                            User user = dataList.get(pos);
                            user.setSelected(!user.isSelected());
                            notifyItemChanged(pos);

                            int index = groupNewParticipantsAdapter.getDataList().indexOf(user);
                            if (index == -1) {
                                groupNewParticipantsAdapter.getDataList().add(user);
                                groupNewParticipantsAdapter.notifyItemInserted(groupNewParticipantsAdapter.getDataList().size() - 1);
                            } else {
                                groupNewParticipantsAdapter.getDataList().remove(index);
                                groupNewParticipantsAdapter.notifyItemRemoved(index);
                            }
                        }
                    });
                }
            }
        }

        public void setData(User user) {
            userName.setText(user.getNameToDisplay());
//            tv_invite.setVisibility(View.GONE);
//            user_no.setVisibility(View.GONE);
            if (!staggered) {
                if (groupNewParticipantsAdapter != null) {
                    userSelected.setVisibility(View.VISIBLE);
                    userSelected.setChecked(user.isSelected());
                }
                Glide.with(context).load(user.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);
            }
            if (removeUser != null)
                removeUser.setVisibility((userMe.getId().equals(user.getId()) || userMe.getId().equals(dataList.get(0).getId())) ? View.VISIBLE : View.GONE);
        }
    }

    private ArrayList<User> getDataList() {
        return dataList;
    }

    public interface ParticipantClickListener {
        void onParticipantClick(int pos, User participant);
    }

}
