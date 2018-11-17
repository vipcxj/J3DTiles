package me.cxj.j3dtiles.utils;

import me.cxj.j3dtiles.impl.v1.ComponentType;
import me.cxj.j3dtiles.impl.v1.ContainerType;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static me.cxj.j3dtiles.utils.CommonUtils.addReference;

/**
 * Created by vipcxj on 2018/11/14.
 */
public class BinaryBodySizeHelper {

    private final Map<String, Object> jsonHeader;
    private int size = 0;

    public BinaryBodySizeHelper(Map<String, Object> jsonHeader) {
        this.jsonHeader = jsonHeader;
    }

    public void addProperty(String property, List data, ComponentType componentType, ContainerType containerType, boolean feature) {
        int width = componentType.getSize();
        size += CommonUtils.calcPadding(size, width);
        addReference(jsonHeader, property, size, componentType, containerType, feature);
        int writes = 0;
        int unit = containerType.getSize();
        size += width * unit * data.size();
    }

    public void addBatchId(List<Long> batchIdList) {
        Long max = batchIdList.stream().max(Long::compareTo).orElse(null);
        if (max == null) {
            throw new IllegalArgumentException("There null value in the batch id list.");
        }
        if (max > 0xFFFF) {
            addProperty("BATCH_ID", batchIdList, ComponentType.UNSIGNED_INT, ContainerType.SCALAR, true);
        } else if (max > 0xFF) {
            addProperty("BATCH_ID", batchIdList, ComponentType.UNSIGNED_SHORT, ContainerType.SCALAR, true);
        } else {
            addProperty("BATCH_ID", batchIdList, ComponentType.UNSIGNED_BYTE, ContainerType.SCALAR, true);
        }
    }

    public void finished() {
        size += CommonUtils.calcPadding(size, 8);
    }

    public Map<String, Object> getJsonHeader() {
        return jsonHeader;
    }

    public int calcHeaderSize(int offset, JsonParser parser) {
        int length = parser.toJsonString(jsonHeader).getBytes(StandardCharsets.UTF_8).length;
        return length + CommonUtils.calcPadding(offset + length, 8);
    }

    public int getSize() {
        return size;
    }
}
