package com.example.liyuanjing.glsurfaceviewproject;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.liyuanjing.object.Mallet;
import com.example.liyuanjing.object.Table;
import com.example.liyuanjing.programs.ColorShaderProgram;
import com.example.liyuanjing.programs.TextureShaderProgram;
import com.example.liyuanjing.util.LoggerConfig;
import com.example.liyuanjing.util.MatrixHelper;
import com.example.liyuanjing.util.ShaderHelper;
import com.example.liyuanjing.util.TextResourceReader;
import com.example.liyuanjing.util.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by liyuanjing on 2015/6/19.
 */
public class LYJRenderer implements GLSurfaceView.Renderer {
    /*private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT=3;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE=(POSITION_COMPONENT_COUNT+COLOR_COMPONENT_COUNT)*BYTES_PER_FLOAT;
    private final FloatBuffer vertexData;
    private final Context context;
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX="u_Matrix";
    private final float[] projectionMatrix=new float[16];
    private final float[] modelMatrix=new float[16];
    private int uMatrixLocation;
    private int aColorLocation;
    private int aPositionLocation;
    private int program;*/

    private final Context context;

    private final float[] projectionMatrix=new float[16];
    private final float[] modelMatrix=new float[16];

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    public LYJRenderer(Context context) {
        this.context = context;
        /*float[] tableVerticesWithTriangles = {
                0f, 0f, 1f, 1f, 1f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,

                0f, -0.4f, 0f, 0f, 1f,
                0f, 0.4f, 1f, 0f, 0f
        };
        this.vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.vertexData.put(tableVerticesWithTriangles);*/
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        this.table=new Table();
        this.mallet=new Mallet();

        this.textureProgram=new TextureShaderProgram(context);
        this.colorProgram=new ColorShaderProgram(context);

        this.texture= TextureHelper.loadTexture(context,R.drawable.air_hockey_surface);
        /*String vertexShaderSource = TextResourceReader.readTextFileFromResource(this.context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(this.context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }
        GLES20.glUseProgram(program);
        this.aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        this.aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        this.uMatrixLocation=GLES20.glGetUniformLocation(program,U_MATRIX);
        this.vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, this.vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        this.vertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, this.vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);*/
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        /*final float aspectRatio=width>height?(float)width/(float)height:(float)height/(float)width;
        if(width>height){
            Matrix.orthoM(projectionMatrix,0,-aspectRatio,aspectRatio,-1f,1f,-1f,1f);
        }else{
            Matrix.orthoM(projectionMatrix,0,-1f,1f,-aspectRatio,aspectRatio,-1f,1f);
        }*/
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix,0,0f,0f,-2.5f);
        Matrix.rotateM(modelMatrix,0,-60f,1f,0f,0f);
        final float[] temp=new float[16];
        Matrix.multiplyMM(temp,0,projectionMatrix,0,modelMatrix,0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        this.textureProgram.useProgram();
        this.textureProgram.setUniforms(projectionMatrix,this.texture);
        this.table.bindData(this.textureProgram);
        this.table.draw();

        this.colorProgram.useProgram();
        this.colorProgram.setUniforms(projectionMatrix);
        this.mallet.bindData(this.colorProgram);
        this.mallet.draw();
        /*GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,projectionMatrix,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);*/
    }
}
