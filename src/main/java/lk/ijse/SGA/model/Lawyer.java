package lk.ijse.SGA.model;

public class Lawyer {
    private String lawyerId;
    private String name;
    private String yrsOfExp;
    private String address;
    private String email;
    private String contact;

    public Lawyer() {
    }

    public Lawyer(String lawyerId, String name, String yrsOfExp, String address, String email, String contact) {
        this.lawyerId = lawyerId;
        this.name = name;
        this.yrsOfExp = yrsOfExp;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYrsOfExp() {
        return yrsOfExp;
    }

    public void setYrsOfExp(String yrsOfExp) {
        this.yrsOfExp = yrsOfExp;
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

    @Override
    public String toString() {
        return "Lawyer{" +
                "lawyerId='" + lawyerId + '\'' +
                ", name='" + name + '\'' +
                ", yrsOfExp=" + yrsOfExp +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact=" + contact +
                '}';
    }
}
