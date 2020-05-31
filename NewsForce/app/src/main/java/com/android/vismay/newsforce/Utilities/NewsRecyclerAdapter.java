package com.android.vismay.newsforce.Utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.vismay.newsforce.R;
import com.bumptech.glide.Glide;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.viewHolder> {

    NewsModel model;
    Context context;

    public NewsRecyclerAdapter(NewsModel model, Context context) {
        this.model = model;
        this.context = context;
    }

    public NewsRecyclerAdapter() {
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int i) {
        if (model.getArticles().get(i).getContent() != null) {
            holder.mTitle.setText(model.getArticles().get(i).getTitle());
            holder.mSource.setText(model.getArticles().get(i).getSource().getName());
            Glide.with(context).load(model.getArticles().get(i).getUrlToImage()).into(holder.mImg);
            String timedate = format(model.getArticles().get(i).getPublishedAt());
            holder.mDatetime.setText(timedate);
            holder.cardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url=model.getArticles().get(i).getUrl();
                    Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return model.getArticles().size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView mImg;
        TextView mTitle,mSource,mDatetime;
        CardView cardButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardButton=itemView.findViewById(R.id.cardview_button);
            mImg=itemView.findViewById(R.id.news_id_image);
            mTitle=itemView.findViewById(R.id.news_headline_id_text);
            mSource=itemView.findViewById(R.id.source_news_id);
            mDatetime=itemView.findViewById(R.id.date_time_news_id);
        }
    }

    public String format(String date){
        //2019-06-25T15:55:22Z
        date=date.replace("T"," ");
        date=date.substring(0,16);
        return date;
    }
}
