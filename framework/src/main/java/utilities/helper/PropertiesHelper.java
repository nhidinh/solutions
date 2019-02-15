package utilities.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    private Properties properties = new Properties();;
    private InputStream inputStream = null;

    public PropertiesHelper(String propertiesFileName) throws IOException {
        try {
            inputStream = PropertiesHelper.class.getClassLoader().getResourceAsStream(propertiesFileName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("properties file '" + propertiesFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
    }

    public String getPropertyValue(String propertyKey){
        return properties.getProperty(propertyKey);
    }
}
