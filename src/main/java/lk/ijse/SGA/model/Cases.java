package lk.ijse.SGA.model;

public class Cases {

    private String caseId;
    private String description;
    private String date;
    private String lawyerId;
    private String clientId;

    public Cases() {
    }

    public Cases(String caseId, String description, String date, String lawyerId, String clientId) {
        this.caseId = caseId;
        this.description = description;
        this.date = date;
        this.lawyerId = lawyerId;
        this.clientId = clientId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Cases{" +
                "caseId='" + caseId + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", lawyerId='" + lawyerId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
