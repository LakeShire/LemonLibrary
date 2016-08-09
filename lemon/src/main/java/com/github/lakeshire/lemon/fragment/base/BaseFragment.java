package com.github.lakeshire.lemon.fragment.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.lakeshire.lemon.R;
import com.github.lakeshire.lemon.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakeshire on 2016/4/19.
 */
public abstract class BaseFragment extends Fragment {

    protected ViewGroup mContainerView;
    private List<ImageView> mImageViews = new ArrayList<>();
    private List<Bitmap> mBitmaps = new ArrayList<>();
    private View mLoadingLayout;
    private View mNetworkErrorLayout;
    private View mNoContentLayout;

    public void startFragment(Class<?> clazz) {
        ((BaseActivity) getActivity()).startFragment(clazz, null);
    }

    public void startFragment(Class<?> clazz, Bundle bundle) {
        ((BaseActivity) getActivity()).startFragment(clazz, bundle);
    }

    public void endFragment() {
        ((BaseActivity) getActivity()).endFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainerView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        initUi();
        return mContainerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    public void loadData() {

    }

    public void initUi() {
        mLoadingLayout = find(R.id.layout_loading);
        mNetworkErrorLayout = find(R.id.layout_network_error);
        mNoContentLayout = find(R.id.layout_no_content);
        hideAllLayout();
    }

    protected void l(String log) {
        Log.i(this.getClass().getSimpleName(), log);
    }

    public abstract int getLayoutId();

    protected View find(int res) {
        View view = mContainerView.findViewById(res);
        if (view instanceof ImageView) {
            mImageViews.add((ImageView) view);
        }
        return view;
    }

    public boolean onBackPressed() {
        endFragment();
        return true;
    }

    @Override
    public void onDestroy() {
        for (ImageView iv : mImageViews) {
            iv.setImageBitmap(null);
        }
        super.onDestroy();
    }

    public void showLoadingLayout() {
        if (mLoadingLayout != null) {
            mLoadingLayout.setVisibility(View.VISIBLE);
        }
        if (mNetworkErrorLayout != null) {
            mNetworkErrorLayout.setVisibility(View.GONE);
        }
        if (mNoContentLayout != null) {
            mNoContentLayout.setVisibility(View.GONE);
        }
    }

    public void showNetworkErrorLayout() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mNetworkErrorLayout != null) {
                        mNetworkErrorLayout.setVisibility(View.VISIBLE);
                    }
                    if (mLoadingLayout != null) {
                        mLoadingLayout.setVisibility(View.GONE);
                    }
                    if (mNoContentLayout != null) {
                        mNoContentLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public void showNoContentLayout() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mNoContentLayout != null) {
                        mNoContentLayout.setVisibility(View.VISIBLE);
                    }
                    if (mNetworkErrorLayout != null) {
                        mNetworkErrorLayout.setVisibility(View.GONE);
                    }
                    if (mLoadingLayout != null) {
                        mLoadingLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public void hideAllLayout() {
        if (mNoContentLayout != null) {
            mNoContentLayout.setVisibility(View.GONE);
        }
        if (mNetworkErrorLayout != null) {
            mNetworkErrorLayout.setVisibility(View.GONE);
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.setVisibility(View.GONE);
        }
    }
}
