package io;

import exception.WindowInitializationException;
import exception.WindowNotCreatedException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int x;
    private int y;
    private int mouseX;
    private int mouseY;
    private int width;
    private int height;
    private String title;
    private long windowAddress;
    private float backgroundColorRed;
    private float backgroundColorGreen;
    private float backgroundColorBlue;
    public boolean viewportNeedsAdjusting;
    private GLFWVidMode videoMode;
    private boolean fullscreen = false;

    public Window(String title, int width, int height, float backgroundRed, float backgroundGreen, float backgroundBlue) {
        this.title = title;
        this.width = width;
        this.height = height;

        this.setBackgroundColor(backgroundRed, backgroundGreen, backgroundBlue);
    }

    public void init() throws WindowInitializationException, WindowNotCreatedException {
        if (!GLFW.glfwInit()) {
            throw new WindowInitializationException();
        }

        this.windowAddress = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);

        if (this.windowAddress == 0) {
            throw new WindowNotCreatedException();
        }

        this.videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        this.x = (videoMode.width() - this.width) / 2;
        this.y = (videoMode.height() - this.height) / 2;
        GLFW.glfwSetWindowPos(this.windowAddress, x, y);

        GLFW.glfwMakeContextCurrent(this.windowAddress);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GLFW.glfwShowWindow(this.windowAddress);
    }

    public void setFullscreen(boolean b) {
        this.fullscreen = b;
        this.viewportNeedsAdjusting = true;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void updateWindow() {
        if (this.viewportNeedsAdjusting) {
            this.viewportNeedsAdjusting = false;

            if (this.fullscreen) {
                GLFW.glfwSetWindowMonitor(this.windowAddress, GLFW.glfwGetPrimaryMonitor(), 0, 0, this.width, this.height, 180);
            } else {
                GLFW.glfwSetWindowMonitor(this.windowAddress, 0, this.x, this.y, this.width, this.height, 180);
            }

            GL11.glViewport(0, 0, this.width, this.height);
        }
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.windowAddress);
    }

    public long getWindowAddress() {
        return this.windowAddress;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
        GL11.glViewport(0, 0, this.getWidth(), this.getHeight());
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
        GL11.glViewport(0, 0, this.getWidth(), this.getHeight());
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        GLFW.glfwSetWindowTitle(this.windowAddress, this.title);
    }

    public float getBackgroundColorBlue() {
        return backgroundColorBlue;
    }

    public void setBackgroundColorBlue(float backgroundColorBlue) {
        this.backgroundColorBlue = backgroundColorBlue;
    }

    public float getBackgroundColorGreen() {
        return backgroundColorGreen;
    }

    public void setBackgroundColorGreen(float backgroundColorGreen) {
        this.backgroundColorGreen = backgroundColorGreen;
    }

    public float getBackgroundColorRed() {
        return backgroundColorRed;
    }

    public void setBackgroundColorRed(float backgroundColorRed) {
        this.backgroundColorRed = backgroundColorRed;
    }

    private void setBackgroundColor(float backgroundRed, float backgroundGreen, float backgroundBlue) {
        this.setBackgroundColorRed(backgroundRed);
        this.setBackgroundColorGreen(backgroundGreen);
        this.setBackgroundColorBlue(backgroundBlue);
    }

    public int getMouseX() {
        return mouseX;
    }

    protected void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    protected void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }
}
