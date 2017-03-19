package com.example.liyuanjing.object;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.liyuanjing.data.Constants;
import com.example.liyuanjing.data.VertexArray;
import com.example.liyuanjing.programs.ColorShaderProgram;

/**
 * Created by liyuanjing on 2015/6/27.
 */
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT=2;
    private static final int COLOR_COMPONENT_COUNT=3;
    private static final int STRIDE=(POSITION_COMPONENT_COUNT+COLOR_COMPONENT_COUNT)* Constants.BYTE_PER_FLOAT;

    private static final float[] VERTEX_DATA={
            0f,-0.4f,0f,0f,1f,
            0f,0.4f,1f,0f,0f
    };

    private VertexArray vertexArray;

    public Mallet(){
        this.vertexArray=new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorProgram){
        this.vertexArray.setVertextAttribPointer(0,colorProgram.getaPositionLocation(),POSITION_COMPONENT_COUNT,STRIDE);
        this.vertexArray.setVertextAttribPointer(POSITION_COMPONENT_COUNT,colorProgram.getaColorLocation(),COLOR_COMPONENT_COUNT,STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_LINES,0,2);
    }
}
