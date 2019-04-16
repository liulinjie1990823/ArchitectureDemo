# lib-record

### video

- setOutputFormat 视频输出格式
- setVideoFrameRate 视频帧率
- setVideoSize 视频宽高
- setVideoEncodingBitRate 视频码率
- setVideoEncoder 视频编码标准
```
public static final int DEFAULT = 0;
public static final int H263 = 1;
public static final int H264 = 2;
public static final int MPEG_4_SP = 3;
public static final int VP8 = 4;
public static final int HEVC = 5;
```



### audio


- setAudioEncodingBitRate 音频码率
- setAudioChannels 音频通道数。1.单声道 2.多声道
- setAudioSamplingRate 音频采样率
- setAudioEncoder  音频编码标准
```
public static final int DEFAULT = 0;
/** AMR (Narrowband) audio codec */
public static final int AMR_NB = 1;
/** AMR (Wideband) audio codec */
public static final int AMR_WB = 2;
/** AAC Low Complexity (AAC-LC) audio codec */
public static final int AAC = 3;
/** High Efficiency AAC (HE-AAC) audio codec */
public static final int HE_AAC = 4;
/** Enhanced Low Delay AAC (AAC-ELD) audio codec */
public static final int AAC_ELD = 5;
/** Ogg Vorbis audio codec */
public static final int VORBIS = 6;
```