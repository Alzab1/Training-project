package core.configuration;

import core.browser.DriverType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final String RESOURCES_PATH = "src/main/resources/";
    private static Properties properties;

    public static void readProperties() {
        if (properties == null) {
            properties = new Properties();
        }
        try {
            properties.load(new FileReader(new File(RESOURCES_PATH, "env.properties")));
            properties.load(new FileReader(new File(RESOURCES_PATH, "chrome.properties")));
            properties.load(new FileReader(new File(RESOURCES_PATH, "browser.properties")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void initProperties() {
        if (properties == null) {
            readProperties();
        }
    }

    public static DriverType getDriverType() {
        initProperties();
        return DriverType.valueOf(properties.getProperty("browserType"));
    }

    public static String getMainUrl() {
        initProperties();
        return properties.getProperty("mainUrl");
    }

    public static Boolean getHeadless() {
        initProperties();
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }

    public static String getWindowSize() {
        initProperties();
        return properties.getProperty("window-size");
    }


}