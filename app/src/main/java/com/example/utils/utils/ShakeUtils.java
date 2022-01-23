package com.example.utils.utils;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

import com.example.utils.activity.App;

/**
 * Created by wc on 2021/10/28
 * function:摇一摇监听
 * other:
 */
public class ShakeUtils implements SensorEventListener {

    private static final int SENSOR_VALUE = 12;//敏捷度
    private static final long SENSOR_TIME = 1000;//1000毫秒不能摇两次
    private SensorManager mSensorManager = null;
    private OnShakeListener mOnShakeListener = null;

    public interface OnShakeListener {
        public void onShake();
    }

    public ShakeUtils(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void setOnShakeListener(OnShakeListener onShakeListener) {
        mOnShakeListener = onShakeListener;
    }

    public void onResume() {
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private long isShake;
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (System.currentTimeMillis()-isShake<SENSOR_TIME){
            return;
        }
        isShake=System.currentTimeMillis();

        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            //这里可以调节摇一摇的灵敏度
            if ((Math.abs(values[0]) > SENSOR_VALUE || Math.abs(values[1]) > SENSOR_VALUE || Math.abs(values[2]) > SENSOR_VALUE)) {
                System.out.println("onSensorChanged=====>" + " X:" + values[0] + " Y:" + values[1] + " Z:" + values[2]);
                if (null != mOnShakeListener) {
                    mOnShakeListener.onShake();
                }
            }
        }
    }

    /**
     * 开启震动
     * @param milliseconds
     */
    public void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator) App.Companion.instance().getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }
} 
