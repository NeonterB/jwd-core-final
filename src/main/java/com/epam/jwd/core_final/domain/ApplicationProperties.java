package com.epam.jwd.core_final.domain;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public class ApplicationProperties {
    private static ApplicationProperties instance = new ApplicationProperties();

    private String inputRootDir;
    private String outputRootDir;
    private String crewFileName;
    private String missionFileName;
    private String spaceShipFileName;
    private String spaceMapFileName;

    private Integer fileRefreshRate;
    private String dateTimeFormat;

    private ApplicationProperties() {
    }


    public static ApplicationProperties getInstance() {
        return instance;
    }

    public String getCrewFileDir() {
        return inputRootDir + '/' + crewFileName;
    }

    public String getSpaceshipFileDir() {
        return inputRootDir + '/' + spaceShipFileName;
    }

    public String getSpaceMapFileDir() {
        return inputRootDir + '/' + spaceMapFileName;
    }

    public void setInputRootDir(String inputRootDir) {
        this.inputRootDir = inputRootDir;
    }

    public void setOutputRootDir(String outputRootDir) {
        this.outputRootDir = outputRootDir;
    }

    public void setCrewFileName(String crewFileName) {
        this.crewFileName = crewFileName;
    }

    public void setMissionFileName(String missionFileName) {
        this.missionFileName = missionFileName;
    }

    public void setSpaceShipFileName(String spaceShipFileName) {
        this.spaceShipFileName = spaceShipFileName;
    }

    public void setSpaceMapFileName(String spaceMapFileName) {
        this.spaceMapFileName = spaceMapFileName;
    }

    public void setFileRefreshRate(Integer fileRefreshRate) {
        this.fileRefreshRate = fileRefreshRate;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionFileName() {
        return missionFileName;
    }

    public String getSpaceShipFileName() {
        return spaceShipFileName;
    }

    public String getSpaceMapFileName() {
        return spaceMapFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
