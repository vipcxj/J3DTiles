package me.cxj.j3dtiles.ifc;

import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.io.GltfModelWriter;
import me.cxj.ifc.deserializer.IfcAutoDetectDeserializer;
import me.cxj.ifc.deserializer.ZipWrapperDeserializer;
import me.cxj.ifc.model.GeomModel;
import me.cxj.ifc.model.IfcModel;
import me.cxj.j3dtiles.ifc.tools.GltfConvertor;
import org.bimserver.plugins.deserializers.DeserializeException;
import org.junit.Test;

import java.io.*;

/**
 * Created by vipcxj on 2018/11/20.
 */
public class Atest {

    private IfcModel loadModel(String resourcePath, String geomFileName) throws IOException, DeserializeException {
        IfcModel model;
        try (InputStream is = getClass().getResourceAsStream(resourcePath)){
            model = new ZipWrapperDeserializer<>(new IfcAutoDetectDeserializer()).read(is, null);
        }
        if (!new File(geomFileName).exists()) {
            model.generateGeomData();
            GeomModel geomModel = model.exportGeomData();
            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(geomFileName))){
                geomModel.save(os);
            }
        } else {
            try (InputStream gis = new BufferedInputStream(new FileInputStream(geomFileName))){
                model.importGeomData(new GeomModel().load(gis));
            }
        }
        return model;
    }

    @Test
    public void testLoadModel() throws IOException, DeserializeException {
        IfcModel model = loadModel("/IFC Schependomlaan.ifc", "IFC Schependomlaan.geom");
        GltfModel gltf = new GltfConvertor().convert(model);
        new GltfModelWriter().writeBinary(gltf, new File("IFC Schependomlaan.glb"));
    }

    @Test
    public void testLoadBigModel() throws IOException, DeserializeException {
        IfcModel model = loadModel("/DC_Riverside_Bldg-LOD_300.ifc", "DC_Riverside_Bldg-LOD_300.geom");
        GltfModel gltf = new GltfConvertor().convert(model);
        new GltfModelWriter().writeBinary(gltf, new File("DC_Riverside_Bldg-LOD_300.glb"));
    }

    @Test
    public void testStruct() throws IOException, DeserializeException {
        try (InputStream is = getClass().getResourceAsStream("/IFC Schependomlaan.ifc")){
            IfcModel model = new IfcAutoDetectDeserializer().read(is, null);
            try (OutputStream os = new FileOutputStream("struct.json")){
                model.inspect(os);
            }
        }
    }
}
