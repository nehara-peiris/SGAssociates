package lk.ijse.SGA.model;

public class Court {

    private String courtId;
    private String location;

    public Court() {
    }

    public Court(String courtId, String location) {
        this.courtId = courtId;
        this.location = location;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Court{" +
                "courtId='" + courtId + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
