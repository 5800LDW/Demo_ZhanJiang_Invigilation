package com.tecsun.jc.base.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.utils.log.LogUtil


object ShowPicUtil {

    @JvmOverloads
    fun showRoundedCorner(
        activity: Activity,
        imageView: ImageView?,
        view: View?,
        url: String?,
        myInterface: ShowPicInterface = object :ShowPicInterface{
            override fun onResourceReady(resource: Drawable?) {
                imageView?.visibility = View.VISIBLE
                //这里是必须要隐藏的,不隐藏的话会变成图片的底色了,因为图片可能是部分透明
                view?.visibility = View.GONE
            }

            override fun onLoadFailed() {
                imageView?.visibility = View.GONE
                view?.visibility = View.VISIBLE
            }

        }
    ) {

        //1
//        val simpleTarget = object : SimpleTarget<Drawable>() {
//            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                myInterface?.onResourceReady(resource)
//            }
//        }


        //2
//        Glide.with(activity)
//            .load(url)
//            .fitCenter()
////            .listener(SGlideRequestListener(myInterface))
//            .apply(
//                RequestOptions.errorOf(mError).placeholder(mPlaceholder).transform(
//                    RadiusTransformation(5f)
//                )
//            )
//            .into(imageView)

        //3
        imageView?.visibility = View.VISIBLE
        view?.visibility = View.VISIBLE

        imageView?.let {
            Glide.with(activity)
                .load(url)
                .listener(SGlideRequestListener(myInterface))
                .transforms(CenterCrop(), RoundedCorners(ScreenUtil.dip2px(JinLinApp.context, 5f)))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }

    fun clear(iv: ImageView, activity: Activity) {
        Glide.with(activity).clear(iv)
    }


    interface ShowPicInterface {
        fun onResourceReady(resource: Drawable?)

        fun onLoadFailed()
    }


    fun showPic(activity: Activity, imageView: ImageView, url: String) {
        if (activity != null && imageView != null && url != null) {
            Glide.with(activity).load(url).centerCrop().into(imageView)
        }
    }


    internal class SGlideRequestListener(
        l: ShowPicInterface
    ) : RequestListener<Drawable> {

        var myInterface = l
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            LogUtil.e(">>>>>>>>>>>>>>>>> onLoadFailed")
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            LogUtil.e(">>>>>>>>>>>>>>>>> onResourceReady")
            myInterface?.onResourceReady(resource)
            return false
        }

    }


    fun load(id: Int, iv: ImageView) {
        Glide.with(JinLinApp.context!!).load(id).centerCrop().into(iv)
    }


}

//
//
//object ShowPicUtil {
//
//    fun showRoundedCorner(activity: Activity, imageView: ImageView, url: String,myInterface: ShowPicInterface) {
////设置图片圆角角度
//        var url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565599145344&di=c169fb8bb95de29e2e4021e7e9fd280d&imgtype=0&src=http%3A%2F%2Fimg.redocn.com%2Fsheji%2F20141219%2Fzhongguofengdaodeliyizhanbanzhijing_3744115.jpg"
////        var url1 = "https://www.jikedaohang.com/static/imgs/wxgzh.jpg"
//        val roundedCorners = RoundedCorners(10)
////通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
////        val options = RequestOptions.bitmapTransform(roundedCorners).override(300., 300)
//        val options = RequestOptions.bitmapTransform(roundedCorners)
//
//
//        val simpleTarget = object : SimpleTarget<Drawable>() {
//            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                myInterface?.onResourceReady(resource)
//
//            }
//        }
////
////        var viewTarget = object: CustomViewTarget<ImageView, Drawable>(imageView) {
////            override fun onResourceCleared(placeholder: Drawable?) {
////                LogUtil.e(">>>>>>>>>>>>>>>>>>>.onResourceCleared")
////            }
////
////            override fun onLoadFailed(errorDrawable: Drawable?) {
////                LogUtil.e(">>>>>>>>>>>>>>>>>>>.onLoadFailed")
////            }
////
////            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
////                LogUtil.e(">>>>>>>>>>>>>>>>>>>.onResourceReady")
////                myInterface?.onResourceReady(resource)
////            }
////
////        }
//
//
////        Glide
////            .with(activity)
////            .load(url1)
////            .fitCenter()
////            .apply(options)
//////            .diskCacheStrategy(DiskCacheStrategy.NONE)//不使用缓存
//////            .skipMemoryCache(true)
////            .listener(object :RequestListener<Drawable>{
////                override fun onLoadFailed(
////                    e: GlideException?,
////                    model: Any?,
////                    target: Target<Drawable>?,
////                    isFirstResource: Boolean
////                ): Boolean {
////
////                    LogUtil.e(">>>>>>>>>>>>>>>>>>>  onLoadFailed   >>>>>>>>>>>>>>>>>>>>>>")
////                    return true
////                }
////
////                override fun onResourceReady(
////                    resource: Drawable?,
////                    model: Any?,
////                    target: Target<Drawable>?,
////                    dataSource: DataSource?,
////                    isFirstResource: Boolean
////                ): Boolean {
////                    LogUtil.e(">>>>>>>>>>>>>>>>>>>  onResourceReady   >>>>>>>>>>>>>>>>>>>>>>")
////                    myInterface?.onResourceReady(resource)
////
////                    return true
////                }
////
////            })
////            .into(imageView)
//
//
//
//        Glide.with(activity)
//            .load(url1)
//            .diskCacheStrategy(DiskCacheStrategy.NONE)//不使用缓存
//            .skipMemoryCache(true)
//            .transforms(CenterCrop(), RoundedCorners(60))
//            .into(simpleTarget)
//
//    }
//
//    interface ShowPicInterface{
//        fun onResourceReady(resource: Drawable?)
//    }
//
//}
//
//
//































