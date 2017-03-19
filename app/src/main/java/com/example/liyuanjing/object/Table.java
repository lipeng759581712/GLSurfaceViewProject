package com.example.liyuanjing.object;

import android.opengl.GLES20;

import com.example.liyuanjing.data.Constants;
import com.example.liyuanjing.data.VertexArray;
import com.example.liyuanjing.programs.TextureShaderProgram;

/**
 * Created by liyuanjing on 2015/6/27.
 */
public class Table {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COUNT) * Constants.BYTE_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };
    private  final VertexArray vertexArray;

    public Table(){
        this.vertexArray=new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram){
        this.vertexArray.setVertextAttribPointer(0,textureProgram.getaPositionLocation(),POSITION_COMPONENT_COUNT,STRIDE);
        this.vertexArray.setVertextAttribPointer(POSITION_COMPONENT_COUNT,textureProgram.getaTextureCoordinatesLocation(),TEXTURE_COORDINATES_COUNT,STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6);
    }
}
