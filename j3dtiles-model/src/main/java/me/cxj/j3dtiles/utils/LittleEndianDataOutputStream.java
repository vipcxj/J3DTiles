package me.cxj.j3dtiles.utils;

import java.io.*;

/**
 * A data output stream lets an application write primitive Java data
 * types to an output stream in a portable way. An application can
 * then use a data input stream to read the data back in.
 *
 * @author  unascribed
 * @see     java.io.DataInputStream
 * @since   JDK1.0
 */
public class LittleEndianDataOutputStream extends FilterOutputStream implements DataOutput {
    /**
     * Creates a {@code LittleEndianDataOutputStream} that wraps the given stream.
     *
     * @param out the stream to delegate to
     */
    public LittleEndianDataOutputStream(OutputStream out) {
        super(new DataOutputStream(out));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        // Override slow FilterOutputStream impl
        out.write(b, off, len);
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        ((DataOutputStream) out).writeBoolean(v);
    }

    @Override
    public void writeByte(int v) throws IOException {
        ((DataOutputStream) out).writeByte(v);
    }

    /**
     * @deprecated The semantics of {@code writeBytes(String s)} are considered dangerous. Please use
     *     {@link #writeUTF(String s)}, {@link #writeChars(String s)} or another write method instead.
     */
    @Deprecated
    @Override
    public void writeBytes(String s) throws IOException {
        ((DataOutputStream) out).writeBytes(s);
    }

    /**
     * Writes a char as specified by {@link DataOutputStream#writeChar(int)}, except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeChar(int v) throws IOException {
        writeShort(v);
    }

    /**
     * Writes a {@code String} as specified by {@link DataOutputStream#writeChars(String)}, except
     * each character is written using little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeChars(String s) throws IOException {
        for (int i = 0; i < s.length(); i++) {
            writeChar(s.charAt(i));
        }
    }

    /**
     * Writes a {@code double} as specified by {@link DataOutputStream#writeDouble(double)}, except
     * using little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    /**
     * Writes a {@code float} as specified by {@link DataOutputStream#writeFloat(float)}, except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    /**
     * Writes an {@code int} as specified by {@link DataOutputStream#writeInt(int)}, except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeInt(int v) throws IOException {
        out.write(0xFF & v);
        out.write(0xFF & (v >> 8));
        out.write(0xFF & (v >> 16));
        out.write(0xFF & (v >> 24));
    }

    /**
     * Writes a {@code long} as specified by {@link DataOutputStream#writeLong(long)}, except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeLong(long v) throws IOException {
        byte[] bytes = new byte[8];
        bytes[7] = (byte)(v >>> 56);
        bytes[6] = (byte)(v >>> 48);
        bytes[5] = (byte)(v >>> 40);
        bytes[4] = (byte)(v >>> 32);
        bytes[3] = (byte)(v >>> 24);
        bytes[2] = (byte)(v >>> 16);
        bytes[1] = (byte)(v >>>  8);
        bytes[0] = (byte)(v);
        write(bytes, 0, bytes.length);
    }

    /**
     * Writes a {@code short} as specified by {@link DataOutputStream#writeShort(int)}, except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeShort(int v) throws IOException {
        out.write(0xFF & v);
        out.write(0xFF & (v >> 8));
    }

    @Override
    public void writeUTF(String str) throws IOException {
        ((DataOutputStream) out).writeUTF(str);
    }

    // Overriding close() because FilterOutputStream's close() method pre-JDK8 has bad behavior:
    // it silently ignores any exception thrown by flush(). Instead, just close the delegate stream.
    // It should flush itself if necessary.
    @Override
    public void close() throws IOException {
        out.close();
    }
}