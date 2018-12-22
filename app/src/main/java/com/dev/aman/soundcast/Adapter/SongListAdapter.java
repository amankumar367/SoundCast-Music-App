package com.dev.aman.soundcast.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.aman.soundcast.PlayMusicActivity;
import com.dev.aman.soundcast.R;
import com.dev.aman.soundcast.model.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongListAdapterViewHolder>  {

    private List<Results> arrayList;
    private Context context;

    public SongListAdapter(List<Results> arrayList, Context ctxt) {
        this.arrayList = arrayList;
        this.context = ctxt;
    }

    @NonNull
    @Override
    public SongListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_song_list, parent, false);
        return new SongListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongListAdapterViewHolder holder, final int position) {

        holder.title.setText(arrayList.get(position).getTitle());
        holder.created.setText(arrayList.get(position).getCreatedAt());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("mylist", (ArrayList<? extends Parcelable>) arrayList);
                intent.putExtras(bundle);
                intent.putExtra("POSITION",position);
                context.startActivity(intent);
            }
        });
        Picasso.get()
                .load(arrayList.get(position).getThumbnail())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SongListAdapterViewHolder extends RecyclerView.ViewHolder{

        View view;
        TextView title, created;
        ImageView thumbnail;
        public SongListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            title = view.findViewById(R.id.title);
            created = view.findViewById(R.id.created);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }
}
