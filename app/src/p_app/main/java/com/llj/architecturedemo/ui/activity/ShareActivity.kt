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
import com.llj.socialization.share.ShareUtil
import com.llj.socialization.share.callback.ShareListener
import com.llj.socialization.share.model.ShareResult

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


    private val mShareListener = object : ShareListener() {
        override fun onShareResponse(shareResult: ShareResult?) {
            if (shareResult == null) {
                return
            }
            when (shareResult.response) {
                ShareResult.RESPONSE_SHARE_NOT_INSTALL -> showLongToast("应用未安装")
                ShareResult.RESPONSE_SHARE_SUCCESS -> showLongToast("分享成功")
                ShareResult.RESPONSE_SHARE_FAILURE -> if (!TextUtils.isEmpty(shareResult.getMessage())) {
                    showLongToast(shareResult.getMessage())
                } else {
                    showLongToast("分享失败")
                }
                ShareResult.RESPONSE_SHARE_HAS_CANCEL -> showLongToast("分享已取消")
                ShareResult.RESPONSE_SHARE_AUTH_DENIED -> showLongToast("分享被拒绝")
            }
        }

        override fun imageLocalPathWrap(imageLocalPath: String): String? {
            return null
        }

        override fun getExceptionImage(): Bitmap? {
            return null
        }
    }

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
        when (view.id) {
            R.id.tv_qq_title -> {
                ShareUtil.shareTitle(this, Platform.PlatformType.QQ, "这是标题", mShareListener)
            }
            R.id.tv_qq_description -> {
                ShareUtil.shareDescription(this, Platform.PlatformType.QQ, "测试内容", mShareListener)
            }
            R.id.tv_qq_title_target -> {
                ShareUtil.shareText(this, Platform.PlatformType.QQ, "这是标题", null, "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qq_description_target -> {
                ShareUtil.shareText(this, Platform.PlatformType.QQ, null, "这是内容", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qq_text -> {
                ShareUtil.shareText(this, Platform.PlatformType.QQ, "这是标题", "这是内容", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qq_image -> {
                ShareUtil.shareMedia(this, Platform.PlatformType.QQ, "这是标题", "测试内容", "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qq_image_text -> {
                ShareUtil.shareMedia(this, Platform.PlatformType.QQ, "这是标题", "这是内容", "", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qqzone_title -> {
                ShareUtil.shareTitle(this, Platform.PlatformType.QQ_ZONE, "这是标题", mShareListener)
            }
            R.id.tv_qqzone_description -> {
                ShareUtil.shareDescription(this, Platform.PlatformType.QQ_ZONE, "这是内容", mShareListener)
            }
            R.id.tv_qqzone_title_target -> {
                ShareUtil.shareText(this, Platform.PlatformType.QQ_ZONE, "这是标题", null, "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qqzone_description_target -> {
                ShareUtil.shareText(this, Platform.PlatformType.QQ_ZONE, null, "这是内容", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qqzone_text -> {
                ShareUtil.shareText(this, Platform.PlatformType.QQ_ZONE, "这是标题", "这是内容", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_qqzone_image -> {
                ShareUtil.shareImage(this, Platform.PlatformType.QQ_ZONE, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg", mShareListener)
            }
            R.id.tv_qqzone_image_text -> {
                ShareUtil.shareMedia(this, Platform.PlatformType.QQ_ZONE, "这是标题", "这是内容", "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_wechat_title -> {
                ShareUtil.shareTitle(this, Platform.PlatformType.WECHAT, "这是标题", mShareListener)
            }
            R.id.tv_wechat_desc -> {
                ShareUtil.shareDescription(this, Platform.PlatformType.WECHAT, "测试内容", mShareListener)
            }
            R.id.tv_wechat_text -> {
                ShareUtil.shareText(this, Platform.PlatformType.WECHAT, "这是标题", "测试内容", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_wechat_image -> {
                ShareUtil.shareImage(this, Platform.PlatformType.WECHAT, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg", mShareListener)
            }
            R.id.tv_wechat_webpage -> {
                ShareUtil.shareMedia(this, Platform.PlatformType.WECHAT, "这是标题", "测试内容", "https://img.tthunbohui.cn/dmp/h/old/1533225600/jh-img-orig-ga_1025269300355801088_80_80_3770.jpg?x-oss-process=image/resize,m_fill,h_80,w_80", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_wechat_circle_title -> {
                ShareUtil.shareTitle(this, Platform.PlatformType.WECHAT_CIRCLE, "这是标题", mShareListener)
            }
            R.id.tv_wechat_circle_desc -> {
                ShareUtil.shareDescription(this, Platform.PlatformType.WECHAT_CIRCLE, "测试内容", mShareListener)
            }
            R.id.tv_wechat_circle_text -> {
                ShareUtil.shareText(this, Platform.PlatformType.WECHAT_CIRCLE, "这是标题", "测试内容", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_wechat_circle_image -> {
                ShareUtil.shareImage(this, Platform.PlatformType.WECHAT_CIRCLE, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg", mShareListener)
            }
            R.id.tv_wechat_circle_webpage -> {
                ShareUtil.shareMedia(this, Platform.PlatformType.WECHAT_CIRCLE, "", "", "http://s8.tthunbohui.cn/www/mobile/img/sm03.gif?00c46990", "https://www.baidu.com", mShareListener)
            }
            R.id.tv_sign -> {
                ShareUtil.shareMedia(this, Platform.PlatformType.SINA, "这是标题", "这是内容", "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=a33885321bdfa9ece9235e540ab99d76/8b13632762d0f703c06472f202fa513d2797c5ce.jpg", "https://www.baidu.com", mShareListener)
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