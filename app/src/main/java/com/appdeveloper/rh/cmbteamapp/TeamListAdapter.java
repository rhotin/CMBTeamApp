package com.appdeveloper.rh.cmbteamapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Roman on 12/26/2017.
 */

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;

        ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rowName);
            photo = (ImageView) itemView.findViewById(R.id.rowImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    private ArrayList<TeamObject> mTeamObjects;
    private Context mContext;

    TeamListAdapter(Context context, ArrayList<TeamObject> gifObjects) {
        mTeamObjects = gifObjects;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View listView = inflater.inflate(R.layout.list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeamObject teamObject = mTeamObjects.get(position);

        TextView textView = holder.name;
        textView.setText(String.format("%s %s", teamObject.firstName, teamObject.lastName));
        ImageView imageView = holder.photo;
        Glide.with(getContext()).load(teamObject.avatar).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mTeamObjects.size();
    }
}
