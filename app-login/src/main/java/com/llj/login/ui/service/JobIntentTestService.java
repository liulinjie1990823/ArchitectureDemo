package com.llj.login.ui.service;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import android.util.Log;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-20
 */
public class JobIntentTestService extends JobIntentService {
    public static final String TAG = JobIntentTestService.class.getSimpleName();

    public static void start(Context context) {
        Intent intent = new Intent(context, JobIntentTestService.class);
        enqueueWork(context, JobIntentTestService.class, 10086, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e(TAG, "onStartOnHandleWork");
    }
}