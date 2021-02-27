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

    private Integer fileRefreshRate;
    private String dateTimeFormat;

    private ApplicationProperties(){}

    public static ApplicationProperties getInstance(){
        return instance;
    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public void setInputRootDir(String inputRootDir) {
        this.inputRootDir = inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public void setOutputRootDir(String outputRootDir) {
        this.outputRootDir = outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public void setCrewFileName(String crewFileName) {
        this.crewFileName = crewFileName;
    }

    public String getMissionFileName() {
        return missionFileName;
    }

    public void setMissionFileName(String missionFileName) {
        this.missionFileName = missionFileName;
    }

    public String getSpaceShipFileName() {
        return spaceShipFileName;
    }

    public void setSpaceShipFileName(String spaceShipFileName) {
        this.spaceShipFileName = spaceShipFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public void setFileRefreshRate(Integer fileRefreshRate) {
        this.fileRefreshRate = fileRefreshRate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }
}
