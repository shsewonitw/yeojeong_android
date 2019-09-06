package com.tje.yeojunglogin;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MypageRecyclerViewAdaptor extends RecyclerView.Adapter<MypageRecyclerViewAdaptor.ViewHolder> {

    private static final String HOST_NETWORK_PROTOCOL = "http://";
    private static final String HOST_ADDRESS = "192.168.0.51:8080";
    private static final String HOST__APP_NAME = "/yeojeong";

    public Mypage_recycle recycle;
    public MypageRecyclerViewAdaptor mypageRecyclerViewAdaptor;
    ArrayList<Review_view> list;

    public MypageRecyclerViewAdaptor(Mypage_recycle recycle) {
        this.recycle = recycle;
        loadItems();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_country;
        public TextView tv_city;
        public TextView tv_name;
        public TextView tv_write_time;
        public TextView tv_content;
        public TextView tv_review_star;
        public TextView tv_comment_count;
        public ImageView iv_src;

        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_write_time = (TextView) view.findViewById(R.id.tv_write_time);
            tv_country = (TextView) view.findViewById(R.id.tv_country);
            tv_city = (TextView) view.findViewById(R.id.tv_city);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_review_star = (TextView) view.findViewById(R.id.tv_review_star);
            tv_comment_count = (TextView) view.findViewById(R.id.tv_comment_count);
            iv_src = (ImageView) view.findViewById(R.id.iv_src);
        }
    }

    @NonNull
    @Override
    public MypageRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ViewHolder vh = new ViewHolder(view);


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MypageRecyclerViewAdaptor.ViewHolder holder, int position) {

        Review_view review_view = this.list.get(position);
        holder.tv_country.setText(review_view.getCountry());
        holder.tv_city.setText(review_view.getCity());
        holder.tv_content.setText(review_view.getContent());
        holder.tv_review_star.setText(review_view.getReview_star() + "");
        holder.tv_comment_count.setText(review_view.getComment_count() + "");
        holder.tv_name.setText(review_view.getName() + "");
        holder.tv_write_time.setText(review_view.getWrite_time() + "");
        String imageUrl = HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST__APP_NAME +
                "/resources/cityimg/" + review_view.getImage_src();
        Glide.with(holder.itemView).load(imageUrl).into(holder.iv_src);

//        holder.iv_src.setImageBitmap(review_view.getRegistDate());


    }

    @Override
    public int getItemCount() {
        if (this.list == null) {
            return 0;
        } else {
            return this.list.size();
        }
    }

    public void loadItems() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

//                int pageNO = 1;

                String targetURL = "/auth/mypage";

                try {
                    URL url = new URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST__APP_NAME + targetURL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                    String cookieString = CookieManager.getInstance().getCookie(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST__APP_NAME);

                    if (cookieString != null) {
                        connection.setRequestProperty("Cookie", cookieString);
                    } else {
                        return;
                    }

                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
//                            connection.setRequestProperty("Content-type", "application/json");
//                            connection.setRequestProperty("Accept", "application/json");

//                    OutputStream writer = connection.getOutputStream();


                    System.out.println(connection.getResponseCode());

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Map<String, List<String>> headerFields = connection.getHeaderFields();
                        String COOKIES_HEADER = "Set-Cookie";
                        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                        if (cookiesHeader != null) {
                            for (String cookie : cookiesHeader) {
                                String cookieName = HttpCookie.parse(cookie).get(0).getName();
                                String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                                cookieString = cookieName + "=" + cookieValue;
                                CookieManager.getInstance().setCookie(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST__APP_NAME, cookieString);
                            }
                        }

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(in);
                        JsonArray review_review = element.getAsJsonObject().get("rList").getAsJsonArray();

//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<List<Review_view>>() {
//                        }.getType();
//                        List<Review_view> a = gson.fromJson(review_review, listType);
//                        System.out.println(a.get(0).getArticle_id());

                        ArrayList<Review_view> reviews = JKHParser.getReviews(review_review.toString());

                        list = reviews;

                        // 날짜 대입
                        for (int i = 0; i < review_review.size(); i++) {
                            JsonElement o = review_review.get(i);
                            long write_time = o.getAsJsonObject().get("write_time").getAsLong();
                            Date date1 = new Date(write_time);
                            list.get(i).setWrite_time(date1);
                        }


//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<HashMap<String, Object>>() {
//                        }.getType();
//                        HashMap<String, Object> result = gson.fromJson(in, listType);
//                        JsonParser parser = new JsonParser();
//                        JsonElement element = parser.parse(result);
//                        String logout_msg = element.getAsJsonObject().get("logout_msg").getAsString();
//                        System.out.println(result.size());
//                        List<Review_view> review_viewList = (List<Review_view>) (result.get("rList"));
//                        list = (ArrayList<Review_view>)review_viewList;
//                        System.out.println(review_viewList.get(0).getArticle_id());
                        in.close();

//                    writer.close();


                    }
                    recycle.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MypageRecyclerViewAdaptor.this.notifyDataSetChanged();
                        }
                    });
                    reset();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


    }

    public Review_view getItem(int position) {
        return this.list.get(position);
    }

    public void reset() {


        if (getItemCount() == 0) {
            recycle.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recycle.tv_no_content.setVisibility(View.VISIBLE);
                    recycle.mypage_recycle.setVisibility(View.GONE);

                }
            });

        } else {
            recycle.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recycle.tv_no_content.setVisibility(View.GONE);
                    recycle.mypage_recycle.setVisibility(View.VISIBLE);

                }
            });

        }
    }
}
