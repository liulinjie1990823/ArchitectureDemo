package com.llj.login.ui.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-20
 */
public class JobTestService extends JobService {
    public static final String TAG = JobTestService.class.getSimpleName();


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob");

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            scheduleRefresh();
//        }
//
//
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jobFinished(params, false);
            }
        }, 2000);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "onStopJob");
        return false;
    }

    private void scheduleRefresh() {
        JobScheduler mJobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);

        JobInfo.Builder mJobBuilder = new JobInfo.Builder(10086, new ComponentName(getPackageName(), JobTestService.class.getName()));

        /* For Android N and Upper Versions */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mJobBuilder.setMinimumLatency(5000);
        }
    }

}