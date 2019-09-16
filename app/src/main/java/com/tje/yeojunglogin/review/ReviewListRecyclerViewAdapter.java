package com.tje.yeojunglogin.review;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;

import java.util.ArrayList;

public class ReviewListRecyclerViewAdapter extends RecyclerView.Adapter<ReviewListRecyclerViewAdapter.ViewHolder> {

    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;

    ArrayList<Review_view> list = new ArrayList<>();


    public ReviewListRecyclerViewAdapter(){

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_title;
        public TextView text_name;
        public TextView text_date;
        public ImageView review_star;
        public TextView text_count;
        public ImageView review_img;
        public TextView tv_articleId;

        public ViewHolder(View view) {
            super(view);
            text_title = view.findViewById(R.id.text_title);
            text_name = view.findViewById(R.id.text_name);
            text_date = view.findViewById(R.id.text_date);
            review_star = view.findViewById(R.id.review_star);
            text_count = view.findViewById(R.id.text_count);
            review_img = view.findViewById(R.id.review_img);

            tv_articleId = view.findViewById(R.id.tv_articleId);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(
            @NonNull final ViewHolder holder, final int position) {
        final Review_view review_view = this.list.get(position);
        holder.text_title.setText(review_view.getContent());
        holder.text_name.setText(review_view.getName());
        holder.text_date.setText(review_view.getWrite_time() + "");
       // holder.text_score.setText(String.valueOf(review_view.getReview_star()));
        if(review_view.getReview_star() == 1){
            holder.review_star.setImageResource(R.drawable.star_1);
        }else if(review_view.getReview_star() == 2){
            holder.review_star.setImageResource(R.drawable.star_2);
        }else if(review_view.getReview_star() == 3){
            holder.review_star.setImageResource(R.drawable.star_3);
        }else if(review_view.getReview_star() == 4){
            holder.review_star.setImageResource(R.drawable.star_4);
        }else if(review_view.getReview_star() == 5){
            holder.review_star.setImageResource(R.drawable.star_5);
        }else if(review_view.getReview_star() == 6){
            holder.review_star.setImageResource(R.drawable.star_6);
        }else if(review_view.getReview_star() == 7){
            holder.review_star.setImageResource(R.drawable.star_7);
        }else if(review_view.getReview_star() == 8){
            holder.review_star.setImageResource(R.drawable.star_8);
        }else if(review_view.getReview_star() == 9){
            holder.review_star.setImageResource(R.drawable.star_9);
        }else if(review_view.getReview_star() == 10){
            holder.review_star.setImageResource(R.drawable.star_10);
        }

         holder.text_count.setText(String.valueOf(review_view.getComment_count()));

//        holder.tv_articleId.setText(review_view.getArticle_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),DetailReviewActivity.class);
                intent.putExtra("article_id",review_view.getArticle_id());
                view.getContext().startActivity(intent);

            }
        });

        //if(holder.review_img == null){
           // holder.review_img.setImageDrawable(Drawable.createFromPath(String.valueOf(R.drawable.notimg)));
       // }

        String imageUrl = HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME +
                "/resources/cityimg/" + review_view.getImage_src();
        Glide.with(holder.itemView).load(imageUrl).into(holder.review_img);

    }

    @Override
    public int getItemCount() {
        if (this.list == null) {
            return 0;
        } else {
            return this.list.size();
        }
    }



    public void loadItems(ArrayList<Review_view> list) {
        this.list = list;
    }

    public Review_view getItem(int position) {
        return list.get(position);
    }


}











