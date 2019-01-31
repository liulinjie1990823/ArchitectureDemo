
# 代码规范

### 初始化位置

1. 静态final常量
2. 控件初始化，如BindView
3. ui相关helper
4. Present
5. Bundle中的参数
6. 类中使用的成员变量

> 1.相关功能代码写在一块，最好用方法提取，实在不行需要隔行并且加上注释


### ui相关

1. 细节使用ConstraintLayout来布局，整体使用LinearLayout来布局
2. 如果列表数据有很多，不要嵌套到ScrollView中，避免内存溢出


### 命名
> 变量，类名使用驼峰命名
> 成员变量m小写开头
> 静态变量s小写开头

1. activity  
ProductDetailActivity
2. 图片命名
   1. 普通图片  normal,press,select
    ic_xxxx_xxxx_normal.png  
    btn_xxxx_xxxx_normal.png
    bg_xxxx_xxxx_normal.png
    def_xxxx_xxxx_normal.png
   1. shape,selector图片
    shape_ic_xxxx_xxxx.xml
    selector_ic_xxxx_xxxx.xml
3. 布局  
activity: activity_main.xml
Dialog: dialog_main.xml
PopupWindow: popupWindow_main.xml
item: item_album.xml
include: include_album.xml
4. layout中的id命名  
控件缩写加功能