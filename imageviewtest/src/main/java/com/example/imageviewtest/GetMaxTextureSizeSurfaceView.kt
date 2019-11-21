package com.example.imageviewtest

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.SurfaceHolder
import java.nio.IntBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *  해당 디바이스의 Texture 크기를 구하는 SurfaceView
 * */
class GetMaxTextureSizeSurfaceView(context: Context?, var callback: (String) -> Int) :
    GLSurfaceView(context),
    SurfaceHolder.Callback {

    public var mRenderer: ClearRenderer = ClearRenderer()

    init {
        setRenderer(mRenderer)
        callback.invoke(mRenderer.mTextureLimit.toString())
    }
}

class ClearRenderer : GLSurfaceView.Renderer {
    public var mTextureLimit: Int = 0

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        val textureLimit = IntBuffer.allocate(1)
        gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, textureLimit)
        Log.d("jhlee", "onSurfaceCreated $textureLimit")
        Log.d("jhlee", "onSurfaceCreated ${textureLimit.get(0)}")
        mTextureLimit = textureLimit.get(0)
    }

    override fun onSurfaceChanged(gl: GL10, w: Int, h: Int) {
        Log.d("jhlee", "onSurfaceChanged")
        gl.glViewport(0, 0, w, h)
    }

    override fun onDrawFrame(gl: GL10) {
//        Log.d("jhlee", "onDrawFrame")
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
    }
}