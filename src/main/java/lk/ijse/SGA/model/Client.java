package lk.ijse.SGA.model;

public class Client {

    private String clientId;
    private String name;
    private String address;
    private String email;
    private String contact;
    private String lawyerId;

    public Client() {
    }

    public Client(String clientId, String name, String address, String email, String contact, String lawyerId) {
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.lawyerId = lawyerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        lawyerId = lawyerId;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact=" + contact +
                ", lawyerId='" + lawyerId + '\'' +
                '}';
    }
}
