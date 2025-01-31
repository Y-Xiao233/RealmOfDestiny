package net.yxiao233.realmofdestiny.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyDownUtil {
    private static final long window = Minecraft.getInstance().getWindow().getWindow();
    public static boolean isShiftKeyDown(){
        boolean isLeftShiftDown = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
        boolean isRightShiftDown = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
        return isLeftShiftDown || isRightShiftDown;
    }

    public static boolean isCtrlDown(){
        boolean isLeftCtrlDown = GLFW.glfwGetKey(window,GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS;
        boolean isRightCtrlDown = GLFW.glfwGetKey(window,GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
        return isLeftCtrlDown || isRightCtrlDown;
    }

    public static boolean isAltDown(){
        boolean isLeftAltDown = GLFW.glfwGetKey(window,GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
        boolean isRightAltDown = GLFW.glfwGetKey(window,GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS;
        return isLeftAltDown || isRightAltDown;
    }
}
