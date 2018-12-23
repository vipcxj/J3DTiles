package me.cxj.j3dtiles.ifc.tools;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by vipcxj on 2018/12/12.
 */
public class Metas {
    static Properties properties = new Properties();
    static {
        try {
            properties.load(Metas.class.getResourceAsStream("/meta.properties"));
        } catch (IOException e) {
            throw new Error("This is impossible.", e);
        }
    }
}
