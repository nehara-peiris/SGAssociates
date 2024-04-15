package lk.ijse.SGA.model;

public class Deed {
    private String deedId;
    private String description;
    private String date;
    private String lawyerId;
    private String clientId;

    public Deed() {
    }

    public Deed(String deedId, String description, String date, String lawyerId, String clientId) {
        this.deedId = deedId;
        this.description = description;
        this.date = date;
        this.lawyerId = lawyerId;
        this.clientId = clientId;
    }

    public String getDeedId() {
        return deedId;
    }

    public void setDeedId(String deedId) {
        this.deedId = deedId;
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
        return "Deed{" +
                "deedId='" + deedId + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", lawyerId='" + lawyerId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}

