package com.tje.yeojunglogin.with_me;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.chatting.ChattingActivity;
import com.tje.yeojunglogin.model.Withme_view;

import java.util.ArrayList;

public class Withme_adapter extends RecyclerView.Adapter<Withme_adapter.ViewHolder>{

    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;



    private ArrayList<Withme_view> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_withme_city;
        TextView tv_withme_name;
        TextView tv_withme_country;
        TextView tv_withme_age;
        TextView tv_withme_gender;
        TextView tv_withme_style;
        LinearLayout withme_item;
        ViewHolder(View itemView){
            super(itemView);

            tv_withme_city = itemView.findViewById(R.id.tv_withme_city);
            tv_withme_name = itemView.findViewById(R.id.tv_withme_name);
            tv_withme_country = itemView.findViewById(R.id.tv_withme_country);
            tv_withme_age = itemView.findViewById(R.id.tv_withme_age);
            tv_withme_gender = itemView.findViewById(R.id.tv_withme_gender);
            tv_withme_style = itemView.findViewById(R.id.tv_withme_style);
            withme_item = itemView.findViewById(R.id.withme_item);
        }
    }

    public Withme_adapter(ArrayList<Withme_view> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.withme_item_view, parent, false);
        Withme_adapter.ViewHolder vh = new Withme_adapter.ViewHolder(view);

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Withme_view withme_view = mData.get(position);
        holder.tv_withme_name.setText(withme_view.getMember_id());
        holder.tv_withme_city.setText(withme_view.getCity());
        holder.tv_withme_country.setText(withme_view.getCountry());

        if( withme_view.getCategory_gender() == 1 ) {
            holder.tv_withme_gender.setText("여자");
        } else if ( withme_view.getCategory_gender() == 2 ) {
            holder.tv_withme_gender.setText("남자");
        } else {
            holder.tv_withme_gender.setText("무관");
        }

        if( withme_view.getCategory_age() == 1 ) {
            holder.tv_withme_age.setText("20대");
        } else if ( withme_view.getCategory_age() == 2 ) {
            holder.tv_withme_age.setText("30대");
        } else if ( withme_view.getCategory_age() == 3 ) {
            holder.tv_withme_age.setText("40대");
        } else {
            holder.tv_withme_age.setText("무관");
        }

        if( withme_view.getCategory_style() == 1 ) {
            holder.tv_withme_style.setText("관광");
        } else if ( withme_view.getCategory_style() == 2 ) {
            holder.tv_withme_style.setText("맛집");
        } else if ( withme_view.getCategory_style() == 3 ) {
            holder.tv_withme_style.setText("쇼핑");
        } else if ( withme_view.getCategory_style() == 4 ) {
            holder.tv_withme_style.setText("휴양");
        } else if ( withme_view.getCategory_style() == 5 ) {
            holder.tv_withme_style.setText("액티비티");
        } else {
            holder.tv_withme_style.setText("무관");
        }

//        tv_withme_country = itemView.findViewById(R.id.tv_withme_country);
//        tv_withme_age = itemView.findViewById(R.id.tv_withme_age);
//        tv_withme_gender = itemView.findViewById(R.id.tv_withme_gender);
//        tv_withme_style = itemView.findViewById(R.id.tv_withme_style);

        final ViewHolder tmpHolder = holder;


        holder.withme_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(view.getContext(), ChattingActivity.class);
                intent.putExtra("member_id",withme_view.getMember_id());

                view.getContext().startActivity(intent);
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
