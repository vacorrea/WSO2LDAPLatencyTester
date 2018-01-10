package org.wso2.carbon.identity.ldap.tester.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private Properties properties = new Properties();

    private ConfigLoader() {

        init();
    }

    private static class LazyHolder {

        private static final ConfigLoader INSTANCE = new ConfigLoader();
    }

    public static ConfigLoader getInstance() {

        return LazyHolder.INSTANCE;
    }

    private void init() {

        try (InputStream input = new FileInputStream("resources/test.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Can't find/read 'resources/test.properties' file.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public String getProperty(String key) {

        return properties.getProperty(key);
    }
}
