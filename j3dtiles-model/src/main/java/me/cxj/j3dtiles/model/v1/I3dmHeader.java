package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.utils.LittleEndianDataInputStream;
import me.cxj.j3dtiles.utils.LittleEndianDataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by vipcxj on 2018/11/7.
 */
public class I3dmHeader extends BatchHeader {

    public static final byte magic[] = { 0x69, 0x33, 0x64, 0x6d };
    private int featureTableJSONByteLength;
    private int featureTableBinaryByteLength;
    private int gltfFormat;

    public byte[] getMagic() {
        return magic;
    }

    public int getVersion() {
        return 1;
    }

    @Override
    public int getHeaderLength() {
        return 32;
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

    public int getGltfFormat() {
        return gltfFormat;
    }

    public void setGltfFormat(int gltfFormat) {
        this.gltfFormat = gltfFormat;
    }

    public static I3dmHeader read(InputStream is, boolean hasReadMagic, boolean hasReadVersion) throws IOException {
        LittleEndianDataInputStream dataInputStream = new LittleEndianDataInputStream(is);
        I3dmHeader header = new I3dmHeader();
        if (hasReadVersion && !hasReadMagic) {
            throw new IllegalArgumentException("It is impossible to read version without reading magic.");
        }
        if (!hasReadMagic) {
            byte[] magic = new byte[4];
            dataInputStream.readFully(magic);
            if (!Arrays.equals(magic, header.getMagic())) {
                throw new IllegalArgumentException("Invalid input stream. Not a valid i3dm stream. The magic is wrong. Expect i3dm but " + new String(magic) + ".");
            }
        }
        if (!hasReadVersion) {
            int version = dataInputStream.readInt();
            if (version != header.getVersion()) {
                throw new IllegalArgumentException("Invalid input stream. Not a valid i3dm stream. Mismatch version. Expect 1 but " + version + ".");
            }
        }
        header.setByteLength(dataInputStream.readInt());
        header.setFeatureTableJSONByteLength(dataInputStream.readInt());
        header.setFeatureTableBinaryByteLength(dataInputStream.readInt());
        header.setBatchTableJSONByteLength(dataInputStream.readInt());
        header.setBatchTableBinaryByteLength(dataInputStream.readInt());
        header.setGltfFormat(dataInputStream.readInt());
        return header;
    }

    public void write(OutputStream os) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(os);
        dos.write(magic);
        dos.writeInt(getVersion());
        dos.writeInt(getByteLength());
        dos.writeInt(getFeatureTableJSONByteLength());
        dos.writeInt(getFeatureTableBinaryByteLength());
        dos.writeInt(getBatchTableJSONByteLength());
        dos.writeInt(getBatchTableBinaryByteLength());
        dos.writeInt(gltfFormat);
    }
}
