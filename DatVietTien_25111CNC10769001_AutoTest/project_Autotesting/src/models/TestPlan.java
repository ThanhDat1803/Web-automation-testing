package models;

public class TestPlan {
	private String title;
	private String srs;
    private String version;
    private String description;
    private String startDate;
    private String endDate;
    private String filePath;
    // Constructor
    public TestPlan(String title, String srs, String version, String description, String startDate, String endDate, String filePath) {
        this.title = title;
        this.srs = srs;
        this.version = version;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filePath = filePath;
    }
    // Getter 
    public String getTitle() { return title; }
    public String getSRS() { return srs; }
    public String getVersion() { return version; }
    public String getDescription() { return description; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getFilePath() { return filePath; }
}
