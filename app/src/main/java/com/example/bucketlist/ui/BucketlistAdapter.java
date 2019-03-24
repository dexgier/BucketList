package com.example.bucketlist.ui;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.BucketlistItem;


import java.util.ArrayList;
import java.util.List;

public class BucketlistAdapter extends RecyclerView.Adapter<BucketlistAdapter.ViewHolder> {

    private List<BucketlistItem> bucketlistItemList;
    private LayoutInflater mInflater;
    private Context mContext;

    public BucketlistAdapter(Context mContext, ArrayList<BucketlistItem> bucketlistItemList) {
        this.bucketlistItemList = bucketlistItemList;
        this.mInflater = mInflater;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public BucketlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_inlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final BucketlistItem item = bucketlistItemList.get(position);
        holder.titleTextView.setText((item.getTitle()));
        holder.descriptionTextView.setText((item.getDescription()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    holder.titleTextView.setPaintFlags(holder.titleTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.descriptionTextView.setPaintFlags(holder.descriptionTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.titleTextView.setPaintFlags(holder.titleTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.descriptionTextView.setPaintFlags(holder.descriptionTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bucketlistItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        LinearLayout parentalLayout;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            parentalLayout = itemView.findViewById(R.id.parentLayout);
            parentalLayout.setClickable(true);
            checkBox = itemView.findViewById(R.id.bucketListCheck);
        }
    }
}



