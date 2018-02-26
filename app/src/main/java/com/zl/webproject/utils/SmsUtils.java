package com.zl.webproject.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

/**
 * Created by 张磊 on 2017/8/17.
 * 短信工具类
 */

public class SmsUtils {
    private static boolean isOk;

    /**
     * 发送短信
     *
     * @param phone
     */
    public static void sendSms(String phone) {
        SMSSDK.getInstance().getSmsCode(phone, "1", new SmscodeListener() {
            @Override
            public void getCodeSuccess(final String uuid) {
                // 获取验证码成功，uuid 为此次获取的唯一标识码。

            }

            @Override
            public void getCodeFail(int errCode, final String errMsg) {
                // 获取验证码失败 errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。

            }
        });
    }

    public static void sendCode(final Context context, String phone, final TextView textView) {
        textView.setEnabled(false);
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                textView.setText(l / 1000 + "s后可重发");
            }

            @Override
            public void onFinish() {
                textView.setText("重新发送");
                textView.setEnabled(true);
            }
        }.start();

        SMSSDK.getInstance().getSmsCode(phone, "1", new SmscodeListener() {
            @Override
            public void getCodeSuccess(final String uuid) {
                // 获取验证码成功，uuid 为此次获取的唯一标识码。
                Toast.makeText(context, "验证码发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getCodeFail(int errCode, final String errMsg) {
                // 获取验证码失败 errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。
                Toast.makeText(context, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean checkSmsCode(final Context context, final String phone, final String code) {

        SmsUtils.checkSmsCode(phone, code, new SmscheckListener() {
            @Override
            public void checkCodeSuccess(final String code) {
                isOk = true;
            }

            @Override
            public void checkCodeFail(int errCode, final String errMsg) {
                isOk = false;
                // 验证码验证失败, errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。
                final String errData;
                switch (errCode) {
                    case 4015:
                        errData = "验证码不正确";
                        break;
                    case 4016:
                        errData = "没有余额";
                        break;
                    case 4017:
                        errData = "验证码超时";
                        break;
                    case 4018:
                        errData = "重复验证";
                        break;
                    case 2997:
                        errData = "未获取验证码";
                        break;
                    default:
                        errData = "验证失败";
                        break;
                }
                Toast.makeText(context, errData, Toast.LENGTH_SHORT).show();
            }
        });

        return isOk;
    }

    /**
     * 发送短信
     *
     * @param phone
     */
    public static void sendSms(String phone, SmscodeListener listener) {
        SMSSDK.getInstance().getSmsCode(phone, "1", listener);
    }

    /**
     * 进行短信验证
     *
     * @param phone
     * @param code
     */
    private static void checkSmsCode(String phone, String code, SmscheckListener listener) {
        SMSSDK.getInstance().checkSmsCodeAsyn(phone, code, listener);
    }

}
