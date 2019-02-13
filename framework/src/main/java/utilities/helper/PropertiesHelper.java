package utilities.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    private Properties properties = new Properties();;
    private InputStream inputStream = null;

    public PropertiesHelper(String propertiesName) throws IOException {
        try {
            inputStream = PropertiesHelper.class.getClassLoader().getResourceAsStream(propertiesName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("properties file '" + propertiesName + "' not found in the classpath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
    }

    public String getPropertyName(String propertyName){
        return properties.getProperty(propertyName);
    }
}
