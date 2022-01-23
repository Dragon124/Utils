package com.example.utils.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtil {

    private static PhoneUtil phoneUtil;

    private PhoneUtil(){

    }

    public static PhoneUtil getInstance(){
        if(phoneUtil==null){
            phoneUtil = new PhoneUtil();
        }
        return phoneUtil;
    }

    /**
     * 立即拨打电话
     * @param context
     * @param phoneNumber
     */
    public void startCallPhone(Context context, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 跳转到拨号页面
     * @param context
     * @param phoneNumber
     */
    public void toCallPhone(Context context, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        context.startActivity(intent);
    }
}
