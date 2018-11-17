package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.utils.LittleEndianDataInputStream;
import me.cxj.j3dtiles.utils.LittleEndianDataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by vipcxj on 2018/11/14.
 */
public class CompositeHeader extends Header {

    /**
     * cmpt
     */
    public static final byte magic[] = { 0x63, 0x6d, 0x70, 0x74 };
    /**
     * The number of tiles in the Composite.
     * uint32
     */
    private int tilesLength;

    @Override
    public byte[] getMagic() {
        return magic;
    }

    @Override
    public int getHeaderLength() {
        return 16;
    }

    public int getTilesLength() {
        return tilesLength;
    }

    public void setTilesLength(int tilesLength) {
        this.tilesLength = tilesLength;
    }

    public static CompositeHeader read(InputStream is, boolean hasReadMagic, boolean hasReadVersion) throws IOException {
        LittleEndianDataInputStream dataInputStream = new LittleEndianDataInputStream(is);
        CompositeHeader header = new CompositeHeader();
        if (hasReadVersion && !hasReadMagic) {
            throw new IllegalArgumentException("It is impossible to read version without reading magic.");
        }
        if (!hasReadMagic) {
            byte[] magic = new byte[4];
            dataInputStream.readFully(magic);
            if (!Arrays.equals(magic, header.getMagic())) {
                throw new IllegalArgumentException("Invalid input stream. Not a valid cmpt stream. The magic is wrong. Expect cmpt but " + new String(magic) + ".");
            }
        }
        if (!hasReadVersion) {
            int version = dataInputStream.readInt();
            if (version != header.getVersion()) {
                throw new IllegalArgumentException("Invalid input stream. Not a valid cmpt stream. Mismatch version. Expect 1 but " + version + ".");
            }
        }
        header.setByteLength(dataInputStream.readInt());
        header.setTilesLength(dataInputStream.readInt());
        return header;
    }

    public void write(OutputStream os) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(os);
        dos.write(magic);
        dos.writeInt(getVersion());
        dos.writeInt(getByteLength());
        dos.writeInt(getTilesLength());
    }
}
