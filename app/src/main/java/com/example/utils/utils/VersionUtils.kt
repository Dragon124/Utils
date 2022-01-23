package com.example.utils.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

class VersionUtils {
    companion object {

        /**
         * 获取当前apk的版本号
         *
         * @param mContext
         * @return
         */
        fun getVersionCode(mContext : Context) : Int {
            var versionCode = 0
            try {
                //获取软件版本号，对应AndroidManifest.xml下android:versionCode
                versionCode = mContext.packageManager.getPackageInfo(mContext.packageName, 0).versionCode
            } catch (e : PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return versionCode
        }

        /**
         * 获取当前apk的版本名
         *
         * @param context 上下文
         * @return
         */
        fun getVersionName(context : Context) : String? {
            var versionName : String? = ""
            try {
                //获取软件版本号，对应AndroidManifest.xml下android:versionName
                versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e : PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return versionName
        }

        /**
         * 安装Apk
         * @param context Context
         * @param downloadApkPath String APK路径
         */
        fun installApk(context : Context, downloadApkPath : String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val file = File(downloadApkPath)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //如果SDK版本大于24
                    val packageName = context.applicationContext.packageName
                    val authority = StringBuilder(packageName).append(".provider").toString()
                    val uri = FileProvider.getUriForFile(context, authority, file)
                    intent.setDataAndType(uri, "application/vnd.android.package-archive")
                } else {
                    val uri = Uri.fromFile(file)
                    intent.setDataAndType(uri, "application/vnd.android.package-archive")
                }
                context.startActivity(intent)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }

        /**
         * 判断版本更新
         *
         * @param localVersion 本地app 版本号
         * @param newVersion   最新版本号
         * @return true 需要更新 false 不用
         */
        fun updateApp(localVersion : String, newVersion : String) : Boolean {
            var localVersion = localVersion
            var localVersionArray = localVersion.split(".").toTypedArray()
            val newVersionArray = newVersion.split(".").toTypedArray()
            if (localVersionArray.size < newVersionArray.size) {
                val cha = newVersionArray.size - localVersionArray.size
                for (i in 0 until cha) {
                    localVersion = "$localVersion.0"
                }
                localVersionArray = localVersion.split(".").toTypedArray()
            }
            try {
                for (i in newVersionArray.indices) {
                    val temp = newVersionArray[i].toInt()
                    val compar = localVersionArray[i].toInt()
                    if (temp > compar) {
                        return true
                    }
                }
            } catch (e : java.lang.Exception) {
                e.printStackTrace()
            }
            return false
        }
    }
}