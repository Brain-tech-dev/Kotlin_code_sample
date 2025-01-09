package com.adambulette.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.adambulette.R;


public class ExpandableTextView extends AppCompatTextView {


    boolean isCollapsed = true;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);

        typedArray.recycle();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCollapsed) {
                    expand(v);
                } else {
                   // collapse(v);
                }

            }
        });
    }


    public void collapse(final View v) {
        isCollapsed = true;

        ((TextView) v).setSingleLine(true);
        ((TextView) v).setEllipsize(TextUtils.TruncateAt.END);
        int n = 1; // the exact number of lines you want to display
        ((TextView) v).setMaxLines(n);

    }

    public void expand(final View v) {
        isCollapsed = false;

        ((TextView) v).setSingleLine(false);
        ((TextView) v).setEllipsize(TextUtils.TruncateAt.END);
        int n = 5; // the exact number of lines you want to display
        ((TextView) v).setMaxLines(n);


    }

    public boolean getIsCollapsed() {
        return isCollapsed;
    }

}