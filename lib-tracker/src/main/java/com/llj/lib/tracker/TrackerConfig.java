package com.llj.lib.tracker;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-06-19
 */
public class TrackerConfig {

  private                         String mAppName;
  private                         String mBaseUrl;
  private @Tracker.ReportStrategy int    mReportStrategy;

  public String getAppName() {
    return mAppName;
  }

  public String getBaseUrl() {
    return mBaseUrl;
  }

  public int getReportStrategy() {
    return mReportStrategy;
  }

  public static final class Builder {

    private String mAppName;
    private String mBaseUrl;
    private int    mReportStrategy;

    public Builder() {
    }


    public Builder AppName(String AppName) {
      this.mAppName = AppName;
      return this;
    }

    public Builder BaseUrl(String BaseUrl) {
      this.mBaseUrl = BaseUrl;
      return this;
    }

    public Builder ReportStrategy(int ReportStrategy) {
      this.mReportStrategy = ReportStrategy;
      return this;
    }

    public TrackerConfig build() {
      TrackerConfig trackerConfig = new TrackerConfig();
      trackerConfig.mAppName = this.mAppName;
      trackerConfig.mReportStrategy = this.mReportStrategy;
      trackerConfig.mBaseUrl = this.mBaseUrl;
      return trackerConfig;
    }
  }
}
