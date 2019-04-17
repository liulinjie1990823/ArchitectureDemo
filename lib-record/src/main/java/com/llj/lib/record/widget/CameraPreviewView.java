package com.llj.lib.record.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.llj.lib.record.CameraHelper;
import com.llj.lib.record.ICameraHandler;
import com.llj.lib.record.R;
import com.llj.lib.record.RecordSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public class CameraPreviewView extends FrameLayout implements ICameraHandler {
    public static final String TAG = CameraPreviewView.class.getSimpleName();

    private LSurfaceView mCameraView;     //surfarce view
    private int          mWidth;     //指定视频尺寸宽
    private int          mHeight;    //指定视频尺寸高

    private Animation mFocusAnimation;      //对焦动画
    private ImageView mFocusAnimationView;  //对焦资源

    public CameraPreviewView(@NonNull Context context) {
        super(context);
        initFocusAnim(context);
    }

    public CameraPreviewView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFocusAnim(context);
    }

    public CameraPreviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFocusAnim(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraPreviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFocusAnim(context);
    }

    private void initFocusAnim(Context context) {
        // 添加对焦动画视图
        mFocusAnimationView = new ImageView(context);
        mFocusAnimationView.setVisibility(INVISIBLE);
        mFocusAnimationView.setImageResource(R.drawable.video_focus_icon);
        addView(mFocusAnimationView, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        // 定义对焦动画
        mFocusAnimation = AnimationUtils.loadAnimation(context, R.anim.focus_animation);
        mFocusAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFocusAnimationView.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    public void init(RecordSetting recordSetting) {
        mWidth = recordSetting.getPreviewWidth();
        mHeight = recordSetting.getPreviewHeight();

        if (mCameraView != null) {
            removeView(mCameraView);
            mCameraView = null;
        }

        //重新创建一个CameraView加进去
        mCameraView = new LSurfaceView(getContext());

        mCameraView.init(recordSetting);

        mCameraView.setOnTouchListener(mOnTouchListener);
        addView(mCameraView, 0);

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (camera() == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        //按照设置的视频尺寸比例设置显示部分层
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width / (1f * mWidth / mHeight));
        int wms = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int hms = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(wms, hms);
    }


    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        private float mOldDist = 1f;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (camera() == null) {
                return false;
            }
            if (event.getPointerCount() == 1) {     //单点触控
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        handleFocusMetering(camera(), event.getX(), event.getY());       //对焦
                        break;
                }
            } else {        //多点触控
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mOldDist = getFingerSpacing(event);          //记录down时的两指距离
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float newDist = getFingerSpacing(event);        //判断是放大还是缩小
                        if (newDist > mOldDist) {
                            handleZoom(true, camera());
                        } else if (newDist < mOldDist) {
                            handleZoom(false, camera());
                        }
                        mOldDist = newDist;
                        break;
                }
            }
            return true;
        }

        //获取2个手指的直线距离
        private float getFingerSpacing(MotionEvent event) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }

        //处理放大或缩小
        private void handleZoom(boolean isZoomIn, Camera camera) {
            Camera.Parameters params = camera.getParameters();
            if (params.isZoomSupported()) {
                int maxZoom = params.getMaxZoom();
                int zoom = params.getZoom();
                if (isZoomIn && zoom < maxZoom) {
                    zoom++;
                } else if (zoom > 0) {
                    zoom--;
                }
                params.setZoom(zoom);
                camera.setParameters(params);
            } else {
                Log.w(TAG, "zoom not supported");
            }
        }
    };


    //触摸对焦 测光
    private void handleFocusMetering(Camera camera, final float x, final float y) {
        Camera.Parameters params = camera.getParameters();
        Camera.Size previewSize = params.getPreviewSize();
        Rect focusRect = calculateTapArea(x, y, 1f, previewSize);
        Rect meteringRect = calculateTapArea(x, y, 1.5f, previewSize);

        camera.cancelAutoFocus();

        //触摸对焦
        if (params.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<>();
            focusAreas.add(new Camera.Area(focusRect, 800));
            params.setFocusAreas(focusAreas);
        } else {
            Log.w(TAG, "focus areas not supported");
        }

        //触摸测光
        if (params.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> meteringAreas = new ArrayList<>();
            meteringAreas.add(new Camera.Area(meteringRect, 800));
            params.setMeteringAreas(meteringAreas);
        } else {
            Log.w(TAG, "metering areas not supported");
        }

        final String currentFocusMode = params.getFocusMode();
        CameraHelper.setCameraFocusMode(camera, Camera.Parameters.FOCUS_MODE_MACRO);

        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                CameraHelper.setCameraFocusMode(camera, currentFocusMode);     //恢复之前的对焦模式
            }
        });

        //对焦动画
        mFocusAnimation.cancel();
        mFocusAnimationView.clearAnimation();
        int left = (int) (x - mFocusAnimationView.getWidth() / 2f);
        int top = (int) (y - mFocusAnimationView.getHeight() / 2f);
        int right = left + mFocusAnimationView.getWidth();
        int bottom = top + mFocusAnimationView.getHeight();
        mFocusAnimationView.layout(left, top, right, bottom);
        mFocusAnimationView.setVisibility(VISIBLE);
        mFocusAnimationView.startAnimation(mFocusAnimation);
    }

    //计算触碰区域
    private Rect calculateTapArea(float x, float y, float coefficient, Camera.Size previewSize) {
        float focusAreaSize = 300;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerX = (int) (x / previewSize.width - 1000);
        int centerY = (int) (y / previewSize.height - 1000);

        int left = clamp(centerX - areaSize / 2, -1000, 1000);
        int top = clamp(centerY - areaSize / 2, -1000, 1000);

        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);

        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }


    @Override
    public Camera camera() {
        return mCameraView.camera();
    }

    @Override
    public boolean isFlashOn() {
        return mCameraView.isFlashOn();
    }

    @Override
    public boolean flashOn() {
        return mCameraView.flashOn();
    }


    @Override
    public boolean flashOff() {
        return mCameraView.flashOff();
    }

    @Override
    public void switchCamera() {
        mCameraView.switchCamera();
    }

    @Override
    public void takePicture() {
        mCameraView.takePicture();
    }

    @Override
    public void takeContinuousShooting() {
        mCameraView.takeContinuousShooting();
    }

    @Override
    public boolean isRecording() {
        return mCameraView.isRecording();
    }

    @Override
    public boolean startRecorder() {
        return mCameraView.startRecorder();
    }

    @Override
    public boolean resumeRecording() {
        return mCameraView.resumeRecording();
    }

    @Override
    public boolean pauseRecording() {
        return mCameraView.pauseRecording();
    }

    @Override
    public boolean stopRecorder() {
        return mCameraView.stopRecorder();
    }

    @Override
    public boolean finishRecorder() {
        return mCameraView.finishRecorder();
    }

    @Override
    public void onStart() {
        mCameraView.onStart();
    }

    @Override
    public void onStop() {
        mCameraView.onStop();
    }

    @Override
    public void onDestroy() {
        mCameraView.onDestroy();
    }
}
