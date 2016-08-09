package com.github.lakeshire.lemon.adapter.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    protected final SparseArray<View> mItemViewsArray;
    protected View mConvertView;
    protected Context mContext;

    public ViewHolder(int resId, Context context) {
        mContext = context;
        mItemViewsArray = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(resId, null);
        mConvertView.setTag(this);
    }

    public static ViewHolder getView(View convertView, int layoutId, Context context) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(layoutId, context);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return viewHolder;
    }

    public void setVisibility(int resId, int visibleType) {
        if (resId > 0) {
            getItemView(resId).setVisibility(visibleType);
        }
    }

    public void setTextColor(int resId, int res) {
        if (resId > 0 && res > 0) {
            ((TextView) getItemView(resId)).setTextColor(mContext.getResources().getColor(res));
        }
    }

    public void setBgColor(int resId, int res) {
        if (resId > 0 && res > 0) {
            getItemView(resId).setBackgroundColor(res);
        }
    }

    public void setBgDrawable(int resId, int res) {
        if (resId > 0 && res > 0) {
            getItemView(resId).setBackgroundResource(res);
        }
    }

    public void setVisibility(int resId, boolean visible) {
        if (resId > 0) {
            if (visible) {
                getItemView(resId).setVisibility(View.VISIBLE);
            } else {
                getItemView(resId).setVisibility(View.GONE);
            }

        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getItemView(int resId) {
        View view = mItemViewsArray.get(resId);
        if (view == null) {
            view = mConvertView.findViewById(resId);
            if (view != null) {
                mItemViewsArray.put(resId, view);
            }
        }
        return (T) view;
    }

    public void setText(int resId, String content) {
        if (resId > 0 && !TextUtils.isEmpty(content)) {
            ((TextView) getItemView(resId)).setText(content);
        }
    }

    public void setImageResource(int resId, String url) {
        setImageResource(resId, url, -1);
    }

    public void setImageResource(int resId, String url, int defaultResId) {
        if (resId > 0 && !TextUtils.isEmpty(url)) {
            //  TODO:
        }
    }

    public void setImageResource(int resId, int res) {
        if (resId > 0 && res > 0) {
            ((ImageView) getItemView(resId)).setImageResource(res);
        }
    }

    public View getConvertView() {
        return this.mConvertView;
    }
}
