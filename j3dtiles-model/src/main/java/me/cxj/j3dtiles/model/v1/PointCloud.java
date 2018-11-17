package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.utils.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by vipcxj on 2018/11/9.
 */
public class PointCloud implements TileModel {

    private PointCloudHeader header;
    private PntsFeatureTable featureTable;
    private BatchTable batchTable;

    public static PointCloud read(InputStream is, JsonParser parser, boolean hasReadMagic, boolean hasReadVersion) throws IOException {
        PointCloud instance = new PointCloud();
        instance.header = PointCloudHeader.read(is, hasReadMagic, hasReadVersion);
        instance.featureTable = PntsFeatureTable.read(is, instance.header, parser);
        instance.batchTable = BatchTable.read(is, instance.header, parser, instance.featureTable.getBatchLength() != null ? instance.featureTable.getBatchLength() : instance.featureTable.getPointsLength());
        return instance;
    }

    @Override
    public void write(OutputStream os, JsonParser parser) throws IOException {
        byte[] featureTableBuffer = featureTable.createBuffer(header, parser);
        byte[] batchTableBuffer = batchTable.createBuffer(header, parser, featureTable.getBatchLength() != null ? featureTable.getBatchLength() : featureTable.getPointsLength());
        header.setByteLength(header.getHeaderLength() + featureTableBuffer.length + batchTableBuffer.length);
        header.write(os);
        os.write(featureTableBuffer);
        os.write(batchTableBuffer);
    }

    public long calcSize(JsonParser parser) {
        return header.getHeaderLength() + featureTable.calcSize(header, parser) + batchTable.calcSize(header, parser, featureTable.getBatchLength() != null ? featureTable.getBatchLength() : featureTable.getPointsLength());
    }

    @Override
    public PointCloudHeader getHeader() {
        return header;
    }
}
