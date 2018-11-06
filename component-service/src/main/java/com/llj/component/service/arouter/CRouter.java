package com.llj.component.service.arouter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/7/3
 */
public class CRouter {

    //app
    public static final String APP_MAIN_ACTIVITY              = "/app/MainActivity";
    public static final String APP_SECOND_ACTIVITY            = "/app/SecondActivity";
    public static final String APP_TOUCH_EVENT_ACTIVITY       = "/app/TouchEventActivity";
    public static final String APP_RECYCLE_VIEW_ACTIVITY      = "/app/RecycleViewActivity";
    public static final String APP_RXJAVA2_ACTIVITY           = "/app/RxJava2Activity";
    public static final String APP_REQUEST_ACTIVITY           = "/app/RequestActivity";
    public static final String APP_PROXY_ACTIVITY             = "/app/ProxyActivity";
    public static final String APP_NESTED_SCROLLVIEW_ACTIVITY = "/app/NestedScrollViewActivity";
    public static final String APP_LINEAR_LAYOUT_ACTIVITY     = "/app/LinearLayoutActivity";
    public static final String APP_COMPONENT_ACTIVITY         = "/app/ComponentActivity";
    public static final String APP_SHARE_ACTIVITY             = "/app/ShareActivity";
    public static final String APP_MEMORY_LEAK_ACTIVITY       = "/app/MemoryLeakActivity";
    public static final String APP_APT_ACTIVITY               = "/app/AptActivity";
    public static final String APP_HOOK_ACTIVITY              = "/app/HookActivity";
    public static final String WIDGET_CONSTRAINT_ACTIVITY     = "/app/widget/ConstraintActivity";
    public static final String CIRCLE_VIEW_ACTIVITY           = "/app/widget/CircleViewActivity";

    //login
    public static final String LOGIN_LOGIN_ACTIVITY       = "/login/LoginActivity";
    public static final String LOGIN_PHONE_LOGIN_ACTIVITY = "/login/PhoneLoginActivity";
    public static final String LOGIN_REGISTER_ACTIVITY    = "/login/RegisterActivity";



    public static final String QRCODE_CAPTUREACTIVITY                  = "/qrcode/CaptureActivity";
    public static final String APP_GUIDE_ACTIVITY                      = "/app/GuideActivity";                 //跳转到首页
    public static final String APP_ADV_ACTIVITY                        = "/app/AdvertisingActivity";                 //广告页
    public static final String APP_MAIN_TAB_ACTIVITY                   = "/app/MainTabActivity";                 //跳转到首页
    public static final String KEY_TAB_INDEX                           = "KEY_TAB_INDEX";                        //跳转到首页
    public static final String APP_NEW_CHANNEL_ACTIVITY                = "/app/channel/ChannelViewActivity";
    public static final String APP_CHANNEL_TRAVEL_DESTINATION_ACTIVITY = "/app/channel/travel/TravelDestinationActivity";
    public static final String APP_CHANNEL_ACTIVITY                    = "/app/channel/ChannelActivity";
    public static final String ALBUM_PHOTOSCAN                         = "/album/PhotoScanActivity";
    public static final String ALBUM_PHOTOPICKER                       = "/album/photopickeractivity";
    public static final String WEBVIEW_WEBVIEWACTIVITY                 = "/webview/WebviewActivity";

    public static final String KEY_NICKNAME                          = "KEY_NICKNAME";
    public static final String KEY_BIND_PHONE_TYPE                   = "KEY_BIND_PHONE_TYPE";
    public static final int    VALUE_BIND_PHONE                      = -1;
    public static final int    VALUE_UNBIND_PHONE                    = 0;
    public static final int    VALUE_REBIND_PHONE                    = 1;
    public static final String KEY_PHONE                             = "KEY_PHONE";
    public static final String KEY_TYPE                              = "KEY_TYPE";
    public static final int    VALUE_SET_PASSWORD                    = 0;
    public static final int    VALUE_UPDATE_PASSWORD                 = 1;
    public static final String KEY_PUSH_OPEN                         = "KEY_PUSH_OPEN";
    public static final int    VALUE_ADD_SHIPPING_ADDRESS            = 0;
    public static final int    VALUE_EDIT_SHIPPING_ADDRESS           = 1;
    public static final int    VALUE_ADD_ALIPAY                      = 0;
    public static final int    VALUE_UPDATE_ALIPAY                   = 1;
    public static final String KEY_AMOUNT                            = "KEY_AMOUNT";
    public static final String KEY_TRANSFER_STATUS                   = "KEY_TRANSFER_STATUS";
    public static final String KEY_COUNT                             = "KEY_COUNT";
    public static final String KEY_USER_VOUCHER_ID                   = "KEY_USER_VOUCHER_ID";
    public static final String KEY_VOUCHER_STATUS                    = "KEY_VOUCHER_STATUS";
    public static final String KEY_WITHDRAWALS_MONEY                 = "KEY_WITHDRAWALS_MONEY";
    public static final String KEY_SHOPPING_ALLOWANCE                = "KEY_SHOPPING_ALLOWANCE";
    public static final String KEY_TITLE                             = "KEY_TITLE";
    public static final String KEY_CONTENT                           = "KEY_CONTENT";
    public static final String KEY_WEB_URL                           = "KEY_WEB_URL";
    public static final String KEY_IMAGE_URL                         = "KEY_IMAGE_URL";
    public static final String KEY_IMAGE_RES                         = "KEY_IMAGE_RES";
    public static final String KEY_SHARE_TYPE                        = "KEY_SHARE_TYPE";
    public static final String KEY_SHIPPING_ADDRESS                  = "KEY_SHIPPING_ADDRESS";
    public static final String KEY_ALIPAY_INFO                       = "KEY_ALIPAY_INFO";
    public static final String KEY_LEVEL                             = "KEY_LEVEL";
    public static final String KEY_CODE                              = "KEY_CODE";
    public static final String KEY_COUPON_ID                         = "KEY_COUPON_ID";
    public static final String KEY_TAB                               = "KEY_TAB";
    public static final String KEY_INDUSTRY_CATE_ID                  = "industryId";
    public static final String KEY_DESTINATION_ID                    = "destinationId";
    public static final String KEY_DESTINATION_NAME                  = "destinationName";
    public static final String KEY_STORE_ID                          = "storeId";
    public static final String MY_ACTIVITY_TYPE                      = "MY_ACTIVITY_TYPE";
    public static final String KEY_CHANNEL_TITLE                     = "KEY_CHANNEL_TITLE";
    public static final String KEY_DESTINATION_TYPE                  = "pageType";
    public static final String KEY_DESTINATION_ALBUM_TYPE            = "KEY_DESTINATION_ALBUM_TYPE";
    public static final int    VALUE_TRAVEL_DESTINATION_DETAIL       = 0;
    public static final int    VALUE_TRAVEL_STORE_DESTINATION_DETAIL = 1;
    public static final int    VALUE_TRAVEL_DESTINATION_ALBUM        = 0;
    public static final int    VALUE_TRAVEL_STORE_DESTINATION_ALBUM  = 1;
    public static final int    VALUE_TRAVEL_ALBUM                    = 2;
    public static final int    VALUE_TRAVEL_STRORE_ALBUM             = 3;

    public static final String APP_MINE_MEMBER_CENTER_ACTIVITY                  = "/app/mine/MemberCentreActivity";
    public static final String APP_MINE_MY_PROFILE_ACTIVITY                     = "/app/mine/MyProfileActivity";
    public static final String APP_MINE_MODIFY_NICKNAME_ACTIVITY                = "/app/mine/ModifyNicknameActivity";
    public static final String APP_MINE_BIND_PHONE_ACTIVITY                     = "/app/mine/BindPhoneActivity";
    public static final String APP_MINE_SET_PASSWORD_ACTIVITY                   = "/app/mine/SetPasswordActivity";
    public static final String APP_MINE_SETTING_ACTIVITY                        = "/app/mine/SettingActivity";
    public static final String APP_MINE_ABOUT_US_ACTIVITY                       = "/app/mine/AboutUsActivity";
    public static final String APP_MINE_COUPON_QR_CODE_ACTIVITY                 = "/app/mine/CouponQrCodeActivity";
    public static final String APP_MINE_FEEDBACK_ACTIVITY                       = "/app/mine/FeedbackActivity";
    public static final String APP_MINE_MY_INTEGRAL_ACTIVITY                    = "/app/mine/MyIntegralActivity";
    public static final String APP_MINE_OFFICIAL_WECHAT_ACTIVITY                = "/app/mine/OfficialWeChatActivity";
    public static final String APP_MINE_HELP_ACTIVITY                           = "/app/mine/HelpActivity";
    public static final String APP_MINE_MY_COUPON_ACTIVITY                      = "/app/mine/MyCouponActivity";
    public static final String APP_MINE_MY_COUPON_FRAGMENT                      = "/app/mine/MyCouponFragment";
    public static final String APP_MINE_BALANCE_ACTIVITY                        = "/app/mine/BalanceActivity";
    public static final String APP_MINE_INCOME_AND_EXPENDITURE_DETAILS_ACTIVITY = "/app/mine/IncomeAndExpenditureDetailsActivity";
    public static final String APP_MINE_WITHDRAW_ACTIVITY                       = "/app/mine/WithdrawActivity";
    public static final String APP_MINE_WITHDRAW_SUCCESS_ACTIVITY               = "/app/mine/WithdrawSuccessActivity";
    public static final String APP_MINE_WITHDRAW_FAILURE_ACTIVITY               = "/app/mine/WithdrawFailureActivity";
    public static final String APP_MINE_WITHDRAWING_ACTIVITY                    = "/app/mine/WithdrawingActivity";
    public static final String APP_MINE_NO_BIND_ALIPAY_ACTIVITY                 = "/app/mine/NoBindAlipayActivity";
    public static final String APP_MINE_BIND_ALIPAY_ACTIVITY                    = "/app/mine/BindAlipayActivity";
    public static final String APP_MINE_ALIPAY_INFO_ACTIVITY                    = "/app/mine/AlipayInfoActivity";
    public static final String APP_MINE_BIND_ALIPAY_FAILURE_ACTIVITY            = "/app/mine/BindAlipayFailureActivity";
    public static final String APP_MINE_BIND_ALIPAY_SUBMIT_ACTIVITY             = "/app/mine/BindAlipaySubmitActivity";
    public static final String APP_MINE_MY_CARD_PACKAGE_ACTIVITY                = "/app/mine/MyCardPackageActivity";
    public static final String APP_MINE_CARD_PACKAGE_DETAIL_ACTIVITY            = "/app/mine/CardPackageDetailActivity";
    public static final String APP_MINE_MY_SHOPPING_ALLOWANCE_ACTIVITY          = "/app/mine/MyShoppingAllowanceActivity";
    public static final String APP_MINE_MY_ACTIVITY                             = "/app/mine/myActivity";                               //我的活动
    public static final String APP_MINE_MY_ACT_ACTIVITY                         = "/app/mine/MyActActivity";                               //我的活动
    public static final String APP_MINE_SHIPPING_ADDRESS_ACTIVITY               = "/app/mine/ShippingAddressActivity";
    public static final String APP_MINE_SHIPPING_ADDRESS_EDIT_ACTIVITY          = "/app/mine/ShippingAddressEditActivity";
    public static final String APP_MINE_SCANNING_RESULT_ACTIVITY                = "/app/mine/ScanningResultActivity";
    public static final String APP_MINE_MY_REMARK_GIFT_ACTIVITY                 = "/app/mine/APP_MINE_MY_REMARK_GIFT_ACTIVITY";
    public static final String APP_MINE_MY_WALLET_ACTIVITY                      = "/app/mine/APP_MINE_MY_WALLET_ACTIVITY";

    public static final String APP_STORE_LIST_NO_FILTER_STORE_LIST_ACTIVITY = "/app/storelist/nofilterlist/NoFilterStoreListActivity";
    public static final String APP_COMMON_SEARCH_RESULT_ACTIVITY            = "/app/common/SearchResultActivity";
    public static final String APP_COMMON_SEARCH_BEFORE_ACTIVITY            = "/app/common/SearchBeforeActivity";
    public static final String APP_HOTEL_DETAIL_ACTIVITY                    = "/app/storedetails/HotelDetailActivity";//店铺详情页
    public static final String STORE_ID                                     = "store_id";//店铺id
    public static final String APP_STORE_LIST_ACTIVITY                      = "/app/storelist/StoreListActivity";//店铺列表
    public static final String INDUSTRYCATE_ID                              = "industryCateId";//行业id
    public static final String INDUSTRYCATE_NAME                            = "industryCateName";//行业名称
    public static final String APP_STORE_DETAILS_SHOP_LIST_ACTIVITY         = "/app/storedetails/ShopListActivity";
    public static final String APP_TRAVEL_STORE_DETAIL_ACTIVITY = "/app/travelstoredetail/TravelStoreDetailActivity";//旅拍店铺
    public static final String APP_TRAVEL_STORE_LIST_ACTIVITY = "/app/channel/storelist/TravelStoreListActivity";//旅拍店铺列表页


    /*******************************旅拍***********************************/
    public static final String IS_SELECT_DISTINATION_CITY                     = "is_select_distination_city";
    public static final String APP_JIEHUN_DESTINATION_LIST_ACTIVITY = "/app/jiehun/destination/list/ui/activity/DestinationListActivity";
    public static final String APP_JIEHUN_STORE_DES_LIST_ACTIVITY = "/app/jiehun/destination/list/ui/activity/StoreDesListActivity";
    public static final String APP_JIEHUN_TRAVEL_ALBUM_ACTIVITY = "/app/jiehun/albumorcase/list/TravelAlbumActivity";


    public static final String SOCIALIZATION_SHARE    = "/socialization/ShareActivity";
    /**
     * 点评--上传订单
     */
    public static final String COMMENT_UPLOAD_ORDER   = "/comment/uploadOrder";
    public static final String PARAM_ORDER_REFUND_ID  = "order_refund_id";
    public static final String PARAM_ACTIVITY_TYPE    = "activity_type";
    public static final String PARAM_BUTTON_TITLE     = "button_title";
    public static final String PARAM_TYPE             = "type";
    /**
     * 我的评价
     */
    public static final String MY_COMMENT_LIST        = "/comment/list/view/impl/commentlistactivityimpl";
    /**
     * 初评详情
     */
    public static final String FIRST_COMMENT          = "/comment/detail/firstcommentactivity";
    /**
     * 评价有礼
     */

    public static final String COMMENT_AWARD_ACTIVITY = "/comment/award/commentawardactivity";

    /**
     * 相册列表
     *
     * @param path
     */
    public static final String COMMENT_PHOTO_LIST = "/album/photopickeractivity";

    /**
     * 评论详情
     */
    public static final String COMMENT_DETAIL      = "/detail/commentdetailviewimpl";
    /**
     * 评价有礼详情，不具有回复和点赞
     */
    public static final String SHOW_COMMENT_DETAIL = "/showdetail/showcommentdetailactivity";

    /**
     * 店铺点评列表
     */
    public static final String STORE_COMMENT_LIST = "/list/commentcommentstoreviewimpl";

    /**
     * 评价发布
     */
    public static final String COMMENT_PUBLISH = "/publish/commentpublishviewimpl";

    public static final String CHOOSE_STORE = "/bbs/edit/bbsEditChoiceStoreActivity";//选择商家

    /*******************************订单相关start***********************************/
    public static final String ORDER_SHOPPING_CART   = "/order/shoppingCart"; //购物车，无其他参数
    public static final String ORDER_ADDRESS_LIST    = "/order/addressList";  //地址列表，需要如下1个参数
    public static final String PARAM_SELECTED_ID     = "selected_id";         //已选中地址id，long类型
    public static final String ORDER_CHOOSE_COUPON   = "/order/chooseCoupon"; //选择购物券，如下2个参数
    public static final String PARAM_SELECTED_COUPON = "selected_coupon";     //已选中的现金券信息，CouponVo
    public static final String PARAM_ORDER_PARAM     = "order_param";         //请求现金券列表所需参数，HashMap<String, Object>
    public static final String ORDER_CONFIRM_ORDER   = "/order/confirmOrder"; //确认订单，如下4个参数
    public static final String PARAM_ORDER_TYPE      = "order_type";          //订单类型，int
    public static final String PARAM_GOODS_INFO      = "goods_info";          //购物车中物品信息，HashMap<Long,PostOrderVo.SkuInfo>
    public static final String PARAM_GOODS_INFO_STR  = "goods_info_str";      //购物车中物品信息，json str
    public static final String PARAM_POST_COUPON     = "post_coupon";         //请求现金券参数,PostCouponVo
    public static final String PARAM_POST_COUPON_STR = "post_coupon_str";     //请求现金券参数,json str
    public static final String PARAM_CART_IDS        = "cart_ids";            //购物车
    public static final String ORDER_EDIT_USER       = "/order/editUser";     //编辑使用者，如下2个参数
    public static final String PARAM_USER_NAME       = "user_name";           //使用者名字，String
    public static final String PARAM_USER_TEL        = "user_tel";            //使用者电话，String
    public static final String ORDER_PAY             = "/order/pay";          //付款页面，如下5个参数
    public static final String PARAM_PAY_ORDER_ID    = "pay_order_id";        //付款单id，long+PARAM_ORDER_NO+PARAM_PAY_WAY+PARAM_PAY_MONEY+PARAM_ORDER_TYPE
    public static final String PARAM_ORDER_STORE_ID  = "order_store_id";      //orderStoreId long
    public static final String PARAM_ORDER_PAY_URL   = "order_pay_url";       //支付地址
    public static final String ORDER_PAY_SUCCESS     = "/order/paySuccess";   //支付成功，如下4个参数+PARAM_ORDER_ID
    public static final String PARAM_ORDER_NO        = "order_no";            //订单号,String
    public static final String PARAM_PAY_WAY         = "pay_way";             //付款方式，String
    public static final String PARAM_PAY_MONEY       = "pay_money";           //付款金额，String
    public static final String ORDER_SUBMIT_FAILED   = "/order/submitFailed";//订单提交失败，如下1个参数
    public static final String PARAM_FAILED_REASON   = "failed_reason";       //失败原因，String
    public static final String ORDER_PAY_FAILED      = "/order/payFailed";    //付款失败,如下1个参数
    public static final String PARAM_ORDER_ID        = "order_id";            //订单id，long
    public static final String ORDER_LIST            = "/order/orderList";    //订单列表页,1个参数
    public static final String OTHER_ORDER_LIST      = "/voucher/order/getlist";//其他订单列表
    public static final String PARAM_INDEX           = "order_index";         //跳转到指定的页,int
    public static final String PARAM_CATE_ID         = "cate_id";         //跳转到指定的页,int
    public static final String ORDER_DETAIL          = "/order/orderDetail";  //订单详情页，1个String类型的orderId参数，可用上面定义的ORDER_ID
    public static final String ORDER_CHOICE_ACTIVITY = "/order/orderlist/OrderChoiceActivity";  //订单详情页，1个String类型的orderId参数，可用上面定义的ORDER_ID

    /*******************************订单相关end***********************************/

    /**
     * 登录相关
     */
    public static final String LOGIN_ACCOUNT_LOGIN = "/login/accountLoginActivity";  //账号登录
    public static final String LOGIN_PHONE_LOGIN   = "/login/phoneLoginActivity";  //手机登录
    public static final String LOGIN_BIND_PHONE    = "/login/bindPhoneActivity";  //绑定账号

    /**
     * 社区相关
     */
    public static final String BBS_DYNAMIC_VIEW_DYNAMICLISTACITIVITY = "/bbs/dynamic/view/DynamicActivity";
    public static final String BBS_DYNAMIC_VIEW_TUWENDETAILSACITIVITY = "/bbs/dynamic/view/TuWenDetailsActivity";
    public static final String BBS_SUBJECT_SUBJECTDETAIL        = "/subject/SubjectDetailActivity";
    public static final String BBS_SUBJECT_SUBJECTLIST          = "/subject/SubjectListActivity";
    public static final String BBS_EDIT_BBSQUESTIONEDITACTIVITY = "/edit/BbsQuestionEditActivity";
    public static final String BBS_EDIT_BBSEDITACTIVITY         = "/edit/BbsEditActivity";
    public static final String BBS_ZT_LIST                      = "/subject/ZTListActivity";
    public static final String BBS_TOPIC_DETAILS                = "/topic/TopicDetailsActivity";
    public static final String BBS_ASK_HOME                     = "/bbs/askHomeActivity";
    public static final String BBS_ASK_TOP_20                   = "/bbs/askChoicenessActivity";
    public static final String BBS_ASK_DETAILS                  = "/ask/AskDetailsActivity";
    public static final String BBS_COMMENT_LIST                 = "/topic/comment/CommentListActivity";
    public static final String BBS_COMMENT_DETAILS              = "/topic/comment/details/CommentDetailsActivity";
    public static final String BBS_ANSWER_DETAILS               = "/ask/answer/AnswerDetailsActivity";
    public static final String BBS_SECTION_LIST                 = "/bbs/fragment/section/WholeSectionActivity";
    public static final String BBS_SECTION_DETAIL               = "/bbs/BBSSectionDetailActivity";
    public static final String BBS_MINE                         = "/mine/BbsMineActivity";
    public static final String BBS_STRATEGY_DETAILS             = "/bbs/strategy/details/StrategyDetailsAcitivity";
    public static final String BBS_HUATI_DETAILS                = "/bbs/strategy/topiclist/ui/activity/TopicListActivity";
    public static final String USER_ID                          = "user_id";    //用户id
    public static final String VEST_LIST                        = "/vest/VestListActivity";
    public static final String KEY_SCANNING_RESULT              = "KEY_SCANNING_RESULT";
    public static final String COMMUNITY_ID                     = "community_id";
    public static final String FORUM_ID                         = "forum_id";
    public static final String ACTIVITY_ID                      = "activity_id";
    /*****************************攻略start******************************************/
    //攻略大全
    public static final String STRATEGY_LIST                    = "/strategy/list/StrategyListActivity";
    public static final String PROGRAMA_LIST                    = "/strategy/programa/ProgramaListActivity";
    public static final int    SHARE_FOR_RESULT_REQUEST_CODE    = 123;


    public static final String CONSUMER_VOUCHER = "/comment/ConsumerVoucherActivity";

    public static void startShare(String path, String title, String content, String webUrl, String imgUrl) {
        ARouter.getInstance().build(path)
                .withString(KEY_IMAGE_URL, imgUrl)
                .withString(KEY_TITLE, title)
                .withString(KEY_CONTENT, content)
                .withString(KEY_WEB_URL, webUrl)
                .navigation();
    }

    public static void startShareForResult(Activity mContext, String path, String title, String content, String webUrl, String imgUrl) {
        ARouter.getInstance().build(path)
                .withString(KEY_IMAGE_URL, imgUrl)
                .withString(KEY_TITLE, title)
                .withString(KEY_CONTENT, content)
                .withString(KEY_WEB_URL, webUrl)
                .navigation(mContext, SHARE_FOR_RESULT_REQUEST_CODE);
    }

    public static void startShare(String path, String title, String content, String webUrl, int imgRes) {
        ARouter.getInstance().build(path)
                .withInt(KEY_IMAGE_RES, imgRes)
                .withString(KEY_TITLE, title)
                .withString(KEY_CONTENT, content)
                .withString(KEY_WEB_URL, webUrl)
                .navigation();
    }
    //////////////////////////////////相册/////////////////////////////
    /*
     * 相册列表
     * */

    public static final String ALBUM_CASE_LIST   = "/app/albumorcase/list/AlbumCaseActivity";
    /*
     * 相册详情
     * */
    public static final String ALBUM_CASE_DETAIL = "/app/albumorcase/AlbumCaseDetialActivity";
    public static final String PARAM_ALBUM_ID = "album_id";//相册id
    public static final String PARAM_IS_IMAGE = "is_image";//大图预览 是否是图片
    public static final String PARAM_ALBUM_DETAIL_DATA = "param_album_detail_data";//相册详情数据
    public static final String PARAM_ALBUM_DEMAND_DATA = "param_album_demand_data";//相册详情客资数据
    public static final String PARAM_CURRENT_IMAGE_POSITION = "param_current_image_position";//跳大图当前position

    /**
     * 旅拍微电影
     */
    public static final String PRIVIEW_ACTIVITY = "/preview/PreviewActivity";
    public static final String FILM_DETAIL_ID = "film_detail_id";//微电影id
    public static final String FILM_STORE_ID = "film_store_id";//店铺id
    public static final String KEY_ATLAS_TYPE = "key_atlas_type";//旅拍图集tye
    //社区内容视频秀
    public static final String BBS_VIDEO_SHOW = "bbs_video_show";
    public static final String BBS_VIDEO_SHOW_COMMUNITY_ID = "community_id";


    /**
     * 消息中心相关
     */
    public static final String MESSAGE_MESSAGE_CENTER = "/message/messageCenterActivity";  //消息中心消息类别列表
    public static final String MESSAGE_MESSAGE_LIST   = "/message/messageListActivity";  //消息列表

    /**
     * 旅拍首页
     */
    public static final String TRAVEL_PHOTOGRAPHY_HOME = "/travelphotography/TravelPhotographyActivity.java";
    /*
     * 收藏列表
     * */
    public static final String COLLECTION_LIST         = "/app/collection/CollectionListActivity";


    /*******************************商品start****************************************/
    public static final String APP_NEW_GOODS_LIST   = "/app/goods/newGoodsList";  //旅拍商品列表
    public static final String APP_GOODS_LIST         = "/app/goods/goodsList";       // 商品列表,5个参数
    public static final String PARAM_IS_FORM_STORE    = "is_from_store";              //是否从店铺跳转的，bolean
    public static final String PARAM_PRODUCT_CATE_ID  = "product_cate_id";            //行业id,long
    public static final String PARAM_CATE_NAME        = "product_cate_name";          //行业名称，String
    public static final String PARAM_CATE_TYPE        = "cate_type";                  //类目类型，int
    public static final String PARAM_STORE_ID         = "store_id";                   //店铺id，long
    public static final String PARAM_IS_SHOW_FILTER   = "is_show_FILTER";             //是否显示筛选项
    public static final String PARAM_IS_SHOW_BOTTOM_FILTER   = "is_show_bottom_FILTER";             //是否显示筛选项
    public static final String PARAM_IS_NEED_REFRESH  = "is_need_refresh";             //是否显示筛选项
    public static final String PARAM_IS_TRAVEL_STORE  = "is_travel_store";             //是否显示店铺详情页旅拍reshuffle筛选项
    public static final String PARAM_IS_SHOW_TOP_LINE = "is_show_top_line";            //是否显示头部线
    public static final String PARAM_DESTINATION      = "param_destination";          //目的地
    public static final String APP_GOODS_DETAIL       = "/app/goods/detail";          //商品详情页，2个参数
    public static final String PARAM_GOODS_ID         = "goods_id";                   //商品id，long
    public static final String PARAM_SHOW_TYPE        = "show_type";                  //展示样式，int，0默认，1婚纱
    public static final String APP_COUPON_FIT_GOODS   = "/app/goods/couponFitGoods";  //现金券适应商品列表，1个参数
    public static final String PARAM_COUPON_ID        = "coupon_id";                  //现金券id，long
    public static final String APP_COUPON_DETAIL      = "/app/coupon/couponDetail";   //现金券详情页

    /*******************************商品start****************************************/

    public static final String APP_NAVIGATION = "/storedetail/NavigationActivity"; // 导航页面
    public static final String LAT            = "latitude";//纬度
    public static final String LNG            = "longitude";//经度
    public static final String ADDRESS        = "address";//地址
    public static final String COUPLE_ID      = "couponId";//现金券id



    public static void start(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void start(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static void start(String path, Context context, int requestCode) {
        ARouter.getInstance().build(path).navigation((Activity) context, requestCode);
    }

    public static Object start(Context context, String path, String key, Object value) {
        return ARouter.getInstance().build(path)
                .withObject(key, value)
                .navigation(context);
    }

    public static Object start(Context context, String path, String key, String value) {
        return ARouter.getInstance().build(path)
                .withString(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, String key, int value) {
        return ARouter.getInstance().build(path)
                .withInt(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, String key, long value) {
        return ARouter.getInstance().build(path)
                .withLong(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, String key, boolean value) {
        return ARouter.getInstance().build(path)
                .withBoolean(key, value)
                .navigation(context);
    }


    public static Object start(Context context, String path, Bundle bundle) {
        return ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(context);
    }


}
