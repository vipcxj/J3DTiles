package me.cxj.j3dtiles.utils;

import me.cxj.j3dtiles.impl.v1.BatchTableBinaryBodyReference;
import me.cxj.j3dtiles.impl.v1.BatchTableComponentType;
import me.cxj.j3dtiles.impl.v1.BatchTableContainerType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class FeatureUtils {

    private static final String PROP_BYTE_OFFSET = "byteOffset";
    private static final String PROP_COMPONENT_TYPE = "componentType";
    private static final String PROP_TYPE = "type";

    public static long[] getBatchId(Map<String, Object> jsonHeader, byte[] binaryBuffer, int length) {
        Object objValue = jsonHeader.get("BATCH_ID");
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            Object objComponentType = mapValue.get(PROP_COMPONENT_TYPE);
            String componentType;
            if (objComponentType == null) {
                componentType = "UNSIGNED_SHORT";
            } else if ("UNSIGNED_BYTE".equals(objComponentType.toString())) {
                componentType = "UNSIGNED_BYTE";
            } else if ("UNSIGNED_SHORT".equals(objComponentType.toString())) {
                componentType = "UNSIGNED_SHORT";
            } else if ("UNSIGNED_INT".equals(objComponentType.toString())) {
                componentType = "UNSIGNED_INT";
            } else {
                throw new IllegalArgumentException("Valid component types of the property 'BATCH_ID' are uint8, uint16 (default), and uint32.");
            }
            long[] out = new long[length];
            switch (componentType) {
                case "UNSIGNED_BYTE":
                    for (int i = 0; i < length; ++i) {
                        out[i] = binaryBuffer[offset + i];
                    }
                    return out;
                case "UNSIGNED_SHORT":
                    for (int i = 0; i < length; ++i) {
                        out[i] = StreamUtils.bytes2UnsignedShortLE(binaryBuffer, offset + i * 2);
                    }
                    return out;
                case "UNSIGNED_INT":
                    for (int i = 0; i < length; ++i) {
                        out[i] = StreamUtils.bytes2UnsignedIntLE(binaryBuffer, offset + i * 4);
                    }
                    return out;
                default:
                    throw new IllegalArgumentException("This is impossible!");
            }
        }
        throw new IllegalArgumentException("Invalid BATCH_ID. It must be a BinaryBodyReference.");
    }

    public static short[] getShortArrayFeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName, int length) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2ShortArrayLE(binaryBuffer, offset, length);
        } else if (objValue instanceof List) {
            return TypeUtils.toShortArray((List) objValue, length);
        }
        throw new IllegalArgumentException("Property " + propName + " is not a short array value.");
    }

    public static int[] getUnsignedShortArrayFeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName, int length) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2UnsignedShortArrayLE(binaryBuffer, offset, length);
        } else if (objValue instanceof List) {
            return TypeUtils.toIntegerArray((List) objValue, length);
        }
        throw new IllegalArgumentException("Property " + propName + " is not a short array value.");
    }

    public static Integer getIntegerScalarFeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2IntLE(binaryBuffer, offset);
        }
        if (objValue instanceof List) {
            List listValue = (List) objValue;
            if (listValue.size() != 1) {
                throw new IllegalArgumentException("Invalid scalar feature value: " + objValue);
            }
            return TypeUtils.toInteger(listValue.get(0));
        }
        return TypeUtils.toInteger(objValue);
    }

    public static Long getLongScalarFeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2LongLE(binaryBuffer, offset);
        }
        if (objValue instanceof List) {
            List listValue = (List) objValue;
            if (listValue.size() != 1) {
                throw new IllegalArgumentException("Invalid scalar feature value: " + objValue);
            }
            return TypeUtils.toLong(listValue.get(0));
        }
        return TypeUtils.toLong(objValue);
    }

    public static Float getFloatScalarFeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2FloatLE(binaryBuffer, offset);
        }
        if (objValue instanceof List) {
            List listValue = (List) objValue;
            if (listValue.size() != 1) {
                throw new IllegalArgumentException("Invalid scalar feature value: " + objValue);
            }
            return TypeUtils.toFloat(listValue.get(0));
        }
        return TypeUtils.toFloat(objValue);
    }

    public static Double getDoubleScalarFeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2DoubleLE(binaryBuffer, offset);
        }
        if (objValue instanceof List) {
            List listValue = (List) objValue;
            if (listValue.size() != 1) {
                throw new IllegalArgumentException("Invalid scalar feature value: " + objValue);
            }
            return TypeUtils.toDouble(listValue.get(0));
        }
        return TypeUtils.toDouble(objValue);
    }

    public static float[] getFloatVec3FeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2FloatArrayLE(binaryBuffer, offset, 3);
        } else if (objValue instanceof List) {
            return TypeUtils.toFloatArray((List) objValue, 3);
        }
        throw new IllegalArgumentException("Property " + propName + " is not a float vec3 value.");
    }

    public static float[] getFloatVec4FeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2FloatArrayLE(binaryBuffer, offset, 4);
        } else if (objValue instanceof List) {
            return TypeUtils.toFloatArray((List) objValue, 4);
        }
        throw new IllegalArgumentException("Property " + propName + " is not a float vec4 value.");
    }

    public static float[] getFloatArrayFeatureValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName, int length) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            return null;
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            return StreamUtils.bytes2FloatArrayLE(binaryBuffer, offset, length);
        } else if (objValue instanceof List) {
            return TypeUtils.toFloatArray((List) objValue, length);
        }
        throw new IllegalArgumentException("Property " + propName + " is not a float array value.");
    }

    public static Object getBatchTableValue(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName, int batchId) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            throw new IllegalArgumentException("No such property: " + propName + ".");
        }
        if (objValue instanceof Map) {
            Map mapValue = (Map) objValue;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            Object objComponentType = mapValue.get(PROP_COMPONENT_TYPE);
            if (!(objComponentType instanceof String)) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, no componentType found, or componentType is not a string.");
            }
            String strComponentType = (String) objComponentType;
            BatchTableComponentType componentType;
            try {
                componentType = BatchTableComponentType.valueOf(strComponentType);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, invalid componentType: " + strComponentType + ". Possible values: " + BatchTableComponentType.allValues() + ".", e);
            }
            Object objElementType = mapValue.get(PROP_TYPE);
            if (!(objElementType instanceof String)) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, no type found, or type is not a string.");
            }
            String strElementType = (String) objElementType;
            BatchTableContainerType containerType;
            try {
                containerType = BatchTableContainerType.valueOf(strElementType);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, invalid type: " + strElementType + ". Possible values: " + BatchTableContainerType.allValues() + ".", e);
            }
            int containerSize = containerType.getSize();
            int byteMask = 0xff;
            int shortMask = 0xffff;
            long intMask = 0xffffffff;
            switch (componentType) {
                case BYTE:
                    if (containerSize == 1) {
                        return binaryBuffer[offset + batchId];
                    } else {
                        return Arrays.copyOfRange(binaryBuffer, offset + batchId * containerSize, offset + (batchId + 1) * containerSize);
                    }
                case UNSIGNED_BYTE:
                    if (containerSize == 1) {
                        return byteMask & binaryBuffer[offset + batchId];
                    } else {
                        int[] out = new int[containerSize];
                        for (int i = 0; i < containerSize; ++i) {
                            out[i] = byteMask & binaryBuffer[offset + batchId * containerSize + i];
                        }
                        return out;
                    }
                case SHORT:
                    if (containerSize == 1) {
                        return StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * 2);
                    } else {
                        short[] out = new short[containerSize];
                        for (int i = 0; i < containerSize; ++i) {
                            out[i] = StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * containerSize * 2 +  i * 2);
                        }
                        return out;
                    }
                case UNSIGNED_SHORT:
                    if (containerSize == 1) {
                        return shortMask & StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * 2);
                    } else {
                        int[] out = new int[containerSize];
                        for (int i = 0; i < containerSize; ++i) {
                            out[i] = shortMask & StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * containerSize * 2 +  i * 2);
                        }
                        return out;
                    }
                case INT:
                    if (containerSize == 1) {
                        return StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * 4);
                    } else {
                        int[] out = new int[containerSize];
                        for (int i = 0; i < containerSize; ++i) {
                            out[i] = StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * containerSize * 4 +  i * 4);
                        }
                        return out;
                    }
                case UNSIGNED_INT:
                    if (containerSize == 1) {
                        return intMask & StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * 4);
                    } else {
                        long[] out = new long[containerSize];
                        for (int i = 0; i < containerSize; ++i) {
                            out[i] = intMask & StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * containerSize * 4 +  i * 4);
                        }
                        return out;
                    }
                case FLOAT:
                    if (containerSize == 1) {
                        return StreamUtils.bytes2FloatLE(binaryBuffer, offset + batchId * 4);
                    } else {
                        float[] out = new float[containerSize];
                        for (int i = 0; i < containerSize; ++i) {
                            out[i] = StreamUtils.bytes2FloatLE(binaryBuffer, offset + batchId * containerSize * 4 +  i * 4);
                        }
                        return out;
                    }
                case DOUBLE:
                    if (containerSize == 1) {
                        return StreamUtils.bytes2DoubleLE(binaryBuffer, offset + batchId * 8);
                    } else {
                        double[] out = new double[containerSize];
                        for (int i = 0; i < containerSize; ++i) {
                            out[i] = StreamUtils.bytes2DoubleLE(binaryBuffer, offset + batchId * containerSize * 8 +  i * 8);
                        }
                        return out;
                    }
                default:
                    throw new IllegalArgumentException("Unsupported componentType: " + componentType + ".");
            }
        } else if (objValue instanceof List) {
            return ((List) objValue).get(batchId);
        }
        throw new IllegalArgumentException("Unparseable batch table property: " + propName + ".");
    }

    public static List<?> getBatchTableValues(BatchTableBinaryBodyReference reference, byte[] binaryBuffer, String propName, int batchLength) {
        BatchTableComponentType componentType = reference.getComponentType();
        BatchTableContainerType containerType = reference.getType();
        int offset = reference.getByteOffset();
        int containerSize = containerType.getSize();
        int byteMask = 0xff;
        int shortMask = 0xffff;
        long intMask = 0xffffffff;
        switch (componentType) {
            case BYTE:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> binaryBuffer[offset + batchId])
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> Arrays.copyOfRange(binaryBuffer, offset + batchId * containerSize, offset + (batchId + 1) * containerSize))
                            .collect(Collectors.toList());
                }
            case UNSIGNED_BYTE:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> byteMask & binaryBuffer[offset + batchId])
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> {
                                int[] out = new int[containerSize];
                                for (int i = 0; i < containerSize; ++i) {
                                    out[i] = byteMask & binaryBuffer[offset + batchId * containerSize + i];
                                }
                                return out;
                            })
                            .collect(Collectors.toList());
                }
            case SHORT:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * 2))
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> {
                                short[] out = new short[containerSize];
                                for (int i = 0; i < containerSize; ++i) {
                                    out[i] = StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * containerSize * 2 + i * 2);
                                }
                                return out;
                            })
                            .collect(Collectors.toList());
                }
            case UNSIGNED_SHORT:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> shortMask & StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * 2))
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> {
                                int[] out = new int[containerSize];
                                for (int i = 0; i < containerSize; ++i) {
                                    out[i] = shortMask & StreamUtils.bytes2ShortLE(binaryBuffer, offset + batchId * containerSize * 2 + i * 2);
                                }
                                return out;
                            })
                            .collect(Collectors.toList());
                }
            case INT:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * 4))
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> {
                                int[] out = new int[containerSize];
                                for (int i = 0; i < containerSize; ++i) {
                                    out[i] = StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * containerSize * 4 + i * 4);
                                }
                                return out;
                            })
                            .collect(Collectors.toList());
                }
            case UNSIGNED_INT:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> intMask & StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * 4))
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> {
                                long[] out = new long[containerSize];
                                for (int i = 0; i < containerSize; ++i) {
                                    out[i] = intMask & StreamUtils.bytes2IntLE(binaryBuffer, offset + batchId * containerSize * 4 + i * 4);
                                }
                                return out;
                            })
                            .collect(Collectors.toList());
                }
            case FLOAT:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> StreamUtils.bytes2FloatLE(binaryBuffer, offset + batchId * 4))
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> {
                                float[] out = new float[containerSize];
                                for (int i = 0; i < containerSize; ++i) {
                                    out[i] = StreamUtils.bytes2FloatLE(binaryBuffer, offset + batchId * containerSize * 4 + i * 4);
                                }
                                return out;
                            })
                            .collect(Collectors.toList());
                }
            case DOUBLE:
                if (containerSize == 1) {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> StreamUtils.bytes2DoubleLE(binaryBuffer, offset + batchId * 8))
                            .collect(Collectors.toList());
                } else {
                    return IntStream.range(0, batchLength)
                            .mapToObj(batchId -> {
                                double[] out = new double[containerSize];
                                for (int i = 0; i < containerSize; ++i) {
                                    out[i] = StreamUtils.bytes2DoubleLE(binaryBuffer, offset + batchId * containerSize * 8 + i * 8);
                                }
                                return out;
                            })
                            .collect(Collectors.toList());
                }
            default:
                throw new IllegalArgumentException("Unsupported componentType: " + componentType + ".");
        }
    }

    public static BatchTableBinaryBodyReference toReference(Object value) {
        if (value instanceof BatchTableBinaryBodyReference) {
            return (BatchTableBinaryBodyReference) value;
        }
        if (value instanceof Map) {
            Map mapValue = (Map) value;
            Object objByteOffset = mapValue.get(PROP_BYTE_OFFSET);
            if (objByteOffset == null) {
                throw new IllegalArgumentException("Invalid BinaryBodyReference struct, no byteOffset found.");
            }
            int offset = TypeUtils.toInteger(objByteOffset);
            Object objComponentType = mapValue.get(PROP_COMPONENT_TYPE);
            if (!(objComponentType instanceof String)) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, no componentType found, or componentType is not a string.");
            }
            String strComponentType = (String) objComponentType;
            BatchTableComponentType componentType;
            try {
                componentType = BatchTableComponentType.valueOf(strComponentType);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, invalid componentType: " + strComponentType + ". Possible values: " + BatchTableComponentType.allValues() + ".", e);
            }
            Object objElementType = mapValue.get(PROP_TYPE);
            if (!(objElementType instanceof String)) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, no type found, or type is not a string.");
            }
            String strElementType = (String) objElementType;
            BatchTableContainerType containerType;
            try {
                containerType = BatchTableContainerType.valueOf(strElementType);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct, invalid type: " + strElementType + ". Possible values: " + BatchTableContainerType.allValues() + ".", e);
            }
            return new BatchTableBinaryBodyReference(offset, componentType, containerType);
        }
        throw new IllegalArgumentException("Invalid BatchTableBinaryBodyReference struct: " + value + ".");
    }

    public static List<?> getBatchTableValues(Map<String, Object> jsonHeader, byte[] binaryBuffer, String propName, int batchLength) {
        Object objValue = jsonHeader.get(propName);
        if (objValue == null) {
            throw new IllegalArgumentException("No such property: " + propName + ".");
        }
        if (objValue instanceof Map) {
            BatchTableBinaryBodyReference reference = toReference(objValue);
            return getBatchTableValues(reference, binaryBuffer, propName, batchLength);
        } else if (objValue instanceof List) {
            return (List<?>) objValue;
        }
        throw new IllegalArgumentException("Unparseable batch table property: " + propName + ".");
    }
}
