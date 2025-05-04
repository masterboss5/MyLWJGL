package shader;

public class StaticShaderProgram extends ShaderProgram {

    private static final String vertexFilePath = "src/shader/VertexShader.glsl";
    private static final String fragmentFilePath = "src/shader/FragmentShader.glsl";

    public StaticShaderProgram() {
        super(vertexFilePath, fragmentFilePath);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureUV");
    }
}
