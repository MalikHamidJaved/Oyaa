package com.verbosetech.yoohoo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
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
import com.verbosetech.yoohoo.models.Group;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;



public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private OnUserGroupItemClick itemClickListener;
    private ArrayList<Group> dataList, dataListFiltered;
    private Filter filter;

    public GroupsAdapter(@NonNull Context context, @Nullable ArrayList<Group> groups) {
        if (context instanceof OnUserGroupItemClick) {
            this.itemClickListener = (OnUserGroupItemClick) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnUserGroupItemClick");
        }

        this.context = context;
        this.dataList = groups;
        this.dataListFiltered = groups;
        this.filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    ArrayList<Group> filteredList = new ArrayList<>();
                    for (Group row : dataList) {
                        if (row.getName().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<Group>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public GroupsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_user, parent, false));
    }

    @Override
    public void onBindViewHolder(GroupsAdapter.MyViewHolder holder, int position) {
        holder.setData(dataListFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        CircleImageView userImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    itemClickListener.OnGroupClick(dataListFiltered.get(pos), pos, userImage);
                }
            });
        }

        public void setData(Group group) {
            Glide.with(context).load(group.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);
            userName.setText(group.getName());
        }
    }
}
