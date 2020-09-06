package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.adapter.ListBasedAdapter;
import com.llj.adapter.util.ViewHolderHelper;
import com.llj.architecturedemo.MainMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.R2;
import com.llj.application.router.CRouter;
import com.llj.component.service.permission.PermissionManager;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe: author llj date 2019/2/21
 */
@Route(path = CRouter.APP_PERMISSION_ACTIVITY)
public class PermissionActivity extends MainMvcBaseActivity {

  @BindView(R2.id.recyclerView) RecyclerView mRecyclerView;

  @Override
  public int layoutId() {
    return R.layout.activity_permission;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    //MvAdapter adapter = new UniversalBind.Builder<>(mRecyclerView, new MvAdapter())
    //    .setLinearLayoutManager()
    //    .build().getAdapter();

    List<String> items = new ArrayList<>();
    items.add("日历");
    items.add("相机");
    items.add("存储空间");
    items.add("相机和存储");
    items.add("通讯录");
    items.add("位置信息");
    items.add("麦克风");
    items.add("电话");
    items.add("身体传感器");
    items.add("短信");
    //adapter.addAll(items);
  }

  @Override
  public void initData() {

  }

  public class MvAdapter extends ListBasedAdapter<String, ViewHolderHelper> {

    public MvAdapter() {
      super(null);
      addItemLayout(R.layout.item_permission);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderHelper viewHolder, @Nullable String item,
        int position) {
      TextView textView = viewHolder.getView(R.id.tv_permission);
      setText(textView, item);

      viewHolder.itemView.setOnClickListener(v -> {
        String textStr = getTextStr(textView);
        switch (textStr) {
          case "日历":
            PermissionManager.checkCalendar(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "相机":
            PermissionManager.checkCamera(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "存储空间":
            PermissionManager.checkStorage(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "相机和存储":
            PermissionManager.checkCameraAndStorage(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "通讯录":
            PermissionManager.checkContacts(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "位置信息":
            PermissionManager.checkLocation(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "麦克风":
            PermissionManager.checkRecordAudio(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "电话":
            PermissionManager.checkPhoneState(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "身体传感器":
            PermissionManager.checkSensors(mContext, permissions -> {
              showToast("成功");
            });
            break;
          case "短信":
            PermissionManager.checkSms(mContext, permissions -> {
              showToast("成功");
            });
            break;
          default:
            break;
        }
      });

    }
  }
}
