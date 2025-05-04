package io;

import org.lwjgl.glfw.*;

public class InputHandler {
    public static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double mouseX;
    private double mouseY;
    private GLFWKeyCallback keyPress;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseClick;
    private GLFWWindowSizeCallback windowResizeCallback;
    public Window window;
    private long windowAddress;

    public InputHandler(Window window) {
        this.window = window;
        this.windowAddress = window.getWindowAddress();

        this.createCallbacks();
        this.bindCallbacks();
    }

    private void createCallbacks() {
        this.keyPress = new GLFWKeyCallback() {

            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        this.mouseMove = new GLFWCursorPosCallback() {

            @Override
            public void invoke(long window, double xpos, double ypos) {
                InputHandler.this.window.setMouseX((int) xpos);
                InputHandler.this.window.setMouseY((int) ypos);
            }
        };

        this.mouseClick = new GLFWMouseButtonCallback() {

            @Override
            public void invoke(long window, int button, int action, int mods) {
                mouseButtons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        this.windowResizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                InputHandler.this.window.setWidth(width);
                InputHandler.this.window.setHeight(height);
            }
        };
    }

    private void bindCallbacks() {
        GLFW.glfwSetKeyCallback(this.windowAddress, this.keyPress);
        GLFW.glfwSetCursorPosCallback(this.windowAddress, this.mouseMove);
        GLFW.glfwSetMouseButtonCallback(this.windowAddress, this.mouseClick);
        GLFW.glfwSetWindowSizeCallback(this.windowAddress, this.windowResizeCallback);
    }

    public void cleanUp() {
        this.keyPress.free();
        this.mouseMove.free();
        this.mouseClick.free();
        this.windowResizeCallback.free();
    }

    public double getMouseX() {
        return this.mouseX;
    }

    public double getMouseY() {
        return this.mouseY;
    }

    public boolean isKeyDown(int key) {
        return keys[key];
    }

    public boolean isMouseButtonDown(int button) {
        return mouseButtons[button];
    }
}
