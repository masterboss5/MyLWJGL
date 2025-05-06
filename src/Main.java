import graphic.ModelLoader;
import graphic.ModelTexture;
import graphic.RawModel;
import graphic.TexturedModel;
import io.InputHandler;
import io.Window;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import render.RenderSystem;
import shader.ShaderProgram;
import shader.StaticShaderProgram;

public class Main implements Runnable {
    public static Main INSTANCE;
    public static InputHandler INPUT_HANDLER;
    public static RenderSystem RENDER_SYSTEM;
    public static ModelLoader MODEL_LOADER;
    public static ShaderProgram STATIC_SHADER_PROGRAM;
    private Window window;
    private long windowAddress;
    public Thread gameThread;

    RawModel myModel;
    ModelTexture modelTexture;
    TexturedModel texturedModel;

    float[] vertices = {
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
    };

    int[] indices = {
            0, 1, 3,
            3, 1, 2
    };

    float[] textureUV = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };


    public static void main(String[] args) {
        INSTANCE = new Main();
        INSTANCE.start();
    }

    private void start() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    private void render() {
        GL11.glClearColor(1, 0, 0, 1F);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        STATIC_SHADER_PROGRAM.start();
        RENDER_SYSTEM.renderModel(texturedModel);
        STATIC_SHADER_PROGRAM.stop();
        GLFW.glfwSwapBuffers(this.windowAddress);
    }

    private void update() {
        this.window.updateWindow();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }

    public void run() {
        init();

        while (!window.shouldClose()) {
            update();
            render();

            if (INPUT_HANDLER.isKeyDown(GLFW.GLFW_KEY_F11)) {
                this.window.setFullscreen(!this.window.isFullscreen());
                System.out.println("F11");
            };
        }

        cleanUp();
    }

    private void init() {
        this.window = new Window("First Game", 1920, 1080, 1F, 0F, 0F);
        this.window.init();
        this.windowAddress = this.window.getWindowAddress();

        MODEL_LOADER = new ModelLoader();
        RENDER_SYSTEM = new RenderSystem();
        INPUT_HANDLER = new InputHandler(this.window);
        STATIC_SHADER_PROGRAM = new StaticShaderProgram();

        myModel = MODEL_LOADER.loadToVAO(vertices, indices, textureUV);
        modelTexture = new ModelTexture(MODEL_LOADER.loadTexture("texture"));
        texturedModel = new TexturedModel(myModel, modelTexture);
    }

    private void cleanUp() {
        INPUT_HANDLER.cleanUp();
        MODEL_LOADER.cleanUp();
        STATIC_SHADER_PROGRAM.cleanUp();

        GLFW.glfwWindowShouldClose(this.window.getWindowAddress());
        GLFW.glfwDestroyWindow(this.window.getWindowAddress());
        GLFW.glfwTerminate();
    }
}
