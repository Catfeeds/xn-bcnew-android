package com.cdkj.link_community.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.link_community.R;
import com.cdkj.link_community.databinding.DialogCommentInputBinding;

/**
 * Created by 李先俊 on 2018/3/20.
 */

public class CommentInputDialog extends Dialog {

    private DialogCommentInputBinding mBinding;

    public CommentInputDialog(@NonNull Context context) {
        super(context,R.style.comment_input_dialog);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_comment_input, null, false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mBinding.getRoot());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int screenWidth = DisplayHelper.getScreenWidth(getContext());
        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.width = (int) (screenWidth); //设置宽度
        getWindow().setAttributes(lp);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        getWindow().setGravity(Gravity.BOTTOM);

    }


}
