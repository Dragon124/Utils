package com.example.utils.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class FileUtils {
    companion object {
        //保存BitMap图片到本地文件
        fun saveBitmap(context : Context, bitmap : Bitmap?) {
            if (bitmap == null) {
                Log.e("saveImage", "图片为空")
                return
            }
            //获取需要存储到本地的路径
            val file : File = File(getSaveImagePath(context))
            //如果文件夹不存在 就创建文件夹
            if (!file.exists()) {
                file.mkdirs()
            }
            val path = File(file, System.currentTimeMillis().toString() + ".png")
            try {
                val fileOutputStream = FileOutputStream(path)
                // compress - 压缩的意思  将bitmap保存到本地文件中
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream)
                //存储完成后需要清除相关的进程
                fileOutputStream.flush()
                fileOutputStream.close()
                //图库不更新APP路径图片
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    //把图片插入系统图库
                    insertImage(path, context)
                } else {
                    //保存图片后发送广播通知更新数据库
                    val uri = Uri.fromFile(path)
                    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                    intent.data = uri
                    context.sendBroadcast(intent)
                }
                Log.e("saveImage", "保存图片成功")
            } catch (e : FileNotFoundException) {
                Log.e("saveImage", "error:$e")
            } catch (e : IOException) {
                Log.e("saveImage", "error:$e")
            }
            bitmap.recycle()
            Log.e("saveImage", "图片路径:$path")
        }

        //android 11 需要权限才能在外部存储上创建自己的应用特定目录
        fun getSaveImagePath(context : Context) : String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //获取系统为您的应用程序提供的目录
                context.getExternalFilesDir("").toString() + File.separator + "Utils" + File.separator
            } else {
                Environment.getExternalStorageDirectory().toString() + "/Utils/"
            }
        }

        //把文件插入到系统图库
        fun insertImage(file : File, context : Context) {
            try {
                MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, file.name, null)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}