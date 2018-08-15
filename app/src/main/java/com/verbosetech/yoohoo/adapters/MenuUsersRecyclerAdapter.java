package com.verbosetech.yoohoo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.interfaces.OnUserGroupItemClick;
import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.OnTextChangeTextview;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mayank on 7/5/17.
 */

public class MenuUsersRecyclerAdapter extends RecyclerView.Adapter<MenuUsersRecyclerAdapter.BaseViewHolder> implements Filterable, OnTextChangeTextview {
    private Context context;
    private OnUserGroupItemClick itemClickListener;
    private ArrayList<User> dataList, dataListFiltered;
    private Filter filter;
    private ArrayList<Contact> contactArrayList, filtercontactlist;
    Map<String, ArrayList<String>> usermap, contactmap;
    int i;

    public MenuUsersRecyclerAdapter(@NonNull Context context, @Nullable ArrayList<User> users, @Nullable ArrayList<Contact> contactArrayList1, Map<String, ArrayList<String>> multimapUser) {
        if (context instanceof OnUserGroupItemClick) {
            this.itemClickListener = (OnUserGroupItemClick) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnUserGroupItemClick");
        }
        
        this.context = context;
        this.dataList = users;
        this.dataListFiltered = users;
        this.contactArrayList = contactArrayList1;
        filtercontactlist = new ArrayList<>();
        dataListFiltered = new ArrayList<>();
        filtercontactlist.addAll(contactArrayList1);
        dataListFiltered.addAll(users);
//        this.filtercontactlist = contactArrayList1;


        this.filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
//                    dataListFiltered = dataList;
//                    filtercontactlist = contactArrayList;


                } else {
//                    Map<String, ArrayList<String>> map14 = new HashMap<String, ArrayList<String>>();
//                    if (map13.containsKey(charSequence))
//                    {
//
//                    }

//                    ArrayList<User> filteredList = new ArrayList<>();
//                    for (User row : dataList) {
//                        String toCheckWith = row.getNameInPhone() != null ? row.getNameInPhone() : row.getName();
//                        if (toCheckWith.toLowerCase().startsWith(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    ArrayList<Contact> conlist = new ArrayList<>();
//
//                    for (Contact row : contactArrayList) {
//                        String toCheckWith = row.getName() != null ? row.getName() : row.getName();
//                        if (toCheckWith.toLowerCase().startsWith(charString.toLowerCase())) {
//                            conlist.add(row);
//                        }
//                    }
//                    dataListFiltered = filteredList;
//                    filtercontactlist = conlist;

                }

                FilterResults filterResults = new FilterResults();
//                filterResults.values = dataListFiltered;


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filtercontactlist = (ArrayList<Contact>) filterResults.values;
//                dataListFiltered = (ArrayList<User>) filterResults.values;


//                notifyDataSetChanged();
            }
        };
    }

    public void filter(String chartext) {
        filtercontactlist.clear();
        dataListFiltered.clear();
        if (chartext.length() == 0) {
            filtercontactlist.addAll(contactArrayList);
            dataListFiltered.addAll(dataList);
        } else {
            for (Contact wp : contactArrayList) {
                if (wp.getName().toLowerCase().contains(chartext)) {
                    filtercontactlist.add(wp);
                    // filtercontactlist.add(wp);
                }
            }

            for (User wp : dataList) {
                if (wp.getNameInPhone().toLowerCase().contains(chartext)) {
                    dataListFiltered.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_user, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof UsersViewHolder) {
//            Object[] keysContact = contactmap.keySet().toArray();
//            Object[] keysUser= usermap.keySet().toArray();

            if (position < dataListFiltered.size()) {
                ((UsersViewHolder) holder).setData(dataListFiltered.get(position), null, null);
            } else {
                ((UsersViewHolder) holder).setData(null, filtercontactlist.get(position), null);
            }
        }
    }

    @Override
    public int getItemCount() {
        i = filtercontactlist.size();
        return i;

    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    @Override
    public void TextChangeTextview(String i, String l) {

        Log.d("text value :", "" + i);
        Log.d("lenfth :", l);
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    class UsersViewHolder extends BaseViewHolder {
        @BindView(R.id.user_image)
        CircleImageView userImage;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.user_no)
        TextView user_no;
        @BindView(R.id.tv_invite)
        TextView tv_invite;
        @BindView(R.id.userSelected)
        AppCompatRadioButton appCompatRadioButton;

        public UsersViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos < dataListFiltered.size()) {
                        itemClickListener.OnUserClick(dataListFiltered.get(pos), pos, userImage);
                    }
                }
            });
        }

        public void setData(User user, Contact contact, ArrayList<String> strings) {
//
//            userName.setText(strings.get(1));
//            user_no.setText(strings.get(0));
//            userName.setCompoundDrawablesWithIntrinsicBounds(0, 0, strings.get(2) == "true" ? R.drawable.ring_green : 0, 0);


            if (user != null) {
                userName.setText(TextUtils.isEmpty(user.getName()) ? user.getName() : user.getNameInPhone());
                user_no.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                String profileImageUrl = user.getImage();
                Glide.with(context).load(profileImageUrl).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);

                userName.setCompoundDrawablesWithIntrinsicBounds(0, 0, user.isOnline() ? R.drawable.ring_green : 0, 0);
            } else {
                if (contact != null) {
                    user_no.setVisibility(View.VISIBLE);
                    tv_invite.setVisibility(View.VISIBLE);
                    userName.setText(contact.getPhoneNumber());
                    user_no.setText(contact.getName());
                    appCompatRadioButton.setVisibility(View.GONE);
                    Glide.with(context).load(R.drawable.yoohoo_placeholder).into(userImage);
                    userName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_white, 0);
                    tv_invite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "YooHoo invitation");
                                shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(context.getResources().getString(R.string.invitation_text), context.getPackageName()));
                                context.startActivity(Intent.createChooser(shareIntent, "Share using.."));
                            } catch (Exception ignored) {

                            }
                        }
                    });
                }

            }
        }
    }
}


