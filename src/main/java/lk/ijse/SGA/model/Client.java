package lk.ijse.SGA.model;

public class Client {

    private String clientId;
    private String name;
    private String address;
    private String email;
    private int contact;
    private String LawyerId;

    public Client() {
    }

    public Client(String clientId, String name, String address, String email, int contact, String lawyerId) {
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        LawyerId = lawyerId;
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

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getLawyerId() {
        return LawyerId;
    }

    public void setLawyerId(String lawyerId) {
        LawyerId = lawyerId;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact=" + contact +
                ", LawyerId='" + LawyerId + '\'' +
                '}';
    }
}
