package com.llj.architecturedemo.analysis;

import com.llj.component.service.analysis.Action;

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/3/28
 */

public interface AppAction extends Action {
    //广告
    String AD_SKIP                = "ad_skip";
    String AD_CLICK               = "ad_click";
    //商城 店铺 店铺详情
    String MALL_STORE_ACTIVITY    = "mall_store_activity";//查看活动
    String MALL_STORE_RANKING     = "mall_store_ranking";//查看榜单
    String MALL_STORE_DISCOUNT    = "mall_store_discount";//查看优惠
    String MALL_STORE_COUPON      = "mall_store_coupon";//查看领券
    String MALL_STORE_MAP         = "mall_store_map";//查看地图
    //商城 商品 商品详情
    String MALL_PRODUCT_RANKING   = "mall_product_ranking";//查看榜单
    String MALL_PRODUCT_COUPON    = "mall_product_coupon";//查看领券
    String MALL_PRODUCT_STANDARD  = "mall_product_standard";//查看规格
    String MALL_PRODUCT_GUARANTEE = "mall_product_guarantee";//查看保障
    String MALL_STORE_NEWS        = "mall_store_news";//商家动态

    //签到
    String SIGN_IN_HOME              = "home_signin";//首页点击签到
    String SIGN_IN_MINE              = "mine_signin";//个人页点击签到
    String SIGN_IN_CASH              = "cash_signin";//现金券页签到
    //推送
    String PUSH_CONFIRM              = "push_confirm";//推送确认按钮
    String PUSH_CANCEL               = "push_cancel";//推送忽略按钮
    //CMS position
    String CMS                       = "cms";         //cms统计位
    //我的页面
    String MINE_SCAN                 = "mine_scan";//扫一扫
    String MINE_CODE                 = "mine_code";//我的二维码
    String MINE_NEWS                 = "mine_news";//消息
    String MINE_SETTING              = "mine_setting";//设置
    String MINE_COLLECT              = "mine_collect";//收藏
    String MINE_ACTIVITY             = "mine_activity";//活动
    String MINE_RESERVE              = "mine_reserve";//我的预约
    String MINE_FOOT                 = "mine_foot";//足迹
    String MINE_LEVEL                = "mine_level";//会员等级
    String MINE_INTEGRAL             = "mine_integral";//积分
    String MINE_SHOPPING             = "mine_shopping";//购物车
    String MINE_CASH                 = "mine_cash";//现金券
    String MINE_ORDER                = "mine_order";//订单
    String MINE_EVALUATE             = "mine_evaluate";//评价
    String MINE_WALLET               = "mine_wallet";//我的钱包
    String STORE_NEWS_VIDEO_STORE    = "store_news_video_store";//视频秀店铺入口
    String STORE_NEWS_VIDEO_RESOURCE = "store_news_video_resource";//视频秀资源位
    String STORE_NEWS_VIDEO_SHARE    = "store_news_video_share";//视频秀分享

    //首页底部tab
    String TAB_HOME    = "tab_home";//首页
    String TAB_CASH    = "tab_cash";//现金券
    String TAB_TICKET  = "tab_ticket";//索票页
    String TAB_GONGLVE = "tab_gonglve";//攻略
    String TAB_MINE    = "tab_mine";//我的
}
