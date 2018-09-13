package com.llj.architecturedemo.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity
import com.llj.socialization.Platform
import com.llj.socialization.share.ShareObject
import com.llj.socialization.share.ShareUtil
import com.llj.socialization.share.callback.ShareListener
import com.llj.socialization.share.model.ShareResult
import java.lang.ref.WeakReference


/**
 * ArchitectureDemo.
 * describe:
 * author liulj
 * date 2018/8/10
 */
@Route(path = CRouter.APP_SHARE_ACTIVITY)
class ShareActivity : MvcBaseActivity() {


    override fun layoutId(): Int {
        return R.layout.activity_share
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }

    private class MyShareListener : ShareListener {

        private var mActivity: WeakReference<MvcBaseActivity>

        constructor(activity: MvcBaseActivity) : super() {
            mActivity = WeakReference(activity)
        }

        override fun onShareResponse(shareResult: ShareResult?) {
            if (shareResult == null) {
                return
            }
            when (shareResult.response) {
                ShareResult.RESPONSE_SHARE_NOT_INSTALL -> mActivity.get()?.showLongToast("应用未安装")
                ShareResult.RESPONSE_SHARE_SUCCESS -> mActivity.get()?.showLongToast("分享成功")
                ShareResult.RESPONSE_SHARE_FAILURE -> if (!TextUtils.isEmpty(shareResult.message)) {
                    mActivity.get()?.showLongToast(shareResult.message)
                } else {
                    mActivity.get()?.showLongToast("分享失败")
                }
                ShareResult.RESPONSE_SHARE_HAS_CANCEL -> mActivity.get()?.showLongToast("分享已取消")
                ShareResult.RESPONSE_SHARE_AUTH_DENIED -> mActivity.get()?.showLongToast("分享被拒绝")
            }
        }

        override fun imageLocalPathWrap(imageLocalPath: String): String? {
            return null
        }

        override fun getExceptionImage(): Bitmap? {
            return null
        }

    }

    private val mShareListener = MyShareListener(this)


    @OnClick(R.id.tv_qq_title,
            R.id.tv_qq_title_target,
            R.id.tv_qq_description,
            R.id.tv_qq_description_target,
            R.id.tv_qq_text,
            R.id.tv_qq_image,
            R.id.tv_qq_image_text,
            R.id.tv_qqzone_title,
            R.id.tv_qqzone_title_target,
            R.id.tv_qqzone_description,
            R.id.tv_qqzone_description_target,
            R.id.tv_qqzone_text,
            R.id.tv_qqzone_image,
            R.id.tv_qqzone_image_text,
            R.id.tv_wechat_title,
            R.id.tv_wechat_desc,
            R.id.tv_wechat_text,
            R.id.tv_wechat_image,
            R.id.tv_wechat_webpage,
            R.id.tv_wechat_circle_title,
            R.id.tv_wechat_circle_desc,
            R.id.tv_wechat_circle_text,
            R.id.tv_wechat_circle_image,
            R.id.tv_wechat_circle_webpage,
            R.id.tv_sign,
            R.id.tv_sms,
            R.id.tv_copy)
    fun onViewClicked(view: View) {
        val shareObject = ShareObject()

        when (view.id) {
            R.id.tv_qq_title -> {
                shareObject.title = "这是标题"
                ShareUtil.shareTitle(this, Platform.PlatformType.QQ, shareObject, mShareListener)
            }
            R.id.tv_qq_description -> {
                shareObject.description = "测试内容"
                ShareUtil.shareDescription(this, Platform.PlatformType.QQ, shareObject, mShareListener)
            }
            R.id.tv_qq_title_target -> {
                shareObject.title = "这是标题"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.QQ, shareObject, mShareListener)
            }
            R.id.tv_qq_description_target -> {
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.QQ, shareObject, mShareListener)
            }
            R.id.tv_qq_text -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.QQ, shareObject, mShareListener)
            }
            R.id.tv_qq_image -> {
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareImage(this, Platform.PlatformType.QQ, shareObject, mShareListener)
            }
            R.id.tv_qq_image_text -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareWeb(this, Platform.PlatformType.QQ, shareObject, mShareListener)
            }
            R.id.tv_qqzone_title -> {
                shareObject.title = "这是标题"
                ShareUtil.shareTitle(this, Platform.PlatformType.QQ_ZONE, shareObject, mShareListener)
            }
            R.id.tv_qqzone_description -> {
                shareObject.description = "这是内容"
                ShareUtil.shareDescription(this, Platform.PlatformType.QQ_ZONE, shareObject, mShareListener)
            }
            R.id.tv_qqzone_title_target -> {
                shareObject.title = "这是标题"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.QQ_ZONE, shareObject, mShareListener)
            }
            R.id.tv_qqzone_description_target -> {
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.QQ_ZONE, shareObject, mShareListener)
            }
            R.id.tv_qqzone_text -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.QQ_ZONE, shareObject, mShareListener)
            }
            R.id.tv_qqzone_image -> {
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareImage(this, Platform.PlatformType.QQ_ZONE, shareObject, mShareListener)
            }
            R.id.tv_qqzone_image_text -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareWeb(this, Platform.PlatformType.QQ_ZONE, shareObject, mShareListener)
            }
            R.id.tv_wechat_title -> {
                shareObject.title = "这是标题"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareTitle(this, Platform.PlatformType.WECHAT, shareObject, mShareListener)
            }
            R.id.tv_wechat_desc -> {
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareDescription(this, Platform.PlatformType.WECHAT, shareObject, mShareListener)
            }
            R.id.tv_wechat_text -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.WECHAT, shareObject, mShareListener)
            }
            R.id.tv_wechat_image -> {
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareImage(this, Platform.PlatformType.WECHAT, shareObject, mShareListener)
            }
            R.id.tv_wechat_webpage -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareWeb(this, Platform.PlatformType.WECHAT, shareObject, mShareListener)
            }
            R.id.tv_wechat_circle_title -> {
                shareObject.title = "这是标题"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareTitle(this, Platform.PlatformType.WECHAT_CIRCLE, shareObject, mShareListener)
            }
            R.id.tv_wechat_circle_desc -> {
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareDescription(this, Platform.PlatformType.WECHAT_CIRCLE, shareObject, mShareListener)
            }
            R.id.tv_wechat_circle_text -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareText(this, Platform.PlatformType.WECHAT_CIRCLE, shareObject, mShareListener)
            }
            R.id.tv_wechat_circle_image -> {
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareImage(this, Platform.PlatformType.WECHAT_CIRCLE, shareObject, mShareListener)
            }
            R.id.tv_wechat_circle_webpage -> {
                shareObject.title = "这是标题"
                shareObject.description = "这是内容"
                shareObject.imageUrlOrPath = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg"
                shareObject.targetUrl = "https://www.baidu.com"
                ShareUtil.shareWeb(this, Platform.PlatformType.WECHAT_CIRCLE, shareObject, mShareListener)
            }
            R.id.tv_sign -> {
                ShareUtil.shareWeb(this, Platform.PlatformType.SINA, shareObject, mShareListener)
            }
            R.id.tv_sms -> {
                ShareUtil.sendSms(this, "", "测试短信")
            }
            R.id.tv_copy -> {
                ShareUtil.copyText(this, "测试复制")
            }
        }
    }
}