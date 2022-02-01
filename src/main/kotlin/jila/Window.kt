package jila

import org.lwjgl.Version
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.system.MemoryUtil.NULL

/**
 * Created by Hojat Ghasemi on 2022-01-31.
 *The author could be contacted at "https://twitter.com/hojat_93"
 */
object Window {
    private var width = 1920
    private var height = 1080
    private var title = "Mario"
    private var glfwWindow: Long = 0

    fun run() {
        println("Hello LWJGL ${Version.getVersion()}!")
        init()
        loop()
    }

    private fun loop() {

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents()
            glClearColor(1f, 0f, 0f, 1f)
        }

    }

    private fun init() {
        // Setup error callback.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW
        if (glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW.")
        }

        // Configure GLFW
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE)

        // Create the window
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL)
        if (glfwWindow == NULL) {
            throw java.lang.IllegalStateException("Failed to create the GLFW window.")
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow)

        // Enable v-sync (there will be no delay between frames)
        glfwSwapInterval(1)

        // Show the window
        glfwShowWindow(glfwWindow)

        /*
        * This line is critical for LWJGL's interoperation with GLFW's OpenGL context, or any context that is
        * managed externally. LWJGL detects the context that is current in the current thread, creates the
        * GLCapabilities instance and makes the OpenGL bindings available for use.*/
        GL.createCapabilities()

    }
}