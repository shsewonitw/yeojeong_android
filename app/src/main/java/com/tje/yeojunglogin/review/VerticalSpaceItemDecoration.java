package com.tje.yeojunglogin.review;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int divTop;
    private final int divBotton;


    public VerticalSpaceItemDecoration(int divTop, int divBotton) {
        this.divTop = divTop;
        this.divBotton = divBotton;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // 마지막 아이템이 아닌 경우, 공백 추가
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)

            outRect.top = divTop;
            outRect.bottom = divBotton;

    }

}

