package me.cxj.j3dtiles.model.v1;

import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.io.GltfModelReader;
import de.javagl.jgltf.model.io.GltfModelWriter;
import me.cxj.j3dtiles.utils.CommonUtils;
import me.cxj.j3dtiles.utils.JsonParser;

import java.io.*;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class B3dm {

    private B3dmHeader header;
    private B3dmFeatureTable featureTable;
    private BatchTable batchTable;
    private GltfModel gltf;

    public static B3dm read(InputStream is, JsonParser parser, boolean hasReadMagic, boolean hasReadVersion) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(is);
        B3dm instance = new B3dm();
        instance.header = B3dmHeader.read(is, hasReadMagic, hasReadVersion);
        instance.featureTable = B3dmFeatureTable.read(is, instance.header, parser);
        instance.batchTable = BatchTable.read(is, instance.header, parser, instance.featureTable.getBatchLength());
        instance.gltf = new GltfModelReader().readWithoutReferences(is);
        return instance;
    }

    @SuppressWarnings("Duplicates")
    public void write(OutputStream os, JsonParser parser) throws IOException {
        byte[] featureTableBuffer = featureTable.createBuffer(header, parser);
        byte[] batchTableBuffer = batchTable.createBuffer(header, parser, featureTable.getBatchLength());
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            new GltfModelWriter().writeBinary(gltf, bos);
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
        }
    }
}
