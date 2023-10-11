package org.goit.connection_properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private String url;
    private String user;
    private String password;

    public PropertiesLoader (String propertiesFilePath){
       loadProperties(propertiesFilePath);
    }

    private void loadProperties(String propertiesFilePath){
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)){
            properties.load(fis);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
