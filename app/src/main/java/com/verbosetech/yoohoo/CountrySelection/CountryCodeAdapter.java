package com.verbosetech.yoohoo.CountrySelection;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.utils.Constant;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 19-01-2018.
 */

public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CountryCodeViewHolder> {

    private List<Country> countryList;
    Activity context;
    private ArrayList<Country> univeruserDataList;
    String index;
    RelativeLayout relativeLayout;

    public CountryCodeAdapter(List<Country> countryList, Activity context, String index) {
        this.countryList = countryList;
        this.context = context;
        univeruserDataList = new ArrayList<>();
        univeruserDataList.addAll(countryList);
        this.index=index;
        this.relativeLayout = relativeLayout;
    }

    public void filter(String charText) {

        univeruserDataList.clear();

        if (charText.length() == 0) {
            univeruserDataList.addAll(countryList);
        } else {
            for (Country wp : countryList) {
                if (wp.getName().toLowerCase().startsWith(charText) || wp.getPhoneCode().toLowerCase().startsWith(charText)) {
                    univeruserDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public CountryCodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new CountryCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryCodeViewHolder holder, int position) {
        Country country = univeruserDataList.get(position);
        holder.textView_name.setText(country.getName() +  " " + "(+" + country.getPhoneCode() + ")" );
//        holder.textView_code.setText(country.getPhoneCode());
        holder.imageViewFlag.setImageResource(country.getFlagID());

    }

    @Override
    public int getItemCount() {
        return univeruserDataList.size();
    }

    class CountryCodeViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name, textView_code;
        ImageView imageViewFlag;
        LinearLayout linearFlagHolder;
        View divider;

        public CountryCodeViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.textView_countryName);
//            textView_code = (TextView) itemView.findViewById(R.id.textView_code);
            imageViewFlag = (ImageView) itemView.findViewById(R.id.image_flag);
            linearFlagHolder = (LinearLayout) itemView.findViewById(R.id.linear_flag_holder);
            divider = itemView.findViewById(R.id.preferenceDivider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra(Constant.COUNTRY_NAME,  univeruserDataList.get(getAdapterPosition()).getName());
                    intent.putExtra(Constant.COUNTRY_CODE,  univeruserDataList.get(getAdapterPosition()).getPhoneCode());
                    intent.putExtra(Constant.NAME_CODE,  univeruserDataList.get(getAdapterPosition()).getNameCode());
                    intent.putExtra(Constant.FLAG_IMG,  univeruserDataList.get(getAdapterPosition()).getFlagID());
                    intent.putExtra(Constant.INDEX,index);
                    context.setResult(Activity.RESULT_OK, intent);
                    context.finish();
                }
            });

        }
    }
}
