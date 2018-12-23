package me.cxj.j3dtiles.ifc.tools;

import de.javagl.jgltf.impl.v2.*;
import de.javagl.jgltf.impl.v2.Buffer;
import de.javagl.jgltf.model.GltfConstants;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.GltfModels;
import de.javagl.jgltf.model.io.v2.GltfAssetV2;
import me.cxj.ifc.model.IfcModel;
import me.cxj.j3dtiles.utils.CommonUtils;
import org.bimserver.emf.IdEObject;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.emf.Schema;
import org.bimserver.geometry.IfcColors;
import org.bimserver.geometry.Matrix;
import org.bimserver.models.geometry.GeometryData;
import org.bimserver.models.geometry.GeometryInfo;
import org.bimserver.models.ifc2x3tc1.Tristate;
import org.bimserver.models.ifc4.IfcBuildingElement;
import org.bimserver.models.ifc4.IfcProduct;
import org.bimserver.utils.IfcUtils;
import org.eclipse.emf.ecore.EClass;

import java.nio.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by vipcxj on 2018/11/22.
 */
public class GltfConvertor {

    // private static final byte[] vertexColorFragmentShaderBytes = IOUtils.toByteArray(GltfConvertor.class.getResourceAsStream("/shaders/fragmentcolor.shader"), true);
    // private static final byte[] vertexColorVertexShaderBytes = IOUtils.toByteArray(GltfConvertor.class.getResourceAsStream("/shaders/vertexcolor.shader"), true);
    // private static final byte[] materialColorFragmentShaderBytes = IOUtils.toByteArray(GltfConvertor.class.getResourceAsStream("/shaders/fragmentmaterial.shader"), true);
    // private static final byte[] materialColorVertexShaderBytes = IOUtils.toByteArray(GltfConvertor.class.getResourceAsStream("/shaders/vertexmaterial.shader"), true);

    private final Map<String, Integer> createdMaterials = new HashMap<>();

    private static final int maxIndexValues = 16389;

    private GltfBinary createBinaryBuffer(GlTF gltf, IfcModelInterface ifcModel) {
        int totalIndicesByteLength;
        int totalVerticesByteLength;
        int totalNormalsByteLength;
        int totalColorsByteLength;
        Supplier<Stream<GeometryData>> streamSupplier = () -> {
            Stream<GeometryInfo> geoInfoStream;
            if (ifcModel.getPackageMetaData().getSchema() == Schema.IFC4) {
                geoInfoStream = ifcModel.getAllWithSubTypes(IfcBuildingElement.class)
                        .stream().map(IfcProduct::getGeometry);
            } else if (ifcModel.getPackageMetaData().getSchema() == Schema.IFC2X3TC1) {
                geoInfoStream = ifcModel.getAllWithSubTypes(org.bimserver.models.ifc2x3tc1.IfcBuildingElement.class)
                        .stream().map(org.bimserver.models.ifc2x3tc1.IfcProduct::getGeometry);
            } else {
                throw new IllegalArgumentException("Unsupported ifc schema version: " + ifcModel.getPackageMetaData().getSchema() + ".");
            }
            return geoInfoStream
                    .filter(Objects::nonNull)
                    .filter(info -> info.getData() != null && isNotEmpty(info.getData().getIndices()))
                    .map(GeometryInfo::getData);
        };
        totalIndicesByteLength = streamSupplier.get().mapToInt(data -> data.getIndices().getData().length / 2).sum();
        totalVerticesByteLength = streamSupplier.get().mapToInt(data -> {
            if (!isNotEmpty(data.getVertices())) {
                return 0;
            }
            int nrIndicesBytes = data.getIndices().getData().length;
            return (nrIndicesBytes > 4 * maxIndexValues) ? (nrIndicesBytes / 4 * 3 * 4) : data.getVertices().getData().length;
        }).sum();
        totalNormalsByteLength = streamSupplier.get().mapToInt(data -> {
            if (!isNotEmpty(data.getNormals())) {
                return 0;
            }
            int nrIndicesBytes = data.getIndices().getData().length;
            return (nrIndicesBytes > 4 * maxIndexValues) ? (nrIndicesBytes / 4 * 3 * 4) : data.getNormals().getData().length;
        }).sum();
        totalColorsByteLength = streamSupplier.get()
                .filter(data -> data.getColorsQuantized() != null)
                .mapToInt(data -> {
                    if (!isNotEmpty(data.getColorsQuantized())) {
                        return 0;
                    }
                    int nrIndicesBytes = data.getIndices().getData().length;
                    return (nrIndicesBytes > 4 * maxIndexValues) ? (nrIndicesBytes / 4 * 4 * 4) : data.getColorsQuantized().getData().length;
                }).sum();
        totalIndicesByteLength += CommonUtils.calcPadding(totalIndicesByteLength, 4);
        totalVerticesByteLength += CommonUtils.calcPadding(totalIndicesByteLength + totalVerticesByteLength, 4);
        totalNormalsByteLength += CommonUtils.calcPadding(totalIndicesByteLength + totalVerticesByteLength + totalNormalsByteLength, 4);
        totalColorsByteLength += CommonUtils.calcPadding(totalIndicesByteLength + totalVerticesByteLength + totalNormalsByteLength + totalColorsByteLength, 4);
        int totalBodyByteLength = totalIndicesByteLength + totalVerticesByteLength + totalNormalsByteLength + totalColorsByteLength;
        if (totalBodyByteLength <= 0) {
            return null;
        }
        GltfBinary binary = new GltfBinary();
        binary.body = ByteBuffer.allocate(totalBodyByteLength);
        binary.body.order(ByteOrder.LITTLE_ENDIAN);
        if (totalIndicesByteLength > 0) {
            binary.indicesBuffer = ByteBuffer.allocate(totalIndicesByteLength);
            binary.indicesBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        if (totalVerticesByteLength > 0) {
            binary.verticesBuffer = ByteBuffer.allocate(totalVerticesByteLength);
            binary.verticesBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        if (totalNormalsByteLength > 0) {
            binary.normalsBuffer = ByteBuffer.allocate(totalNormalsByteLength);
            binary.normalsBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        if (totalColorsByteLength > 0) {
            binary.colorsBuffer = ByteBuffer.allocate(totalColorsByteLength);
            binary.colorsBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        readyBinary(gltf, binary);
        return binary;
    }

    private int createBufferView(GlTF gltf, int offset, int len, int target, int stride) {
        BufferView bufferView = new BufferView();
        bufferView.setBuffer(0);
        bufferView.setByteOffset(offset);
        bufferView.setByteLength(len);
        if (target != -1) {
            bufferView.setTarget(target);
        }
        if (stride != -1) {
            bufferView.setByteStride(stride);
        }
        gltf.addBufferViews(bufferView);
        return gltf.getBufferViews().size() - 1;
    }

    private int createAccessor(GlTF gltf, int bufferViewIndex, int offset, int count, int componentType, String type, Number[] min, Number[] max) {
        Accessor accessor = new Accessor();
        accessor.setBufferView(bufferViewIndex);
        accessor.setByteOffset(offset);
        accessor.setCount(count);
        accessor.setComponentType(componentType);
        accessor.setType(type);
        if (min != null) {
            accessor.setMin(min);
        }
        if (max != null) {
            accessor.setMax(max);
        }
        gltf.addAccessors(accessor);
        return gltf.getAccessors().size() - 1;
    }

    private static float[] ROT_Z_TO_Y = new float[] {
            1.f, 0.f, 0.f, 0.f,
            0.f, 0.f, -1.f, 0.f,
            0.f, 1.f, 0.f, 0.f,
            0.f, 0.f, 0.f, 1.f
    };

    private void createDefaultScene(GlTF gltf, IfcModel model) {
        List<Scene> scenes = new ArrayList<>();
        Scene scene = new Scene();
        List<Node> nodes = new ArrayList<>();
        Node node = new Node();
        node.setName("root");
        double[] trueNorth = model.getTrueNorth();
        double r = Math.sqrt(trueNorth[0] * trueNorth[0] + trueNorth[1] * trueNorth[1]);
        float[] matTn = new float[] {
                (float) (trueNorth[1] / r), (float) (-trueNorth[0] / r),    0.f, 0.f,
                (float) (trueNorth[0] / r), (float) (trueNorth[1] / r),     0.f, 0.f,
                0.f,                        0.f,                            1.f, 0.f,
                0.f,                        0.f,                            0.f, 1.f
        };
        float[] mat = new float[16];
        Matrix.multiplyMM(mat, 0, ROT_Z_TO_Y, 0, matTn, 0);
        node.setMatrix(mat);
        nodes.add(node);
        gltf.setNodes(nodes);
        scene.addNodes(0);
        scenes.add(scene);
        gltf.setScenes(scenes);
        gltf.setScene(0);
    }

    private int createMesh(GlTF gltf) {
        Mesh mesh = new Mesh();
        gltf.addMeshes(mesh);
        return gltf.getMeshes().size() - 1;
    }

    private static final float[] defaultMatrix = new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
    private boolean isDefaultMatrix(float[] matrix) {
        for (int i = 0; i < 16; ++i) {
            if (Math.abs(defaultMatrix[i] - matrix[i]) >= 0.00000001) {
                return false;
            }
        }
        return true;
    }

    private void addMeshNode(GlTF gltf, int mesh, float[] matrix, String name) {
        Node root = gltf.getNodes().get(0);
        Node node = new Node();
        node.setMesh(mesh);
        if (matrix != null && !isDefaultMatrix(matrix)) {
            node.setMatrix(matrix);
        }
        if (name != null) {
            node.setName(name);
        }
        gltf.addNodes(node);
        int nodeId = gltf.getNodes().size() - 1;
        root.addChildren(nodeId);
    }

    private int createPrimitives(GlTF gltf, EClass eClass, int mesh, Integer indices, Integer vertices, Integer normals, Integer colors) {
        MeshPrimitive primitive = new MeshPrimitive();
        if (indices != null) {
            primitive.setIndices(indices);
        }
        if (vertices != null) {
            primitive.addAttributes("POSITION", vertices);
        }
        if (normals != null) {
            primitive.addAttributes("NORMAL", normals);
        }
        if (colors != null) {
            primitive.addAttributes("COLOR_0", colors);
        }
        primitive.setMaterial(createOrGetMaterialIfc4(gltf, eClass.getName(), IfcColors.getDefaultColor(eClass.getName())));
        primitive.setMode(GltfConstants.GL_TRIANGLES);
        if (mesh == -1) {
            mesh = createMesh(gltf);
        }
        gltf.getMeshes().get(mesh).addPrimitives(primitive);
        return mesh;
    }

    private int createOrGetMaterialIfc4(GlTF gltf, String name, float[] colors) {
        if (createdMaterials.containsKey(name)) {
            return createdMaterials.get(name);
        }
        Material material = new Material();
        material.setName(name + "Material");
        MaterialPbrMetallicRoughness pbr = new MaterialPbrMetallicRoughness();
        pbr.setMetallicFactor(0.0f);
        pbr.setRoughnessFactor(0.0f);
        pbr.setBaseColorFactor(colors);
        material.setPbrMetallicRoughness(pbr);
        gltf.addMaterials(material);
        int id = gltf.getMaterials().size() - 1;
        createdMaterials.put(name, id);
        return id;
    }

    private void readyBinary(GlTF jsonPart, GltfBinary binary) {
        if (binary.body != null) {
            List<Buffer> buffers = new ArrayList<>(1);
            Buffer buffer = new Buffer();
            buffer.setByteLength(binary.body.capacity());
            buffers.add(buffer);
            jsonPart.setBuffers(buffers);
            int offset = 0;
            if (binary.indicesBuffer != null) {
                binary.indicesBufferId = createBufferView(jsonPart, offset, binary.indicesBuffer.capacity(), GltfConstants.GL_ELEMENT_ARRAY_BUFFER, -1);
                offset += binary.indicesBuffer.capacity();
            }
            if (binary.verticesBuffer != null) {
                binary.verticesBufferId = createBufferView(jsonPart, offset, binary.verticesBuffer.capacity(), GltfConstants.GL_ARRAY_BUFFER, 12);
                offset += binary.verticesBuffer.capacity();
            }
            if (binary.normalsBuffer != null) {
                binary.normalsBufferId = createBufferView(jsonPart, offset, binary.normalsBuffer.capacity(), GltfConstants.GL_ARRAY_BUFFER, 12);
                offset += binary.normalsBuffer.capacity();
            }
            if (binary.colorsBuffer != null) {
                binary.colorsBufferId = createBufferView(jsonPart, offset, binary.colorsBuffer.capacity(), GltfConstants.GL_ARRAY_BUFFER, 16);
            }
        }
    }

    private static final String TYPE_SCALE = "SCALAR";
    private static final String TYPE_VEC3 = "VEC3";
    private static final String TYPE_VEC4 = "VEC4";

    private boolean isNotEmpty(org.bimserver.models.geometry.Buffer buffer) {
        return buffer != null && buffer.getData() != null && buffer.getData().length > 0;
    }

    private float min(float arg0, float arg1, float arg2, float arg3) {
        return Math.min(Math.min(Math.min(arg0, arg1), arg2), arg3);
    }

    private float max(float arg0, float arg1, float arg2, float arg3) {
        return Math.max(Math.max(Math.max(arg0, arg1), arg2), arg3);
    }

    private void calcBound(Number[] min, Number[] max, byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        min[0] = min[1] = min[2] = Float.MAX_VALUE;
        max[0] = max[1] = max[2] = -Float.MAX_VALUE;
        for (int i = 0; 3 * i + 2 < data.length / 4; ++i) {
            float x = floatBuffer.get(3 * i);
            float y = floatBuffer.get(3 * i + 1);
            float z = floatBuffer.get(3 * i + 2);
            min[0] = Math.min((Float) min[0], x);
            min[1] = Math.min((Float) min[1], y);
            min[2] = Math.min((Float) min[2], z);
            max[0] = Math.max((Float) max[0], x);
            max[1] = Math.max((Float) max[1], y);
            max[2] = Math.max((Float) max[2], z);
        }
    }

    private void convertGeometryInfo(GlTF jsonPart, GltfBinary binary, GeometryInfo geometryInfo, IdEObject product, String name) {
        if (geometryInfo != null && geometryInfo.getData() != null && geometryInfo.getData().getVertices() != null && geometryInfo.getData().getVertices().getData().length > 0) {
            GeometryData data = geometryInfo.getData();
            if (isNotEmpty(data.getIndices())) {
                float[] matrix = null;
                if (geometryInfo.getTransformation() != null) {
                    matrix = new float[16];
                    DoubleBuffer buffer = ByteBuffer.wrap(geometryInfo.getTransformation()).order(ByteOrder.LITTLE_ENDIAN).asDoubleBuffer();
                    for (int i = 0; i < 16; ++i) {
                        matrix[i] = (float) buffer.get(i);
                    }
                }
                ByteBuffer indicesBuffer = ByteBuffer.wrap(data.getIndices().getData());
                indicesBuffer.order(ByteOrder.LITTLE_ENDIAN);
                IntBuffer indicesIntBuffer = indicesBuffer.asIntBuffer();
                if (data.getIndices().getData().length > 4 * maxIndexValues) {
                    int totalNrIndices = indicesIntBuffer.capacity();
                    int nrParts = (totalNrIndices + maxIndexValues - 1) / maxIndexValues;
                    int mesh = createMesh(jsonPart);
                    for (int part = 0; part < nrParts; ++part) {
                        int startPositionIndices = binary.indicesBuffer.position();
                        short indexCounter = 0;
                        int upto = Math.min((part + 1) * maxIndexValues, totalNrIndices);
                        int nrVertices = upto - part * maxIndexValues;
                        for (int i = part * maxIndexValues; i < upto; ++i) {
                            binary.indicesBuffer.putShort(indexCounter++);
                        }
                        int indicesAccessorId = createAccessor(jsonPart, binary.indicesBufferId, startPositionIndices, nrVertices / 3, GltfConstants.GL_UNSIGNED_SHORT, TYPE_SCALE, null, null);
                        Integer verticesAccessorId = null;
                        Integer normalsAccessorId = null;
                        Integer colorsAccessorId = null;
                        if (isNotEmpty(data.getVertices())) {
                            int startPositionVertices = binary.verticesBuffer.position();
                            ByteBuffer verticesBuffer = ByteBuffer.wrap(data.getVertices().getData());
                            verticesBuffer.order(ByteOrder.LITTLE_ENDIAN);
                            FloatBuffer verticesFloatBuffer = verticesBuffer.asFloatBuffer();
                            Number[] min = new Number[] {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE};
                            Number[] max = new Number[] {-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};
                            for (int i = part * maxIndexValues; i < upto; i += 3) {
                                int oldIndex1 = indicesIntBuffer.get(i);
                                int oldIndex2 = indicesIntBuffer.get(i+1);
                                int oldIndex3 = indicesIntBuffer.get(i+2);
                                float x1 = verticesFloatBuffer.get(oldIndex1 * 3);
                                float y1 = verticesFloatBuffer.get(oldIndex1 * 3 + 1);
                                float z1 = verticesFloatBuffer.get(oldIndex1 * 3 + 2);
                                binary.verticesBuffer.putFloat(x1);
                                binary.verticesBuffer.putFloat(y1);
                                binary.verticesBuffer.putFloat(z1);
                                float x2 = verticesFloatBuffer.get(oldIndex2 * 3);
                                float y2 = verticesFloatBuffer.get(oldIndex2 * 3 + 1);
                                float z2 = verticesFloatBuffer.get(oldIndex2 * 3 + 2);
                                binary.verticesBuffer.putFloat(x2);
                                binary.verticesBuffer.putFloat(y2);
                                binary.verticesBuffer.putFloat(z2);
                                float x3 = verticesFloatBuffer.get(oldIndex3 * 3);
                                float y3 = verticesFloatBuffer.get(oldIndex3 * 3 + 1);
                                float z3 = verticesFloatBuffer.get(oldIndex3 * 3 + 2);
                                binary.verticesBuffer.putFloat(x3);
                                binary.verticesBuffer.putFloat(y3);
                                binary.verticesBuffer.putFloat(z3);
                                min[0] = min((Float) min[0], x1, x2, x3);
                                min[1] = min((Float) min[1], y1, y2, y3);
                                min[2] = min((Float) min[2], z1, z2, z3);
                                max[0] = max((Float) max[0], x1, x2, x3);
                                max[1] = max((Float) max[1], y1, y2, y3);
                                max[2] = max((Float) max[2], z1, z2, z3);
                            }
                            verticesAccessorId = createAccessor(jsonPart, binary.verticesBufferId, startPositionVertices, nrVertices, GltfConstants.GL_FLOAT, TYPE_VEC3, min, max);
                        }
                        if (isNotEmpty(data.getNormals())) {
                            int startPositionNormals = binary.normalsBuffer.position();
                            ByteBuffer normalsBuffer = ByteBuffer.wrap(data.getNormals().getData());
                            normalsBuffer.order(ByteOrder.LITTLE_ENDIAN);
                            FloatBuffer normalsFloatBuffer = normalsBuffer.asFloatBuffer();
                            for (int i=part * maxIndexValues; i<upto; i+=3) {
                                int oldIndex1 = indicesIntBuffer.get(i);
                                int oldIndex2 = indicesIntBuffer.get(i+1);
                                int oldIndex3 = indicesIntBuffer.get(i+2);
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex1 * 3));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex1 * 3 + 1));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex1 * 3 + 2));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex2 * 3));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex2 * 3 + 1));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex2 * 3 + 2));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex3 * 3));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex3 * 3 + 1));
                                binary.normalsBuffer.putFloat(normalsFloatBuffer.get(oldIndex3 * 3 + 2));
                            }
                            normalsAccessorId = createAccessor(jsonPart, binary.normalsBufferId, startPositionNormals, nrVertices, GltfConstants.GL_FLOAT, TYPE_VEC3, null, null);
                        }
                        if (isNotEmpty(data.getColorsQuantized())) {
                            int startPositionColors = binary.colorsBuffer.position();
                            ByteBuffer materialsBuffer = ByteBuffer.wrap(data.getColorsQuantized().getData());
                            materialsBuffer.order(ByteOrder.LITTLE_ENDIAN);
                            FloatBuffer materialsFloatBuffer = materialsBuffer.asFloatBuffer();
                            for (int i=part * maxIndexValues; i<upto; i+=3) {
                                int oldIndex1 = indicesIntBuffer.get(i);
                                int oldIndex2 = indicesIntBuffer.get(i+1);
                                int oldIndex3 = indicesIntBuffer.get(i+2);
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex1 * 4));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex1 * 4 + 1));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex1 * 4 + 2));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex1 * 4 + 3));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex2 * 4));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex2 * 4 + 1));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex2 * 4 + 2));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex2 * 4 + 3));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex3 * 4));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex3 * 4 + 1));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex3 * 4 + 2));
                                binary.colorsBuffer.putFloat(materialsFloatBuffer.get(oldIndex3 * 4 + 3));
                            }
                            colorsAccessorId = createAccessor(jsonPart, binary.colorsBufferId, startPositionColors, nrVertices, GltfConstants.GL_FLOAT, TYPE_VEC4, null, null);
                        }
                        createPrimitives(jsonPart, product.eClass(), mesh, indicesAccessorId, verticesAccessorId, normalsAccessorId, colorsAccessorId);
                    }
                    addMeshNode(jsonPart, mesh, matrix, name);
                } else {
                    int maxVal = 0;
                    int startPositionIndices = binary.indicesBuffer.position();
                    int totalNrIndices = indicesIntBuffer.capacity();
                    for (int i = 0; i < totalNrIndices; ++i) {
                        int index = indicesIntBuffer.get(i);
                        if (index > Short.MAX_VALUE) {
                            throw new IllegalArgumentException("Index too large to store as short " + index);
                        }
                        binary.indicesBuffer.putShort((short)(index));
                        if (index > maxVal) {
                            maxVal = index;
                        }
                    }
                    Integer[] min = new Integer[]{0};
                    Integer[] max = new Integer[]{maxVal};
                    int indicesAccessorId = createAccessor(jsonPart, binary.indicesBufferId, startPositionIndices, totalNrIndices, GltfConstants.GL_UNSIGNED_SHORT, TYPE_SCALE, min, max);
                    Integer verticesAccessorId = null;
                    Integer normalsAccessorId = null;
                    Integer colorsAccessorId = null;
                    if (isNotEmpty(data.getVertices())) {
                        int startPositionVertices = binary.verticesBuffer.position();
                        binary.verticesBuffer.put(data.getVertices().getData());
                        Number[] vMin = new Number[3], vMax = new Number[3];
                        calcBound(vMin, vMax, data.getVertices().getData());
                        verticesAccessorId = createAccessor(jsonPart, binary.verticesBufferId, startPositionVertices, data.getVertices().getData().length / 12, GltfConstants.GL_FLOAT, TYPE_VEC3, vMin, vMax);
                    }
                    if (isNotEmpty(data.getNormals())) {
                        int startPositionNormals = binary.normalsBuffer.position();
                        binary.normalsBuffer.put(data.getNormals().getData());
                        normalsAccessorId = createAccessor(jsonPart, binary.normalsBufferId, startPositionNormals, data.getNormals().getData().length / 12, GltfConstants.GL_FLOAT, TYPE_VEC3, null, null);
                    }
                    if (isNotEmpty(data.getColorsQuantized())) {
                        int startPositionColors = binary.colorsBuffer.position();
                        binary.colorsBuffer.put(data.getColorsQuantized().getData());
                        colorsAccessorId = createAccessor(jsonPart, binary.colorsBufferId, startPositionColors, data.getColorsQuantized().getData().length / 16, GltfConstants.GL_FLOAT, TYPE_VEC4, null, null);
                    }
                    int mesh = createPrimitives(jsonPart, product.eClass(), -1, indicesAccessorId, verticesAccessorId, normalsAccessorId, colorsAccessorId);
                    addMeshNode(jsonPart, mesh, matrix, name);
                }
            }
        }
    }

    private void generateAsset(GlTF jsonPart) {
        Asset asset = new Asset();
        asset.setVersion("2.0");
        asset.setMinVersion("2.0");
        asset.setGenerator(Metas.properties.getProperty("project.name") + "-" + Metas.properties.getProperty("project.version") + " for " + Metas.properties.getProperty("platform.os.name") + "-" + Metas.properties.getProperty("platform.os.arch"));
        jsonPart.setAsset(asset);
    }

    public GltfModel convert(IfcModel ifcModel) {
        GlTF jsonPart = new GlTF();
        generateAsset(jsonPart);
        createDefaultScene(jsonPart, ifcModel);
        GltfBinary binary = createBinaryBuffer(jsonPart, ifcModel);
        if (binary != null) {
            List<Double> areas = new ArrayList<>();
            List<Double> volumes = new ArrayList<>();
            int num = 0, numAll = 0;
            if (ifcModel.getPackageMetaData().getSchema() == Schema.IFC4) {
                for (IfcBuildingElement ifcBuildingElement : ifcModel.getAllWithSubTypes(IfcBuildingElement.class)) {
                    GeometryInfo geometryInfo = ifcBuildingElement.getGeometry();
                    if (geometryInfo != null) {
                        if (geometryInfo.getArea() > 0.1 && geometryInfo.getVolume() > 0.01) {
                            convertGeometryInfo(jsonPart, binary, geometryInfo, ifcBuildingElement, ifcBuildingElement.getName());
                            ++num;
                        }
                        areas.add(geometryInfo.getArea());
                        volumes.add(geometryInfo.getVolume());
                        ++numAll;
                    }
                }
            } else if (ifcModel.getPackageMetaData().getSchema() == Schema.IFC2X3TC1) {
                for (org.bimserver.models.ifc2x3tc1.IfcBuildingElement ifcBuildingElement : ifcModel.getAllWithSubTypes(org.bimserver.models.ifc2x3tc1.IfcBuildingElement.class)) {
                    Tristate isExternal = IfcUtils.getBooleanProperty(ifcBuildingElement, "IsExternal");
                    if (isExternal == Tristate.TRUE) {
                        GeometryInfo geometryInfo = ifcBuildingElement.getGeometry();
                        if (geometryInfo != null) {
//                            if (geometryInfo.getArea() > 1 && geometryInfo.getVolume() > 0.01) {
                                convertGeometryInfo(jsonPart, binary, geometryInfo, ifcBuildingElement, ifcBuildingElement.getName());
                                ++num;
//                            }
                            areas.add(geometryInfo.getArea());
                            volumes.add(geometryInfo.getVolume());
                            ++numAll;
                        }
                    }
                }
            }
            areas.sort(Double::compareTo);
            volumes.sort(Double::compareTo);
            System.out.println("mid area: " + areas.get(areas.size() / 2) + ", mid volume: " + volumes.get(volumes.size() / 2));
            System.out.println(num + "/" + numAll);
            binary.fill();
        }
        GltfAssetV2 asset = new GltfAssetV2(jsonPart, binary != null ? binary.body : null);
        return GltfModels.create(asset);
    }

    class GltfBinary {
        private ByteBuffer body;
        private ByteBuffer indicesBuffer;
        private int indicesBufferId;
        private ByteBuffer verticesBuffer;
        private int verticesBufferId;
        private ByteBuffer normalsBuffer;
        private int normalsBufferId;
        private ByteBuffer colorsBuffer;
        private int colorsBufferId;

        public void fill() {
            body.clear();
            if (indicesBuffer != null) {
                indicesBuffer.rewind();
                indicesBuffer.limit(indicesBuffer.capacity());
                body.put(indicesBuffer);
            }
            if (verticesBuffer != null) {
                verticesBuffer.rewind();
                verticesBuffer.limit(verticesBuffer.capacity());
                body.put(verticesBuffer);
            }
            if (normalsBuffer != null) {
                normalsBuffer.rewind();
                normalsBuffer.limit(normalsBuffer.capacity());
                body.put(normalsBuffer);
            }
            if (colorsBuffer != null) {
                colorsBuffer.rewind();
                colorsBuffer.limit(colorsBuffer.capacity());
                body.put(colorsBuffer);
            }
            body.rewind();
        }
    }
}
