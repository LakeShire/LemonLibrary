package com.github.lakeshire.lemon.fragment.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.lakeshire.lemon.R;

public abstract class DBaseFragment extends BaseFragment {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private ImageView mIvAction;
    protected View mTitleBar;

    @Override
    public void initUi() {
        super.initUi();

        mTitleBar = find(R.id.titlebar);
        mTvTitle = (TextView) find(R.id.tv_title);
        mIvBack = (ImageView) find(R.id.iv_back);
        mIvAction = (ImageView) find(R.id.iv_action);

        if (mIvBack != null) {
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }

        if (mIvAction != null) {
            mIvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doAction();
                }
            });
        }

        initUI();
    }

    protected abstract void initUI();

    public void doAction() {

    }

    public void showAction(int res) {
        mIvAction.setImageResource(res);
        mIvAction.setVisibility(View.VISIBLE);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void showBack(boolean show) {
        mIvBack.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
