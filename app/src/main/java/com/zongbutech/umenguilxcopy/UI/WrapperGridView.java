package com.zongbutech.umenguilxcopy.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by lixian on 2016/4/20.
 */

public class WrapperGridView extends GridView {
    public boolean hasScrollBar;

    public WrapperGridView(Context context) {
        this(context, (AttributeSet)null);
    }

    public WrapperGridView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.hasScrollBar = true;
    }

    public WrapperGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.hasScrollBar = true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(this.hasScrollBar) {
            int expandSpec = MeasureSpec.makeMeasureSpec(536870911, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public void updateColumns(int maxColumn) {
        int childCount = this.getAdapter().getCount();
        if(childCount > 0) {
            int columns = childCount < maxColumn?childCount % maxColumn:maxColumn;
            this.setNumColumns(columns);
        }

        if(childCount == 1) {
            this.setLayoutParams(new LayoutParams(480/ 3,640 / 3));
        } else {
            this.setLayoutParams(new LayoutParams(-1, -1));
        }

    }
}
