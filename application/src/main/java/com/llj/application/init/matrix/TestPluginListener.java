package com.llj.application.init.matrix;

import android.content.Context;
import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;
import java.lang.ref.SoftReference;

/**
 * describe
 *
 * @author
 * @date
 */
public class TestPluginListener extends DefaultPluginListener {

  public static final String TAG = "TestPluginListener";

  public SoftReference<Context> softReference;

  public TestPluginListener(Context context) {
    super(context);
    softReference = new SoftReference<>(context);
  }

  @Override
  public void onReportIssue(Issue issue) {
    super.onReportIssue(issue);
    MatrixLog.e(TAG, issue.toString());

    //IssuesMap.put(IssueFilter.getCurrentFilter(), issue);
    //jumpToIssueActivity();
  }

  //public void jumpToIssueActivity() {
  //  Context context = softReference.get();
  //  Intent intent = new Intent(context, IssuesListActivity.class);
  //
  //  if (context instanceof Application) {
  //    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  //  }
  //
  //  context.startActivity(intent);
  //
  //}

}