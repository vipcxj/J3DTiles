package me.cxj.j3dtiles.ifc.model;

import org.bimserver.emf.IdEObject;
import org.bimserver.emf.PackageMetaData;

import java.util.Map;

/**
 * Created by vipcxj on 2018/11/16.
 */
public class BasicIfcModel extends IfcModel {

    public BasicIfcModel(PackageMetaData packageMetaData, Map<Integer, Long> pidRoidMap) {
        super(packageMetaData, pidRoidMap);
    }

    public BasicIfcModel(PackageMetaData packageMetaData, Map<Integer, Long> pidRoidMap, int size) {
        super(packageMetaData, pidRoidMap, size);
    }

    @Override
    public void load(IdEObject idEObject) {
    }
}
