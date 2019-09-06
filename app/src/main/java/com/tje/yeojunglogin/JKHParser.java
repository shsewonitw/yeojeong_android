package com.tje.yeojunglogin;

import com.tje.yeojunglogin.Review_view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JKHParser {

    public static final String KEY_ARTICLE_ID = "article_id";
    public static final String KEY_MEMBER_ID = "member_id";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_CITY = "city";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_IMAGE_SRC = "image_src";
    public static final String KEY_REVIEW_START = "review_star";
    public static final String KEY_WIRTHE_TIME = "write_time";
    public static final String KEY_READ_COUND = "read_count";
    public static final String KEY_NAME = "name";
    public static final String KEY_COMMONT_COUNT = "comment_count";

    public static ArrayList<Review_view> getReviews(String string) {
        ArrayList<Review_view> reviews = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(string);
            for (int i = 0; i < array.length(); i++) {

                JSONObject object = array.getJSONObject(i);
                int article_id = object.getInt(KEY_ARTICLE_ID);
                String member_id = object.getString(KEY_MEMBER_ID);
                String country = object.getString(KEY_COUNTRY);
                String city = object.getString(KEY_CITY);
                String content = object.getString(KEY_CONTENT);
                String image_src = object.getString(KEY_IMAGE_SRC);
                int review_star = object.getInt(KEY_REVIEW_START);
                String write_time = object.getString(KEY_WIRTHE_TIME);
                int read_count = object.getInt(KEY_READ_COUND);
                String name = object.getString(KEY_NAME);
                int comment_count = object.getInt(KEY_COMMONT_COUNT);

                Review_view review = new Review_view(
                        article_id,
                        member_id,
                        country,
                        city,
                        content,
                        image_src,
                        review_star,
                        null,
                        read_count,
                        name,
                        comment_count
                );
                reviews.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}