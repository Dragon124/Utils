package com.example.utils.utils

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.text.DecimalFormat

class CacheUtil {
    companion object {

        val SIZETYPE_B = 1 //获取文件大小单位为B的double值

        val SIZETYPE_KB = 2 //获取文件大小单位为KB的double值

        val SIZETYPE_MB = 3 //获取文件大小单位为MB的double值

        val SIZETYPE_GB = 4 //获取文件大小单位为GB的double值

        /**
         * 获取文件指定文件的指定单位的大小
         *
         * @param filePath 文件路径
         * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
         * @return double值的大小
         */
        fun getFileOrFilesSize(filePath : String, sizeType : Int) : Double {
            val file = File(filePath)
            var blockSize : Long = 0
            try {
                if (!file.exists()) {
                    Log.e("获取文件大小", "找不到该文件夹目录:$filePath")
                    return 0.0
                }
                blockSize = if (file.isDirectory) {
                    getFileSizes(file)
                } else {
                    getFileSize(file)
                }
            } catch (e : Exception) {
                e.printStackTrace()
                Log.e("获取文件大小", "获取失败!")
            }
            return FormetFileSize(blockSize, sizeType)
        }

        /**
         * 获取指定文件大小
         */
        @Throws(Exception::class) private fun getFileSize(file : File) : Long {
            var size : Long = 0
            if (file.exists()) {
                var fis : FileInputStream? = null
                fis = FileInputStream(file)
                size = fis.available().toLong()
                fis.close()
            } else {
                Log.e("获取文件大小", "文件不存在!")
            }
            return size
        }

        /**
         * 获取指定文件夹
         *
         * @param f
         * @return
         * @throws Exception
         */
        @Throws(Exception::class) private fun getFileSizes(f : File) : Long {
            var size : Long = 0
            val flist = f.listFiles()
            for (i in flist.indices) {
                size = if (flist[i].isDirectory) {
                    size + getFileSizes(flist[i])
                } else {
                    size + getFileSize(flist[i])
                }
            }
            return size
        }

        /**
         * 转换文件大小,指定转换的类型
         *
         * @param fileS
         * @param sizeType
         * @return
         */
        private fun FormetFileSize(fileS : Long, sizeType : Int) : Double {
            val df = DecimalFormat("#.00")
            var fileSizeLong = 0.0
            when (sizeType) {
                SIZETYPE_B -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble()))
                SIZETYPE_KB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))
                SIZETYPE_MB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))
                SIZETYPE_GB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1073741824))
                else -> {
                }
            }
            return fileSizeLong
        }

        /**
         * 删除目录及目录下的文件
         *
         * @param filePath 要删除的目录的文件路径
         * @return 目录删除成功返回true，否则返回false
         */
        fun deleteDirectory(context : Context, filePath : String) : Boolean {
            // 如果dir不以文件分隔符结尾，自动添加文件分隔符
            var filePath = filePath
            if (!filePath.endsWith(File.separator)) filePath = filePath + File.separator
            val dirFile = File(filePath)
            // 如果dir对应的文件不存在，或者不是一个目录，则退出
            if (!dirFile.exists() || !dirFile.isDirectory) {
                Log.e("删除文件", "删除目录失败：" + filePath + "不存在！")
                return false
            }
            var flag = true
            // 删除文件夹中的所有文件包括子目录
            val files = dirFile.listFiles()
            for (file in files) {
                // 删除子文件
                if (file.isFile) {
                    flag = deleteSingleFile(file.absolutePath)
                    if (!flag) break
                } else if (file.isDirectory) {
                    flag = deleteDirectory(context, file
                        .absolutePath)
                    if (!flag) break
                }
            }
            //通知图库更新图片
            updateFileFromDatabase(context, filePath)
            if (!flag) {
                Log.e("删除文件", "删除目录失败！")
                return false
            }
            // 删除当前目录
            return if (dirFile.delete()) {
                Log.e("删除文件", " 删除目录" + filePath + "成功！")
                true
            } else {
                Log.e("删除文件", "删除目录：" + filePath + "失败！")
                false
            }
        }

        /**
         * 删除单个文件
         *
         * @param name 要删除的文件的文件名
         * @return 单个文件删除成功返回true，否则返回false
         */
        fun deleteSingleFile(name : String) : Boolean {
            val file = File(name)
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            return if (file.exists() && file.isFile) {
                if (file.delete()) {
                    Log.e("123", " 删除单个文件" + name + "成功！")
                    true
                } else {
                    Log.e("123", "删除单个文件" + name + "失败！")
                    false
                }
            } else {
                Log.e("123", "删除单个文件失败：" + name + "不存在！")
                false
            }
        }

        //删除文件后更新数据库  通知媒体库更新文件夹,！！！！！filepath（文件夹路径）要求尽量精确，以防删错
        fun updateFileFromDatabase(context : Context, filepath : String) {
            val where = MediaStore.Audio.Media.DATA + " like \"" + filepath + "%" + "\""
            val i = context.contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, where, null)
            if (i > 0) {
                Log.e("删除文件", "媒体库更新成功！")
            }
        }
    }
}