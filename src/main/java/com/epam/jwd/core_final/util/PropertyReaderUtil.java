package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private static final Properties properties = new Properties();

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        ApplicationProperties appProperties = ApplicationProperties.getInstance();

        try (InputStream input = PropertyReaderUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
            appProperties.setInputRootDir(properties.getProperty("inputRootDir"));
            appProperties.setOutputRootDir(properties.getProperty("outputRootDir"));
            appProperties.setCrewFileName(properties.getProperty("crewFileName"));
            appProperties.setMissionFileName(properties.getProperty("missionsFileName"));
            appProperties.setSpaceShipFileName(properties.getProperty("spaceshipsFileName"));
            appProperties.setSpaceMapFileName(properties.getProperty("spaceMapFileName"));
            appProperties.setFileRefreshRate(Integer.parseInt(properties.getProperty("fileRefreshRate")));
            appProperties.setDateTimeFormat(properties.getProperty("dateTimeFormat"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        logger.debug("Application properties loaded");
    }
}
