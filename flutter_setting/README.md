# flutter_setting

A new Flutter module.

## Getting Started

For help getting started with Flutter, view our online
[documentation](https://flutter.dev/).


## 创建 flutter module
- 使用命令行
```
$ cd some/path/
$ flutter create -t module --org com.example my_flutter
```
- 使用android studio
File->NEW->NEW Flutter Project->Flutter Module

> 该module可以单独作为project工程运行，也可以作为主工程中的flutter module集成。（作为module需要在settings.gradle中配置编译路径）

### 设置编译flutter module
```
setBinding(new Binding([gradle: this]))
evaluate(new File(
        settingsDir,
        'flutter_setting/.android/include_flutter.groovy'
))
```
`

## 编译
会在flutter_setting module下自动生成app和Flutter子module.
```
public class MainActivity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);
  }
}
```
app中自动生成的是默认打开的页面：com.llj.flutter.flutter_setting.host.MainActivity。他继承FlutterActivity


Flutter中自动生成的是Flutter，FlutterFragment，GeneratedPluginRegistrant三个类。

## 运行

- 打开的activity

```
 Run #5: ActivityRecord{ca851a8 u0 com.llj.flutter.flutter_setting.host/.MainActivity t60}
```
通过命令行发现了如上的界面。



```
@NonNull
  public static FlutterFragment createFragment(String initialRoute) {
    final FlutterFragment fragment = new FlutterFragment();
    final Bundle args = new Bundle();
    args.putString(FlutterFragment.ARG_ROUTE, initialRoute);
    fragment.setArguments(args);
    return fragment;
  }
```


