package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.utils.LittleEndianDataInputStream;
import me.cxj.j3dtiles.utils.LittleEndianDataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class B3dmHeader extends BatchHeader {

    private final byte magic[] = { 0x62, 0x33, 0x64, 0x6d };
    private int byteLength;
    private int featureTableJSONByteLength;
    private int featureTableBinaryByteLength;

    public byte[] getMagic() {
        return magic;
    }

    public int getVersion() {
        return 1;
    }

    public int getByteLength() {
        return byteLength;
    }

    public void setByteLength(int byteLength) {
        this.byteLength = byteLength;
    }

    public int getFeatureTableJSONByteLength() {
        return featureTableJSONByteLength;
    }

    public void setFeatureTableJSONByteLength(int featureTableJSONByteLength) {
        this.featureTableJSONByteLength = featureTableJSONByteLength;
    }

    public int getFeatureTableBinaryByteLength() {
        return featureTableBinaryByteLength;
    }

    public void setFeatureTableBinaryByteLength(int featureTableBinaryByteLength) {
        this.featureTableBinaryByteLength = featureTableBinaryByteLength;
    }

    public static B3dmHeader read(InputStream is, boolean hasReadMagic, boolean hasReadVersion) throws IOException {
        LittleEndianDataInputStream dataInputStream = new LittleEndianDataInputStream(is);
        B3dmHeader header = new B3dmHeader();
        if (hasReadVersion && !hasReadMagic) {
            throw new IllegalArgumentException("It is impossible to read version without reading magic.");
        }
        if (!hasReadMagic) {
            byte[] magic = new byte[4];
            dataInputStream.readFully(magic);
            if (!Arrays.equals(magic, header.getMagic())) {
                throw new IllegalArgumentException("Invalid input stream. Not a valid b2dm stream. The magic is wrong. Expect b3dm but " + new String(magic) + ".");
            }
        }
        if (!hasReadVersion) {
            int version = dataInputStream.readInt();
            if (version != header.getVersion()) {
                throw new IllegalArgumentException("Invalid input stream. Not a valid b2dm stream. Mismatch version. Expect 1 but " + version + ".");
            }
        }
        header.setByteLength(dataInputStream.readInt());
        header.setFeatureTableJSONByteLength(dataInputStream.readInt());
        header.setFeatureTableBinaryByteLength(dataInputStream.readInt());
        header.setBatchTableJSONByteLength(dataInputStream.readInt());
        header.setBatchTableBinaryByteLength(dataInputStream.readInt());
        return header;
    }

    @SuppressWarnings("Duplicates")
    public void write(OutputStream os) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(os);
        dos.write(magic);
        dos.writeInt(getVersion());
        dos.writeInt(byteLength);
        dos.writeInt(featureTableJSONByteLength);
        dos.writeInt(featureTableBinaryByteLength);
        dos.writeInt(getBatchTableJSONByteLength());
        dos.writeInt(getBatchTableBinaryByteLength());
    }
}
