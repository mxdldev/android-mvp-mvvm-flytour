package com.fly.tour.common.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.core.content.FileProvider

import com.tbruyelle.rxpermissions2.RxPermissions
import com.fly.tour.common.util.ToastUtil

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import io.reactivex.functions.Consumer
import me.nereo.multi_image_selector.MultiImageSelector

/**
 * Description: <h3>多媒体工具类</h3>
 *
 *  * 1.图片选择器，可算多张图片
 *  * 2.拍照
 *  * 3.拍视频
 *  * 4.创建一个图片路径
 *  * 5.创建一个视频路径
 *
 * <h3>注意事项：</h3>
 *  * 1. 拍照、拍视频、选择图片完成的回调都在onActivityResult中回调的
 *  * 2.选择图片获取：List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)</String>
 *
 * Author:      mxdl<br>
 * Date:        2018/12/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
object MultiMediaUtil {
    val SELECT_IMAGE = 1001
    val TAKE_PHONE = 1002
    val TAKE_VIDEO = 1003

    /**
     * 打开图片选择器，选择图片<br>
     * 来获取图片
     *
     * @param activity
     * @param count：选择图片个数
     * @param requestcode
     */
    fun pohotoSelect(activity: androidx.fragment.app.FragmentActivity, count: Int, requestcode: Int) {
        pohotoSelect(activity, null, count, requestcode)
    }

    fun pohotoSelect(fragment: androidx.fragment.app.Fragment, count: Int, requestcode: Int) {
        pohotoSelect(null, fragment, count, requestcode)
    }

    private fun pohotoSelect(activity: androidx.fragment.app.FragmentActivity?, fragment: androidx.fragment.app.Fragment?, count: Int, requestcode: Int) {
        var rxPermissions: RxPermissions? = null
        if (activity != null) {
            rxPermissions = RxPermissions(activity)
        } else if (fragment != null) {
            rxPermissions = RxPermissions(fragment)
        }
        rxPermissions!!.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe { aBoolean ->
            if (aBoolean!!) {
                if (activity != null) {
                    MultiImageSelector.create().showCamera(false).count(count).single().multi()
                            //.origin(ArrayList<String>)
                            .start(activity, requestcode)
                } else if (fragment != null) {
                    MultiImageSelector.create().showCamera(false).count(count).single().multi()
                            //.origin(ArrayList<String>)
                            .start(fragment, requestcode)
                }
            } else {
                ToastUtil.showToast("无读写外部存储设备权限")
            }
        }


    }

    /**
     * 拍照
     *
     * @param activity
     * @param path:照片存放的路径
     * @param requestcode
     */
    fun takePhoto(activity: androidx.fragment.app.FragmentActivity, path: String, requestcode: Int) {
        takePhoto(activity, null, path, requestcode)
    }

    fun takePhoto(fragment: androidx.fragment.app.Fragment, path: String, requestcode: Int) {
        takePhoto(null, fragment, path, requestcode)
    }

    private fun takePhoto(activity: androidx.fragment.app.FragmentActivity?, fragment: androidx.fragment.app.Fragment?, path: String, requestcode: Int) {
        if (activity == null && fragment == null) {
            return
        }
        var rxPermissions: RxPermissions? = null
        if (activity != null) {
            rxPermissions = RxPermissions(activity)
        } else if (fragment != null) {
            rxPermissions = RxPermissions(fragment)
        }

        rxPermissions!!.request(Manifest.permission.CAMERA).subscribe { aBoolean ->
            if (aBoolean!!) {
                val file = File(path)
                try {
                    if (file.createNewFile()) {
                        val intent = Intent()
                        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        if (activity != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity, "com.zjx.vcars.fileprovider", file))
                            activity.startActivityForResult(intent, requestcode)
                        } else if (fragment != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(fragment.context!!, "com.zjx.vcars.fileprovider", file))
                            fragment.startActivityForResult(intent, requestcode)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtil.showToast("无法启动拍照程序")
                }

            } else {
                ToastUtil.showToast("无摄像头权限,无法进行拍照!")
            }
        }
    }


    /**
     * 拍视频
     *
     * @param activity
     * @param path:视频存放的路径
     * @param requestcode
     */
    fun takeVideo(activity: androidx.fragment.app.FragmentActivity, path: String, requestcode: Int) {
        takeVideo(activity, null, path, requestcode)
    }

    fun takeVideo(fragment: androidx.fragment.app.Fragment, path: String, requestcode: Int) {
        takeVideo(null, fragment, path, requestcode)
    }

    private fun takeVideo(activity: androidx.fragment.app.FragmentActivity?, fragment: androidx.fragment.app.Fragment?, path: String, requestcode: Int) {
        if (activity == null && fragment == null) {
            return
        }
        var rxPermissions: RxPermissions? = null
        if (activity != null) {
            rxPermissions = RxPermissions(activity)
        } else if (fragment != null) {
            rxPermissions = RxPermissions(fragment)
        }
        rxPermissions!!.request(Manifest.permission.CAMERA).subscribe { aBoolean ->
            if (aBoolean!!) {
                val file = File(path)
                try {
                    if (file.createNewFile()) {
                        val intent = Intent()
                        intent.action = MediaStore.ACTION_VIDEO_CAPTURE
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        if (activity != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity, "com.zjx.vcars.fileprovider", file))
                            activity.startActivityForResult(intent, requestcode)
                        } else if (fragment != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(fragment.context!!, "com.zjx.vcars.fileprovider", file))
                            fragment.startActivityForResult(intent, requestcode)
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtil.showToast("无法启动拍视频程序")
                }

            } else {
                ToastUtil.showToast("无摄像头权限,无法进行拍视频!")
            }
        }
    }

    //获取图片路径
    fun getPhotoPath(activity: Activity): String {
        val filename = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + ".jpg"
        return activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + filename
    }

    //获取视频的路径
    fun getVideoPath(activity: Activity): String {
        val filename = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + ".3gp"
        return activity.getExternalFilesDir(Environment.DIRECTORY_MOVIES)!!.absolutePath + File.separator + filename
    }
}
