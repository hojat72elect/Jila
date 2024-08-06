package gdx.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Provides utility methods to copy streams.
 */
public final class StreamUtils {
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final byte[] EMPTY_BYTES = new byte[0];

    /**
     * Allocates a {@value #DEFAULT_BUFFER_SIZE} byte[] for use as a temporary buffer and calls
     * {@link #copyStream(InputStream, OutputStream, byte[])}.
     */
    public static void copyStream(java.io.InputStream input, java.io.OutputStream output) throws java.io.IOException {
        copyStream(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * Allocates a byte[] of the specified size for use as a temporary buffer and calls
     * {@link #copyStream(InputStream, OutputStream, byte[])}.
     */
    public static void copyStream(java.io.InputStream input, java.io.OutputStream output, int bufferSize) throws java.io.IOException {
        copyStream(input, output, new byte[bufferSize]);
    }

    /**
     * Copy the data from an {@link InputStream} to an {@link OutputStream}, using the specified byte[] as a temporary buffer. The
     * stream is not closed.
     */
    public static void copyStream(java.io.InputStream input, java.io.OutputStream output, byte[] buffer) throws java.io.IOException {
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    /**
     * Allocates a {@value #DEFAULT_BUFFER_SIZE} byte[] for use as a temporary buffer and calls
     * {@link #copyStream(InputStream, OutputStream, byte[])}.
     */
    public static void copyStream(java.io.InputStream input, java.nio.ByteBuffer output) throws java.io.IOException {
        copyStream(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * Allocates a byte[] of the specified size for use as a temporary buffer and calls
     * {@link #copyStream(InputStream, ByteBuffer, byte[])}.
     */
    public static void copyStream(java.io.InputStream input, java.nio.ByteBuffer output, int bufferSize) throws java.io.IOException {
        copyStream(input, output, new byte[bufferSize]);
    }

    /**
     * Copy the data from an {@link InputStream} to a {@link ByteBuffer}, using the specified byte[] as a temporary buffer. The
     * buffer's limit is increased by the number of bytes copied, the position is left unchanged. The stream is not closed.
     *
     * @param output Must be a direct Buffer with native byte order and the buffer MUST be large enough to hold all the bytes in
     *               the stream. No error checking is performed.
     * @return the number of bytes copied.
     */
    public static int copyStream(java.io.InputStream input, java.nio.ByteBuffer output, byte[] buffer) throws java.io.IOException {
        int startPosition = output.position(), total = 0, bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            BufferUtils.copy(buffer, 0, output, bytesRead);
            total += bytesRead;
            ((java.nio.Buffer) output).position(startPosition + total);
        }
        ((java.nio.Buffer) output).position(startPosition);
        return total;
    }

    /**
     * Copy the data from an {@link InputStream} to a byte array. The stream is not closed.
     */
    public static byte[] copyStreamToByteArray(java.io.InputStream input) throws java.io.IOException {
        return copyStreamToByteArray(input, input.available());
    }

    /**
     * Copy the data from an {@link InputStream} to a byte array. The stream is not closed.
     *
     * @param estimatedSize Used to allocate the output byte[] to possibly avoid an array copy.
     */
    public static byte[] copyStreamToByteArray(java.io.InputStream input, int estimatedSize) throws java.io.IOException {
        java.io.ByteArrayOutputStream baos = new gdx.utils.StreamUtils.OptimizedByteArrayOutputStream(Math.max(0, estimatedSize));
        copyStream(input, baos);
        return baos.toByteArray();
    }

    /**
     * Calls {@link #copyStreamToString(InputStream, int, String)} using the input's {@link InputStream#available() available}
     * size and the platform's default charset.
     */
    public static String copyStreamToString(java.io.InputStream input) throws java.io.IOException {
        return copyStreamToString(input, input.available(), null);
    }

    /**
     * Calls {@link #copyStreamToString(InputStream, int, String)} using the platform's default charset.
     */
    public static String copyStreamToString(java.io.InputStream input, int estimatedSize) throws java.io.IOException {
        return copyStreamToString(input, estimatedSize, null);
    }

    /**
     * Copy the data from an {@link InputStream} to a string using the specified charset.
     *
     * @param estimatedSize Used to allocate the output buffer to possibly avoid an array copy.
     * @param charset       May be null to use the platform's default charset.
     */
    public static String copyStreamToString(java.io.InputStream input, int estimatedSize, @Null String charset) throws java.io.IOException {
        java.io.InputStreamReader reader = charset == null ? new java.io.InputStreamReader(input) : new java.io.InputStreamReader(input, charset);
        java.io.StringWriter writer = new java.io.StringWriter(Math.max(0, estimatedSize));
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int charsRead;
        while ((charsRead = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, charsRead);
        }
        return writer.toString();
    }

    /**
     * Close and ignore all errors.
     */
    public static void closeQuietly(java.io.Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable ignored) {
            }
        }
    }

    /**
     * A ByteArrayOutputStream which avoids copying of the byte array if possible.
     */
    static public class OptimizedByteArrayOutputStream extends java.io.ByteArrayOutputStream {
        public OptimizedByteArrayOutputStream(int initialSize) {
            super(initialSize);
        }

        @Override
        public synchronized byte[] toByteArray() {
            if (count == buf.length) return buf;
            return super.toByteArray();
        }

        public byte[] getBuffer() {
            return buf;
        }
    }
}
