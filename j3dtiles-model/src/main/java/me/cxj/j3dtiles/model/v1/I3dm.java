package me.cxj.j3dtiles.model.v1;

import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.io.GltfModelReader;
import de.javagl.jgltf.model.io.GltfModelWriter;
import me.cxj.j3dtiles.utils.CommonUtils;
import me.cxj.j3dtiles.utils.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by vipcxj on 2018/11/7.
 */
public class I3dm {

    private I3dmHeader header;
    private I3dmFeatureTable featureTable;
    private BatchTable batchTable;
    private String gltfUri;
    private GltfModel gltfModel;

    public static I3dm read(InputStream is, JsonParser parser, boolean hasReadMagic, boolean hasReadVersion) throws IOException {
        I3dm instance = new I3dm();
        instance.header = I3dmHeader.read(is, hasReadMagic, hasReadVersion);
        instance.featureTable = I3dmFeatureTable.read(is, instance.header, parser);
        instance.batchTable = BatchTable.read(is, instance.header, parser, instance.featureTable.getInstancesLength());
        if (instance.header.getGltfFormat() == 0) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
                int read;
                byte[] buffer = new byte[64 * 1024];
                while ((read = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
                instance.gltfUri = new String(bos.toByteArray(), StandardCharsets.UTF_8).trim();
            }
        } else if (instance.header.getGltfFormat() == 1) {
            instance.gltfModel = new GltfModelReader().readWithoutReferences(is);
        } else {
            throw new IllegalArgumentException("Invalid gltfFormat value: " + instance.header.getGltfFormat() + ".");
        }
        return instance;
    }

    @SuppressWarnings("Duplicates")
    public void write(OutputStream os, JsonParser parser) throws IOException {
        byte[] featureTableBuffer = featureTable.createBuffer(header, parser);
        byte[] batchTableBuffer = batchTable.createBuffer(header, parser, featureTable.getInstancesLength());
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            if (header.getGltfFormat() == 0) {
                byte[] gltfBuffer = CommonUtils.createPaddingBytes(gltfUri.getBytes(StandardCharsets.UTF_8), 8, (byte) 0x20);
                header.setByteLength(featureTableBuffer.length + batchTableBuffer.length + gltfBuffer.length);
                header.write(os);
                os.write(featureTableBuffer);
                os.write(batchTableBuffer);
                os.write(gltfBuffer);
            } else if (header.getGltfFormat() == 1) {
                new GltfModelWriter().writeBinary(gltfModel, bos);
                byte[] gltfBuffer = bos.toByteArray();
                int gltfPadding = CommonUtils.calcPadding(gltfBuffer.length, 8);
                header.setByteLength(featureTableBuffer.length + batchTableBuffer.length + gltfBuffer.length + gltfPadding);
                header.write(os);
                os.write(featureTableBuffer);
                os.write(batchTableBuffer);
                os.write(gltfBuffer);
                for (int i = 0; i < gltfPadding; ++i) {
                    os.write(0);
                }
            } else {
                throw new IllegalArgumentException("Invalid gltfFormat value: " + header.getGltfFormat() + ".");
            }
        }
    }
}
