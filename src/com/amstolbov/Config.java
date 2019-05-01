package com.amstolbov;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File (".\\config\\resume.properties");
    private static final Config INSTANCE = new Config();
    private Properties props = new Properties();
    private Map<ParamType, String> params = new EnumMap<>(ParamType.class);

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            Arrays.stream(ParamType.values()).forEach(paramType -> params.put(paramType, props.getProperty(paramType.getParamName())));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public String getParam(ParamType paramType) {
        return params.get(paramType);
    }

    public enum ParamType {
        STORAGE_DIR("storage.dir"),
        DB_URL("db.url"),
        DB_USER("db.user"),
        DB_PASSWORD("db.password");
        private String paramName;
        ParamType(String paramName) {
            this.paramName = paramName;
        }

        public String getParamName() {
            return this.paramName;
        }
    }
}
