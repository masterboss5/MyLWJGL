package exception;

public class WindowNotCreatedException extends RuntimeException {
    public WindowNotCreatedException() {
        super("GLFW failed to create a window");
    }
}
