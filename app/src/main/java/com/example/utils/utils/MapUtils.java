package com.example.utils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;


import java.util.List;

public class MapUtils {
    /**
     * 判断手机中是否安装指定包名的软件
     *
     * @param context
     * @param pkgname 包名
     */
    public static boolean isInstallApk(Context context, String pkgname) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(pkgname)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

//    /**
//     * gd_lat 纬度
//     * gd_lon 经度
//     * GCJ-02转换BD-09
//     * 高德地图经纬度转百度地图经纬度
//     */
//    public static LatLonPoint gd_bd_encrypt(double gd_lat, double gd_lon) {
//        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
//        double x = gd_lon;
//        double y = gd_lat;
//        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
//        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
//        double bd_lon = z * Math.cos(theta) + 0.0065;
//        double bd_lat = z * Math.sin(theta) + 0.006;
//        return new LatLonPoint(bd_lat, bd_lon);
//    }
}
