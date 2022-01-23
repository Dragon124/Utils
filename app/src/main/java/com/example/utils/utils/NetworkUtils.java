package com.example.utils.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.utils.BuildConfig;


/**
 * Created by wc on 2019/1/11.
 * Function:
 * Desc:
 */
public class NetworkUtils {
    public static final String WIFI = "Wi-Fi";
    public static final String DEFAULT_WIFI_ADDRESS = "00-00-00-00-00-00";
    private static final String TAG = "NetworkUtils";
    private static ConnectivityManager sConnManager = null;
    private static final int[] WEAK_NETWORK_GROUP = new int[]{4, 7, 2, 1};

    private NetworkUtils() {
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connManager = getConnManager(context);
        if (connManager != null) {
            try {
                NetworkInfo e = connManager.getActiveNetworkInfo();
                if (e != null) {
                    return e.isConnected();
                }
            } catch (Exception var3) {
                Log.e("NetworkUtils", var3.toString());
            }
        } else {
            Log.e("NetworkUtils", "connManager is null!");
        }
        return false;
    }

    public static boolean isConnectedToWeakNetwork(Context context) {
        ConnectivityManager connManager = getConnManager(context);
        if (connManager != null) {
            try {
                NetworkInfo e = connManager.getActiveNetworkInfo();
                if (e != null) {
                    int subType = e.getSubtype();
                    if (BuildConfig.DEBUG) {
                        Log.d("NetworkUtils", "subType:" + subType + ": name:" + e.getSubtypeName());
                    }
                    int[] var7 = WEAK_NETWORK_GROUP;
                    int var6 = WEAK_NETWORK_GROUP.length;
                    for (int var5 = 0; var5 < var6; ++var5) {
                        int element = var7[var5];
                        if (element == subType) {
                            return true;
                        }
                    }
                } else {
                    Log.e("NetworkUtils", "networkInfo is null!");
                }
            } catch (Exception var8) {
                Log.e("NetworkUtils", var8.toString());
            }
        } else {
            Log.e("NetworkUtils", "connManager is null!");
        }
        return false;
    }

    public static ConnectivityManager getConnManager(Context context) {
        if (context == null) {
            Log.e("NetworkUtils", "context is null!");
            return null;
        } else {
            if (sConnManager == null) {
                sConnManager = (ConnectivityManager)
                        context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            }
            return sConnManager;
        }
    }

    /**
     * 获取网络状态
     *
     * @param paramContext
     * @return
     */
    public static String[] getNetworkState(Context paramContext) {
        String[] arrayOfString = new String[]{"Unknown", "Unknown"};

        try {
            PackageManager localPackageManager = paramContext.getPackageManager();
            if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) !=0 ) {
                arrayOfString[0] = "Unknown";
                return arrayOfString;
            }

            ConnectivityManager localConnectivityManager = (ConnectivityManager)
                    paramContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            if (localConnectivityManager == null) {
                arrayOfString[0] = "Unknown";
                return arrayOfString;
            }
            NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
            if (localNetworkInfo1 != null && localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
                arrayOfString[0] = "Wi-Fi";
                return arrayOfString;
            }
            NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
            if (localNetworkInfo2 != null && localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
                arrayOfString[0] = "2G/3G";
                arrayOfString[1] = localNetworkInfo2.getSubtypeName();
                return arrayOfString;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayOfString;
    }

    public static String getWifiAddress(Context context) {
        if (context != null) {
            WifiManager wm = (WifiManager)
                    context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiinfo = wm.getConnectionInfo();
            if (wifiinfo != null) {
                String address = wifiinfo.getMacAddress();
                if (TextUtils.isEmpty(address)) {
                    address = "00-00-00-00-00-00";
                }
                return address;
            } else {
                return "00-00-00-00-00-00";
            }
        } else {
            return "00-00-00-00-00-00";
        }
    }

    private static String _convertIntToIp(int i) {
        return (i & 255) + "." + (i >> 8 & 255) + "." + (i >> 16 & 255) + "." + (i >> 24 & 255);
    }

    public static String getWifiIpAddress(Context context) {
        if (context != null) {
            try {
                WifiManager wifiManage = (WifiManager)
                        context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiinfo = wifiManage.getConnectionInfo();
                if (wifiinfo != null) {
                    return _convertIntToIp(wifiinfo.getIpAddress());
                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isWifi(Context context) {
        if (context != null) {
            try {
                if (getNetworkState(context)[0].equals("Wi-Fi")) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
