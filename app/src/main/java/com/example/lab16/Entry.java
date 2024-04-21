package com.example.lab16;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Entry extends LinearLayout {

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public String name, age, gender, email, phone;
    public Entry(Context context, String[] params) {
        super(context);

        this.setOrientation(LinearLayout.VERTICAL);

        this.setBackground(getResources().getDrawable(R.drawable.entry_bg));

        Integer p = dpToPx(8);
        this.setPadding(p,p,p,p);

        for (String param : params) {
            TextView textView = new TextView(context);
            textView.setTextSize(24);
            textView.setText(param);
            this.addView(textView);
        }
    }
}
