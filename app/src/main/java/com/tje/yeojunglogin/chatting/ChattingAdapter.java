package com.tje.yeojunglogin.chatting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.model.City_Data;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ViewHolder>{

    private SharedPreferences appData;

    private ArrayList<String> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_chatting;
        LinearLayout chatting_item;
        ViewHolder(View itemView){
            super(itemView);

            tv_chatting = itemView.findViewById(R.id.tv_chatting);
            chatting_item = itemView.findViewById(R.id.chatting_item);

        }
    }

    public ChattingAdapter(ArrayList<String> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        appData = Preferences.getSp(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.chatting_item_view, parent, false);
        ChattingAdapter.ViewHolder vh = new ChattingAdapter.ViewHolder(view);

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String chatting = mData.get(position);
        String login_id = appData.getString("ID","");
        String target_id = "";
        StringTokenizer st = new StringTokenizer(chatting,"|");
        while(st.hasMoreTokens()){
            String temp = st.nextToken();
            if( !temp.equals(login_id) ) {
                target_id = temp;
            }
        }



        holder.tv_chatting.setText(target_id+ "님 과의 대화");

        final String finalTarget_id = target_id;
        holder.chatting_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("클릭!");
                Intent intent = new Intent(view.getContext(), ChattingActivity.class);
                intent.putExtra("member_id", finalTarget_id);

                view.getContext().startActivity(intent);
            }
        });

        final ViewHolder tmpHolder = holder;











    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
