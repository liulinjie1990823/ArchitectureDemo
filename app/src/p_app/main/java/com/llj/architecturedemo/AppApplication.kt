package com.llj.architecturedemo

import android.content.Context
import com.llj.component.service.ComponentApplication
import com.llj.socialization.SocialConstants
import com.llj.socialization.share.SocialConfig
import com.llj.socialization.share.SocialManager


/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/18
 */
class AppApplication : ComponentApplication() {
    override fun injectApp() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        val config = SocialConfig.instance().qqId("1103566659")
                .wx("wx78b27fadc81b6df4", "022fa45d435d7845179b6ae8d1912690")
                .sign("1476913513", "http://www.jiehun.com.cn/api/weibo/_grant", SocialConstants.SCOPE)
        SocialManager.init(config)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }
}
