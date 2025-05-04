package exception;

public class WindowInitializationException extends RuntimeException {
    public WindowInitializationException() {
        super("GLFW failed to initialize");
    }
}
