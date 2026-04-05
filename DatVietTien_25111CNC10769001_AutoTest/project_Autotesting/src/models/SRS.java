package models;

public class SRS {
	private String idSRS;
    private String title;
    private String version;
    private String pageNumber;
    private String description;
    private String publicYear;
    private String fileSrsDocument; // đường dẫn file upload

    // Constructor
    public SRS(String idSRS, String title, String version, String pageNumber,
               String description, String publicYear, String fileSrsDocument) {
        this.idSRS = idSRS;
        this.title = title;
        this.version = version;
        this.pageNumber = pageNumber;
        this.description = description;
        this.publicYear = publicYear;
        this.fileSrsDocument = fileSrsDocument;
    }
    
    public SRS() { }
    // Getters
    public String getIdSRS() {
        return idSRS;
    }
    
    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicYear() {
        return publicYear;
    }

    public String getFileSrsDocument() {
        return fileSrsDocument;
    }

}
