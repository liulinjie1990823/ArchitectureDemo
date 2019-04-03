package com.llj.lib.opengl.encoder;

import android.content.Context;

import com.llj.lib.opengl.render.LogoRenderImpl;

public class WlMediaEncodec extends WlBaseMediaEncoder{

    private LogoRenderImpl mLogoRenderImpl;

    public WlMediaEncodec(Context context, int textureId) {
        super(context);
        mLogoRenderImpl = new LogoRenderImpl(context, textureId);
        setRender(mLogoRenderImpl);
        setmRenderMode(WlBaseMediaEncoder.RENDERMODE_CONTINUOUSLY);
    }
}
