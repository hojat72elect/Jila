val lwjglVersion = "3.3.4"
val jomlVersion = "1.10.7"
val joml_primitivesVersion = "1.10.0"
val lwjgl3_awtVersion = "0.1.8"
val steamworks4jVersion = "1.9.0"
val steamworks4j_serverVersion = "1.9.0"
val lwjglNatives = "natives-windows"




plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // core LWJGL
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl", "lwjgl")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)

    // Assimp (well-known 3D model formats)
    implementation("org.lwjgl", "lwjgl-assimp")
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)

    // BGFX (Graphics API)
    implementation("org.lwjgl", "lwjgl-bgfx")
    runtimeOnly("org.lwjgl", "lwjgl-bgfx", classifier = lwjglNatives)

    // CUDA (parallel computing on GPUs)
    implementation("org.lwjgl", "lwjgl-cuda")

    // EGL
    implementation("org.lwjgl", "lwjgl-egl")

    // Fmod
    implementation("org.lwjgl", "lwjgl-fmod")

    // FreeType
    implementation("org.lwjgl", "lwjgl-freetype")
    runtimeOnly("org.lwjgl", "lwjgl-freetype", classifier = lwjglNatives)

    // GLFW
    implementation("org.lwjgl", "lwjgl-glfw")
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)

    // HarfBuzz
    implementation("org.lwjgl", "lwjgl-harfbuzz")
    runtimeOnly("org.lwjgl", "lwjgl-harfbuzz", classifier = lwjglNatives)

    // hwloc
    implementation("org.lwjgl", "lwjgl-hwloc")
    runtimeOnly("org.lwjgl", "lwjgl-hwloc", classifier = lwjglNatives)

    // AWT - JAWT
    implementation("org.lwjgl", "lwjgl-jawt")
    implementation("org.lwjglx", "lwjgl3-awt", lwjgl3_awtVersion)

    // Jemalloc
    implementation("org.lwjgl", "lwjgl-jemalloc")
    runtimeOnly("org.lwjgl", "lwjgl-jemalloc", classifier = lwjglNatives)

    // KTX (Khronos Texture)
    implementation("org.lwjgl", "lwjgl-ktx")
    runtimeOnly("org.lwjgl", "lwjgl-ktx", classifier = lwjglNatives)

    // libdivide
    implementation("org.lwjgl", "lwjgl-libdivide")
    runtimeOnly("org.lwjgl", "lwjgl-libdivide", classifier = lwjglNatives)

    // LLVM
    implementation("org.lwjgl", "lwjgl-llvm")
    runtimeOnly("org.lwjgl", "lwjgl-llvm", classifier = lwjglNatives)

    // LMDB
    implementation("org.lwjgl", "lwjgl-lmdb")
    runtimeOnly("org.lwjgl", "lwjgl-lmdb", classifier = lwjglNatives)

    // LZ4
    implementation("org.lwjgl", "lwjgl-lz4")
    runtimeOnly("org.lwjgl", "lwjgl-lz4", classifier = lwjglNatives)

    // meow_hash
    implementation("org.lwjgl", "lwjgl-meow")
    runtimeOnly("org.lwjgl", "lwjgl-meow", classifier = lwjglNatives)

    // Meshoptimizer
    implementation("org.lwjgl", "lwjgl-meshoptimizer")
    runtimeOnly("org.lwjgl", "lwjgl-meshoptimizer", classifier = lwjglNatives)

    //msdfgen
    implementation("org.lwjgl", "lwjgl-msdfgen")
    runtimeOnly("org.lwjgl", "lwjgl-msdfgen", classifier = lwjglNatives)

    // NanoVG
    implementation("org.lwjgl", "lwjgl-nanovg")
    runtimeOnly("org.lwjgl", "lwjgl-nanovg", classifier = lwjglNatives)

    // NFD
    implementation("org.lwjgl", "lwjgl-nfd")
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)

    // nuklear
    implementation("org.lwjgl", "lwjgl-nuklear")
    runtimeOnly("org.lwjgl", "lwjgl-nuklear", classifier = lwjglNatives)

    // ODBC
    implementation("org.lwjgl", "lwjgl-odbc")

    // OpenAL
    implementation("org.lwjgl", "lwjgl-openal")
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)

    // OpenCL
    implementation("org.lwjgl", "lwjgl-opencl")

    // OpenGL
    implementation("org.lwjgl", "lwjgl-opengl")
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)

    // OpenGLES
    implementation("org.lwjgl", "lwjgl-opengles")
    runtimeOnly("org.lwjgl", "lwjgl-opengles", classifier = lwjglNatives)

    // OpenVR
    implementation("org.lwjgl", "lwjgl-openvr")
    runtimeOnly("org.lwjgl", "lwjgl-openvr", classifier = lwjglNatives)

    // OpenXR
    implementation("org.lwjgl", "lwjgl-openxr")
    runtimeOnly("org.lwjgl", "lwjgl-openxr", classifier = lwjglNatives)

    // OPUS
    implementation("org.lwjgl", "lwjgl-opus")
    runtimeOnly("org.lwjgl", "lwjgl-opus", classifier = lwjglNatives)

    // OVR
    implementation("org.lwjgl", "lwjgl-ovr")
    runtimeOnly("org.lwjgl", "lwjgl-ovr", classifier = lwjglNatives)

    //PAR
    implementation("org.lwjgl", "lwjgl-par")
    runtimeOnly("org.lwjgl", "lwjgl-par", classifier = lwjglNatives)

    // Remotery
    implementation("org.lwjgl", "lwjgl-remotery")
    runtimeOnly("org.lwjgl", "lwjgl-remotery", classifier = lwjglNatives)

    // RPMalloc
    implementation("org.lwjgl", "lwjgl-rpmalloc")
    runtimeOnly("org.lwjgl", "lwjgl-rpmalloc", classifier = lwjglNatives)

    // ShaderC
    implementation("org.lwjgl", "lwjgl-shaderc")
    runtimeOnly("org.lwjgl", "lwjgl-shaderc", classifier = lwjglNatives)

    // SPVC
    implementation("org.lwjgl", "lwjgl-spvc")
    runtimeOnly("org.lwjgl", "lwjgl-spvc", classifier = lwjglNatives)

    // SSE
    implementation("org.lwjgl", "lwjgl-sse")
    runtimeOnly("org.lwjgl", "lwjgl-sse", classifier = lwjglNatives)

    // STB
    implementation("org.lwjgl", "lwjgl-stb")
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)

    // TinyEXR
    implementation("org.lwjgl", "lwjgl-tinyexr")
    runtimeOnly("org.lwjgl", "lwjgl-tinyexr", classifier = lwjglNatives)

    // TinyFD
    implementation("org.lwjgl", "lwjgl-tinyfd")
    runtimeOnly("org.lwjgl", "lwjgl-tinyfd", classifier = lwjglNatives)

    // Tootle
    implementation("org.lwjgl", "lwjgl-tootle")
    runtimeOnly("org.lwjgl", "lwjgl-tootle", classifier = lwjglNatives)

    // VMA
    implementation("org.lwjgl", "lwjgl-vma")
    runtimeOnly("org.lwjgl", "lwjgl-vma", classifier = lwjglNatives)

    // Vulkan
    implementation("org.lwjgl", "lwjgl-vulkan")

    // XXHash
    implementation("org.lwjgl", "lwjgl-xxhash")
    runtimeOnly("org.lwjgl", "lwjgl-xxhash", classifier = lwjglNatives)

    // Yoga
    implementation("org.lwjgl", "lwjgl-yoga")
    runtimeOnly("org.lwjgl", "lwjgl-yoga", classifier = lwjglNatives)

    // ZSTD
    implementation("org.lwjgl", "lwjgl-zstd")
    runtimeOnly("org.lwjgl", "lwjgl-zstd", classifier = lwjglNatives)

    // JOML
    implementation("org.joml", "joml", jomlVersion)
    implementation("org.joml", "joml-primitives", joml_primitivesVersion)

    // Steam
    implementation("com.code-disaster.steamworks4j", "steamworks4j", steamworks4jVersion)
    implementation("com.code-disaster.steamworks4j", "steamworks4j-server", steamworks4j_serverVersion)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}