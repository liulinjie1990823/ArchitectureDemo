package com.llj.login.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import androidx.core.app.ActivityCompat;
import com.llj.login.LoginMvcBaseActivity;
import com.llj.login.R;
import com.llj.login.ui.service.JobIntentTestService;
import com.llj.login.ui.service.JobTestService;
import java.util.Collection;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-20
 */
public class BeaconDemoActivity extends LoginMvcBaseActivity implements BeaconConsumer {

    private BeaconManager beaconManager;

    @Override
    public int layoutId() {
        return R.layout.login_beacon_demo_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        checkPermission(this);

//        beaconManager = BeaconManager.getInstanceForApplication(this);
//        beaconManager.getBeaconParsers().add(new BeaconParser().
//                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
//        beaconManager.setBackgroundBetweenScanPeriod(1000);
//        beaconManager.setBackgroundScanPeriod(1000);
//        beaconManager.bind(this);


        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo.Builder builder = new JobInfo.Builder(10086, new ComponentName(getPackageName(), JobTestService.class.getName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setOverrideDeadline(0)
                    .build();
        } else {
            builder.setPeriodic(1000)
                    .build();
        }

        JobIntentTestService.start(this);

//        int schedule = mJobScheduler.schedule(builder.build());


        findViewById(R.id.cl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobScheduler.cancel(10086);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.removeAllRangeNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                  Log.i(mTagLog,
                      "The first beacon I see is about " + beacons.iterator().next().getDistance()
                          + " meters away.");
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region(getPageName(), null, null, null));
        } catch (RemoteException e) {
        }
    }

    private void checkPermission(Activity activity) {
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.ACCESS_COARSE_LOCATION");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
