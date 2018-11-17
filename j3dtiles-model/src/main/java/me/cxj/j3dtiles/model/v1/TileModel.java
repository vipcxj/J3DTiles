package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.utils.JsonParser;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vipcxj on 2018/11/14.
 */
public interface TileModel {

    Header getHeader();

    long calcSize(JsonParser parser);

    void write(OutputStream os, JsonParser parser) throws IOException;
}
