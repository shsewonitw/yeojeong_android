package com.tje.yeojunglogin.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tje.yeojunglogin.R;

public class FragReview extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        System.out.println("Frag Review onCreate View 호출!!!");


        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.reviewlist, container, false);

        return layout;
    }
}
