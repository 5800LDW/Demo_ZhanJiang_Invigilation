package com.tecsun.jc.base.builder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.utils.log.LogUtil
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * 考场内取证: 学生的准考证图片的保存
 *
 * @author liudongwen
 * @date 2019/9/12
 */
object StudentTeacherTakeAdmissionTicketImageBuilder {

    fun savePic(bitmap: Bitmap?, sfzh: String) {
        bitmap?.let {
            deleteImageBySFZH(sfzh)
            var filePath = saveImage(bitmap, sfzh)
            LogUtil.e(">>>>>>>>>>> 人像面  保存到本地的路径:$filePath")
        }
    }


    fun getSFZBitmap(sfzh: String): Bitmap? {
        return getBitmapByFilePathWithName(sfzh)
    }

    private fun getBitmapByFileName1(fileName: String?): Bitmap? {
        fileName?.let {
            return getBitmapByFileName2(fileName)
        }
        return null
    }

    private fun getBitmapByFileName2(fileName: String): Bitmap? {
        val filePath = getFilePath()
        val file = File(filePath + File.separator, fileName)
        return when (file.exists()) {
            false -> null
            true -> BitmapFactory.decodeFile(file.toString())
        }
    }

    private fun getBitmapByFilePathWithName(sfzh: String): Bitmap? {
        val fileName = sfzh + ".png"
        val filePath = getFilePath()
        val file = File(filePath + File.separator, fileName)

//        val file = File(filePathWithName)
        return when (file.exists()) {
            false -> null
            true -> BitmapFactory.decodeFile(file.toString())
        }
    }


    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     * @return
     */
    private fun saveImage(bitmap: Bitmap, sfzh: String): String {

        val fileName = sfzh + ".png"

        val filePath = getFilePath()

        val file = File(filePath + File.separator, fileName)

        if (file.exists()) {
            file.delete()
        }

        var fos: FileOutputStream? = null

        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            LogUtil.e(">>>>>>>>>>>>>  FileNotFoundException ApplyPreviewBuilder")
            LogUtil.e(e)
        } catch (e: IOException) {
            LogUtil.e(">>>>>>>>>>>>>  IOException ApplyPreviewBuilder")
            LogUtil.e(e)
        }

        return file.absolutePath
    }

    private fun deleteImageByFilePath(filePathWithName: String) {
        if (!filePathWithName.isNullOrBlank()) {
            val file = File(filePathWithName)
            try {
                if (file.exists()) {
                    file.delete()
                    LogUtil.e(">>>>>>>>>>>>>  $file 文件已经删除2")
                }
            } catch (e: Exception) {
                LogUtil.e(">>>>>>>>>>>>>   Exception ApplyPreviewBuilder")
                LogUtil.e(e)
            }
        }
    }


    private fun getFilePath(): String {
        return JinLinApp.context!!.getExternalFilesDir("admission_ticketI").absolutePath
    }

    private fun deleteImageBySFZH(sfzh: String) {
        sfzh?.let {
            val filePath = getFilePath()
            val file = File(filePath + File.separator,  "$sfzh.png")
            try {
                if (file.exists()) {
                    file.delete()
                    LogUtil.e(">>>>>>>>>>>>>  $file 文件已经删除1")
                }
            } catch (e: Exception) {
                LogUtil.e(">>>>>>>>>>>>>   Exception ApplyPreviewBuilder")
                LogUtil.e(e)
            }
        }
    }

    //删除所有的身份证图片
    fun deleteAllSFZImage() {
        deleteDirectory(getFilePath())
    }

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    fun deleteFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.isFile && file.exists()) {
            file.delete()
        } else false
    }

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    fun deleteFileBySFZH(sfzh: String): Boolean {
        val fileName = sfzh + ".png"
        val filePath = getFilePath()
        val file = File(filePath + File.separator, fileName)
        return if (file.isFile && file.exists()) {
            file.delete()
        } else false
    }

    /**
     * 删除文件夹以及目录下的文件
     * @param   filePath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    fun deleteDirectory(filePath: String): Boolean {
        var filePath = filePath
        var flag = false
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator
        }
        val dirFile = File(filePath)
        if (!dirFile.exists() || !dirFile.isDirectory) {
            return false
        }
        flag = true
        val files = dirFile.listFiles()
        //遍历删除文件夹下的所有文件(包括子目录)
        for (i in files.indices) {
            if (files[i].isFile) {
                //删除子文件
                flag = deleteFile(files[i].absolutePath)
                if (!flag) break
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].absolutePath)
                if (!flag) break
            }
            LogUtil.e("删除成功:$i")

        }
        return if (!flag) false else dirFile.delete()
        //删除当前空目录
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     * @param filePath  要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    fun DeleteFolder(filePath: String): Boolean {
        val file = File(filePath)
        return if (!file.exists()) {
            false
        } else {
            if (file.isFile) {
                // 为文件时调用删除文件方法
                deleteFile(filePath)
            } else {
                // 为目录时调用删除目录方法
                deleteDirectory(filePath)
            }
        }
    }

}
















