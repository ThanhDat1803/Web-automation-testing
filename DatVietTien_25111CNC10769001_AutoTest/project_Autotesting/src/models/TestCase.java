package models;

public class TestCase {
	private String testPlan;
    private String tcId;
    private String title;
    private String estimation;
    private String testType;
    private String area;
    private String precondition;
    private String step;
    private String testData;
    private String expectedResult;
    private String priority;
    private String result;
    private String author;
    private String note;
    private String date;
    // Constructor
    public TestCase(String testPlan, String tcId, String title, String estimation, String testType,
                    String area, String precondition, String step, String testData, String expectedResult,
                    String priority, String result, String author, String note, String date) {
        this.testPlan = testPlan;
        this.tcId = tcId;
        this.title = title;
        this.estimation = estimation;
        this.testType = testType;
        this.area = area;
        this.precondition = precondition;
        this.step = step;
        this.testData = testData;
        this.expectedResult = expectedResult;
        this.priority = priority;
        this.result = result;
        this.author = author;
        this.note = note;
        this.date = date;
    }
    
    public TestCase() {}
    // Getter 
    public String getTitle() { return title; }

    public String getTcId() {  return tcId; }

    public String getTestPlan() { return testPlan; }
    
    public String getEstimation() { return estimation; }
    
    public String getTestType() { return testType; }
    
    public String getArea() { return area; }
    
    public String getPrecondition() { return precondition; }
    
    public String getStep() { return step; }
    
    public String getTestData() { return testData; }
    
    public String getExpectedResult() { return expectedResult; }
    
    public String getPriority() { return priority; }
    
    public String getResult() { return result; }
    
    public String getAuthor() { return author; }
    
    public String getNote() { return note; }
    
    public String getDate() { return date; }
}

