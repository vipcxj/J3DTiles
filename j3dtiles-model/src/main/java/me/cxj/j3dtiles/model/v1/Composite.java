package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.utils.JsonParser;
import me.cxj.j3dtiles.utils.LittleEndianDataInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by vipcxj on 2018/11/14.
 */
public class Composite implements TileModel {

    private CompositeHeader header;
    private List<TileModel> tiles;

    @Override
    public CompositeHeader getHeader() {
        return header;
    }

    public List<TileModel> getTiles() {
        return Collections.unmodifiableList(tiles);
    }

    public TileModel getTile(int index) {
        return tiles.get(index);
    }

    public TileModel setTile(int index, TileModel tile) {
        return tiles.set(index, tile);
    }

    public void addTile(TileModel tile) {
        tiles.add(tile);
        header.setTilesLength(tiles.size());
    }

    public void removeTile(int index) {
        tiles.remove(index);
    }

    public static Composite read(InputStream is, JsonParser parser, boolean hasReadMagic, boolean hasReadVersion) throws IOException {
        Composite instance = new Composite();
        instance.header = CompositeHeader.read(is, hasReadMagic, hasReadVersion);
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);
        int tilesLength = instance.header.getTilesLength();
        instance.tiles = new ArrayList<>();
        byte[] magic = new byte[4];
        for (int i = 0; i < tilesLength; ++i) {
            dis.readFully(magic);
            TileModel model;
            if (Arrays.equals(magic, B3dmHeader.magic)) {
                model = B3dm.read(is, parser, true, false);
            } else if (Arrays.equals(magic, I3dmHeader.magic)) {
                model = I3dm.read(is, parser, true, false);
            } else if (Arrays.equals(magic, PointCloudHeader.magic)) {
                model = I3dm.read(is, parser, true, false);
            } else if (Arrays.equals(magic, CompositeHeader.magic)) {
                model = read(is, parser, true, false);
            } else {
                throw new IllegalArgumentException("unrecognized magic: " + new String(magic, StandardCharsets.UTF_8) + ".");
            }
            instance.tiles.add(model);
        }
        return instance;
    }

    @Override
    public void write(OutputStream os, JsonParser parser) throws IOException {
        long size = calcSize(parser);
        header.setByteLength((int) size);
        for (TileModel tile : tiles) {
            tile.write(os, parser);
        }
    }

    public long calcSize(JsonParser parser) {
        return header.getHeaderLength() + tiles.stream().mapToLong(tile -> tile.calcSize(parser)).sum();
    }
}
