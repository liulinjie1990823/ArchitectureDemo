package com.llj.architecturedemo.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;

import com.llj.architecturedemo.MainMvcBaseFragment;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo;
import com.llj.lib.tracker.PageName;

import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/2
 */
@PageName(value = "DataFragment")
public class DataFragment extends MainMvcBaseFragment
        implements View.OnClickListener,
        android.content.DialogInterface.OnClickListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener,
        RatingBar.OnRatingBarChangeListener,
        SeekBar.OnSeekBarChangeListener,
        RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener,
        ExpandableListView.OnGroupClickListener,
        ExpandableListView.OnChildClickListener {

    public static DataFragment getInstance(BabyHomeModuleItemVo data, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putSerializable("data", data);
        DataFragment dataFragment = new DataFragment();
        dataFragment.setArguments(bundle);
        return dataFragment;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_data;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {

        super.initViews(savedInstanceState);
        getView().findViewById(R.id.tv_title).setOnClickListener(v -> {

        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
