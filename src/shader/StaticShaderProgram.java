package shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL44;

import java.nio.FloatBuffer;

import static java.lang.Math.sin;

public class StaticShaderProgram extends ShaderProgram {

    private static final String vertexFilePath = "src/shader/VertexShader.glsl";
    private static final String fragmentFilePath = "src/shader/FragmentShader.glsl";
    private int locationTransformationMatrix;

    public StaticShaderProgram() {
        super(vertexFilePath, fragmentFilePath);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public void start() {
        super.start();
        this.assignUniform();
    }

    private void assignUniform() {
        int uniformLocationRed = GL44.glGetUniformLocation(this.programID, "red");
        int uniformLocationGreen = GL44.glGetUniformLocation(this.programID, "green");
        int uniformLocationBlue = GL44.glGetUniformLocation(this.programID, "blue");
        GL44.glUniform1d(uniformLocationRed, 0);
        GL44.glUniform1d(uniformLocationGreen, (sin(GLFW.glfwGetTime()) / 2F ) + 0.5F);
        GL44.glUniform1d(uniformLocationBlue, 0);

        int uniformLocationTransform = GL44.glGetUniformLocation(this.programID, "transform");

        Matrix4f matrix4f = new Matrix4f();
        matrix4f.set(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        matrix4f.translate(new Vector3f(0, 0, 0), matrix4f);
        matrix4f.rotate((float) Math.toRadians(0), new Vector3f(1,0,0), matrix4f);
        matrix4f.rotate((float) Math.toRadians(0), new Vector3f(0,1,0), matrix4f);
        matrix4f.rotate((float) Math.toRadians(0), new Vector3f(0,0,1), matrix4f);
        matrix4f.scale(new Vector3f(1 , 1 , 1), matrix4f);

        float[] values = new float[16];
        matrix4f.get(values);
        GL44.glUniformMatrix4fv(uniformLocationTransform, false, values);
    }
}
