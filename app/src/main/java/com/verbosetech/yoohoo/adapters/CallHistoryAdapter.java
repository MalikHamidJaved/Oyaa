package com.verbosetech.yoohoo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.interfaces.OnCallHistoryItemCleckListener;
import com.verbosetech.yoohoo.models.Call;
import com.verbosetech.yoohoo.viewHolders.CallHistoryItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryItemViewHolder> {

    private List<Call> checklists;
    Context context;
    private WeakReference<OnCallHistoryItemCleckListener> checklistItemCleckListenerWeakReference;
    private WeakReference<TextView> tvNoRecords;

    public CallHistoryAdapter( OnCallHistoryItemCleckListener onChecklistItemCleckListener) {

        this.checklistItemCleckListenerWeakReference = new WeakReference<>(onChecklistItemCleckListener);

        checklists = new ArrayList<>();
    }



    public void setChecklists(List<Call> list) {

        this.checklists = list;
        notifyDataSetChanged();


//        if (list!= null) {
//            if (list.size() == 0) {
//                if (tvNoRecords != null)
//                    tvNoRecords.get().setVisibility(View.VISIBLE);
//            } else {
//                if (tvNoRecords != null)
//                    tvNoRecords.get().setVisibility(View.GONE);
//            }
//        }

    }


    @Override
    public CallHistoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_call_log, parent, false);

        OnCallHistoryItemCleckListener listener = null;
        if (checklistItemCleckListenerWeakReference != null && checklistItemCleckListenerWeakReference.get() != null) {
            listener = checklistItemCleckListenerWeakReference.get();
        }

        return new CallHistoryItemViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(CallHistoryItemViewHolder holder, int position) {
        Call checklist = checklists.get(position);
        holder.setData(checklist,position);
    }

    public Call getItem(int position) {
        if (position < 0 || position >= checklists.size()) {
            return null;
        }
        return checklists.get(position);
    }

    @Override
    public int getItemCount() {
        return checklists.size();
    }
}