package me.cxj.j3dtiles.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vipcxj on 2018/11/19.
 */
public class IOUtils {

    public static void readFully(InputStream is, byte[] buffer) throws IOException {
        readFully(is, buffer, 0, buffer.length);
    }

    public static void readFully(InputStream is, byte[] buffer, int off, int len) throws IOException {
        if (len < 0)
            throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = is.read(buffer, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }

    public static int tryReadFully(InputStream is, byte[] buffer) throws IOException {
        return tryReadFully(is, buffer, 0, buffer.length);
    }

    public static int tryReadFully(InputStream is, byte[] buffer, int off, int len) throws IOException {
        if (len < 0)
            throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = is.read(buffer, off + n, len - n);
            if (count < 0)
                return n;
            n += count;
        }
        return len;
    }

    public static byte[] toByteArray(InputStream is, boolean close) {
        try (InputStream theIs = close ? is : new FakeCloseInputStream(is)){
            List<byte[]> byteArrayList = new ArrayList<>();
            int lastRead = 2048;
            do {
                byte[] buffer = new byte[2048];
                int read = tryReadFully(theIs, buffer);
                if (read > 0) {
                    byteArrayList.add(buffer);
                    lastRead = read;
                    if (read < 2048) {
                        break;
                    }
                } else {
                    break;
                }
            } while (true);
            int size = byteArrayList.stream().mapToInt(ba -> ba.length).sum() - 2048 + lastRead;
            byte[] out = new byte[size];
            int nByteArray = byteArrayList.size();
            int offset = 0;
            for (int i = 0; i < nByteArray; ++i) {
                if (i < nByteArray - 1) {
                    System.arraycopy(byteArrayList.get(i), 0, out, offset, 2048);
                    offset += 2048;
                } else {
                    System.arraycopy(byteArrayList.get(i), 0, out, offset, lastRead);
                }
            }
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class FakeCloseInputStream extends InputStream {

        private final InputStream is;

        public FakeCloseInputStream(InputStream is) {
            this.is = is;
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return is.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return is.read(b, off, len);
        }

        @Override
        public long skip(long n) throws IOException {
            return is.skip(n);
        }

        @Override
        public int available() throws IOException {
            return is.available();
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public void mark(int readlimit) {
            is.mark(readlimit);
        }

        @Override
        public void reset() throws IOException {
            is.reset();
        }

        @Override
        public boolean markSupported() {
            return is.markSupported();
        }
    }
}
