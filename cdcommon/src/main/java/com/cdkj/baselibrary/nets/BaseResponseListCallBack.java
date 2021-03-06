package com.cdkj.baselibrary.nets;

import android.content.Context;

import com.cdkj.baselibrary.CdApplication;
import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.utils.LogUtil;

import java.lang.ref.SoftReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cdkj.baselibrary.nets.NetHelper.NETERRORCODE4;
import static com.cdkj.baselibrary.nets.NetHelper.REQUESTFECODE4;
import static com.cdkj.baselibrary.nets.NetHelper.REQUESTOK;
import static com.cdkj.baselibrary.nets.NetHelper.getThrowableStateCode;
import static com.cdkj.baselibrary.nets.NetHelper.getThrowableStateString;


/**
 * 网络请求回调
 * Created by Administrator on 2016/9/3.
 */
public abstract class BaseResponseListCallBack<T> implements Callback<BaseResponseListModel<T>> {

    private Context context;

    public BaseResponseListCallBack(Context context) {
        SoftReference<Context> mS = new SoftReference<>(context);
        this.context = mS.get();
    }

    @Override
    public void onResponse(Call<BaseResponseListModel<T>> call, Response<BaseResponseListModel<T>> response) {

        onFinish();

        if (response == null || response.body() == null) {
            onNull();
            return;
        }

        if (response.isSuccessful()) {

            try {
                BaseResponseListModel t = response.body();
                checkState(t);      //根据返回错误的状态码实现相应的操作
            } catch (Exception e) {
                if (LogUtil.isDeBug) {
                    onReqFailure(NETERRORCODE4, "未知错误" + e.toString());
                } else {
                    onReqFailure(NETERRORCODE4, "程序出现未知错误");
                }
            }

        } else {
            onReqFailure(NETERRORCODE4, "网络请求失败");
        }

    }

    @Override
    public void onFailure(Call<BaseResponseListModel<T>> call, Throwable t) {

        if (call.isCanceled()) {                //如果是主动请求取消的就不执行
            return;
        }
        onFinish();
        if (!NetUtils.isNetworkConnected(CdApplication.getContext())) {
            onNoNet("暂无网络");
            return;
        }

        onReqFailure(getThrowableStateCode(t), getThrowableStateString(t));

    }

    /**
     * 检查错误码
     *
     * @param baseModelNew 根据返回错误的状态码实现相应的操作
     */
    protected void checkState(BaseResponseListModel baseModelNew) {

        String state = baseModelNew.getErrorCode();

        if (REQUESTOK.equals(state)) { //请求成功

            List<T> t = (List<T>) baseModelNew.getData();

            if (t == null) {
                onFinish();
                onNull();
                return;
            }

            onSuccess(t, baseModelNew.getErrorInfo());

        } else if (REQUESTFECODE4.equals(state)) {
            onLoginFailure(context, baseModelNew.getErrorInfo());
        } else {
            onReqFailure(state, baseModelNew.getErrorInfo());
        }
    }


    /**
     * 请求成功
     *
     * @param data
     */
    protected abstract void onSuccess(List<T> data, String SucMessage);

    /**
     * 请求失败
     *
     * @param errorCode
     * @param errorMessage
     */
    protected void onReqFailure(String errorCode, String errorMessage) {
        NetHelper.onReqFailure(context, errorCode, errorMessage);
    }

    /**
     * 重新登录
     *
     * @param
     */
    protected void onLoginFailure(Context context, String errorMessage) {
        NetHelper.onLoginFailure(context, errorMessage);
    }

    /**
     * 请求数据为空
     */
    protected void onNull() {
        NetHelper.onNull(context);
    }

    /**
     * 请求结束 无论请求成功或者失败都会被调用
     */
    protected abstract void onFinish();

    /**
     * 无网络
     */
    protected void onNoNet(String msg) {
        NetHelper.onNoNet(context, msg);
    }

}
