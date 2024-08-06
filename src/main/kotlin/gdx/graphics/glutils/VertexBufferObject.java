package gdx.graphics.glutils;

import gdx.Gdx;
import gdx.graphics.GL20;
import gdx.graphics.VertexAttribute;
import gdx.graphics.VertexAttributes;
import gdx.utils.BufferUtils;
import gdx.utils.GdxRuntimeException;

import java.nio.Buffer;

/**
 * <p>
 * A {@link VertexData} implementation based on OpenGL vertex buffer objects.
 * <p>
 * If the OpenGL ES context was lost you can call {@link #invalidate()} to recreate a new OpenGL vertex buffer object.
 * <p>
 * The data is bound via glVertexAttribPointer() according to the attribute aliases specified via {@link VertexAttributes} in the
 * constructor.
 * <p>
 * VertexBufferObjects must be disposed via the {@link #dispose()} method when no longer needed
 */
public class VertexBufferObject implements VertexData {
    boolean isDirty = false;
    boolean isBound = false;
    private VertexAttributes attributes;
    private java.nio.FloatBuffer buffer;
    private java.nio.ByteBuffer byteBuffer;
    private boolean ownsBuffer;
    private int bufferHandle;
    private int usage;

    /**
     * Constructs a new interleaved VertexBufferObject.
     *
     * @param isStatic    whether the vertex data is static.
     * @param numVertices the maximum number of vertices
     * @param attributes  the {@link VertexAttribute}s.
     */
    public VertexBufferObject(boolean isStatic, int numVertices, VertexAttribute... attributes) {
        this(isStatic, numVertices, new VertexAttributes(attributes));
    }

    /**
     * Constructs a new interleaved VertexBufferObject.
     *
     * @param isStatic    whether the vertex data is static.
     * @param numVertices the maximum number of vertices
     * @param attributes  the {@link VertexAttributes}.
     */
    public VertexBufferObject(boolean isStatic, int numVertices, VertexAttributes attributes) {
        bufferHandle = Gdx.gl20.glGenBuffer();

        java.nio.ByteBuffer data = BufferUtils.newUnsafeByteBuffer(attributes.vertexSize * numVertices);
        ((java.nio.Buffer) data).limit(0);
        setBuffer(data, true, attributes);
        setUsage(isStatic ? GL20.GL_STATIC_DRAW : GL20.GL_DYNAMIC_DRAW);
    }

    protected VertexBufferObject(int usage, java.nio.ByteBuffer data, boolean ownsBuffer, VertexAttributes attributes) {
        bufferHandle = Gdx.gl20.glGenBuffer();

        setBuffer(data, ownsBuffer, attributes);
        setUsage(usage);
    }

    @Override
    public VertexAttributes getAttributes() {
        return attributes;
    }

    @Override
    public int getNumVertices() {
        return buffer.limit() * 4 / attributes.vertexSize;
    }

    @Override
    public int getNumMaxVertices() {
        return byteBuffer.capacity() / attributes.vertexSize;
    }

    /**
     * @deprecated use {@link #getBuffer(boolean)} instead
     */
    @Override
    @Deprecated
    public java.nio.FloatBuffer getBuffer() {
        isDirty = true;
        return buffer;
    }

    @Override
    public java.nio.FloatBuffer getBuffer(boolean forWriting) {
        isDirty |= forWriting;
        return buffer;
    }

    /**
     * Low level method to reset the buffer and attributes to the specified values. Use with care!
     *
     * @param data
     * @param ownsBuffer
     * @param value
     */
    protected void setBuffer(java.nio.Buffer data, boolean ownsBuffer, VertexAttributes value) {
        if (isBound) throw new GdxRuntimeException("Cannot change attributes while VBO is bound");
        if (this.ownsBuffer && byteBuffer != null) BufferUtils.disposeUnsafeByteBuffer(byteBuffer);
        attributes = value;
        if (data instanceof java.nio.ByteBuffer)
            byteBuffer = (java.nio.ByteBuffer) data;
        else
            throw new GdxRuntimeException("Only ByteBuffer is currently supported");
        this.ownsBuffer = ownsBuffer;

        final int l = byteBuffer.limit();
        ((java.nio.Buffer) byteBuffer).limit(byteBuffer.capacity());
        buffer = byteBuffer.asFloatBuffer();
        ((java.nio.Buffer) byteBuffer).limit(l);
        ((java.nio.Buffer) buffer).limit(l / 4);
    }

    private void bufferChanged() {
        if (isBound) {
            Gdx.gl20.glBufferData(GL20.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
            isDirty = false;
        }
    }

    @Override
    public void setVertices(float[] vertices, int offset, int count) {
        isDirty = true;
        BufferUtils.copy(vertices, byteBuffer, count, offset);
        ((java.nio.Buffer) buffer).position(0);
        ((java.nio.Buffer) buffer).limit(count);
        bufferChanged();
    }

    @Override
    public void updateVertices(int targetOffset, float[] vertices, int sourceOffset, int count) {
        isDirty = true;
        final int pos = byteBuffer.position();
        ((java.nio.Buffer) byteBuffer).position(targetOffset * 4);
        BufferUtils.copy(vertices, sourceOffset, count, byteBuffer);
        ((java.nio.Buffer) byteBuffer).position(pos);
        ((java.nio.Buffer) buffer).position(0);
        bufferChanged();
    }

    /**
     * @return The GL enum used in the call to {@link GL20#glBufferData(int, int, Buffer, int)}, e.g. GL_STATIC_DRAW or
     * GL_DYNAMIC_DRAW
     */
    protected int getUsage() {
        return usage;
    }

    /**
     * Set the GL enum used in the call to {@link GL20#glBufferData(int, int, Buffer, int)}, can only be called when the
     * VBO is not bound.
     */
    protected void setUsage(int value) {
        if (isBound) throw new GdxRuntimeException("Cannot change usage while VBO is bound");
        usage = value;
    }

    /**
     * Binds this VertexBufferObject for rendering via glDrawArrays or glDrawElements
     *
     * @param shader the shader
     */
    @Override
    public void bind(ShaderProgram shader) {
        bind(shader, null);
    }

    @Override
    public void bind(ShaderProgram shader, int[] locations) {
        final GL20 gl = Gdx.gl20;

        gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, bufferHandle);
        if (isDirty) {
            ((java.nio.Buffer) byteBuffer).limit(buffer.limit() * 4);
            gl.glBufferData(GL20.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
            isDirty = false;
        }

        final int numAttributes = attributes.size();
        if (locations == null) {
            for (int i = 0; i < numAttributes; i++) {
                final VertexAttribute attribute = attributes.get(i);
                final int location = shader.getAttributeLocation(attribute.alias);
                if (location < 0) continue;
                shader.enableVertexAttribute(location);

                shader.setVertexAttribute(location, attribute.numComponents, attribute.type, attribute.normalized,
                        attributes.vertexSize, attribute.offset);
            }

        } else {
            for (int i = 0; i < numAttributes; i++) {
                final VertexAttribute attribute = attributes.get(i);
                final int location = locations[i];
                if (location < 0) continue;
                shader.enableVertexAttribute(location);

                shader.setVertexAttribute(location, attribute.numComponents, attribute.type, attribute.normalized,
                        attributes.vertexSize, attribute.offset);
            }
        }
        isBound = true;
    }

    /**
     * Unbinds this VertexBufferObject.
     *
     * @param shader the shader
     */
    @Override
    public void unbind(final ShaderProgram shader) {
        unbind(shader, null);
    }

    @Override
    public void unbind(final ShaderProgram shader, final int[] locations) {
        final GL20 gl = Gdx.gl20;
        final int numAttributes = attributes.size();
        if (locations == null) {
            for (int i = 0; i < numAttributes; i++) {
                shader.disableVertexAttribute(attributes.get(i).alias);
            }
        } else {
            for (int i = 0; i < numAttributes; i++) {
                final int location = locations[i];
                if (location >= 0) shader.disableVertexAttribute(location);
            }
        }
        gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
        isBound = false;
    }

    /**
     * Invalidates the VertexBufferObject so a new OpenGL buffer handle is created. Use this in case of a context loss.
     */
    @Override
    public void invalidate() {
        bufferHandle = Gdx.gl20.glGenBuffer();
        isDirty = true;
    }

    /**
     * Disposes of all resources this VertexBufferObject uses.
     */
    @Override
    public void dispose() {
        GL20 gl = Gdx.gl20;
        gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
        gl.glDeleteBuffer(bufferHandle);
        bufferHandle = 0;
        if (ownsBuffer) BufferUtils.disposeUnsafeByteBuffer(byteBuffer);
    }
}
