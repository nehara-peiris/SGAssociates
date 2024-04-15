package lk.ijse.SGA.model;

public class Lawyer {
    private String lawyerId;
    private String name;
    private int yrsOfExp;
    private String address;
    private String email;
    private int contact;

    public Lawyer() {
    }

    public Lawyer(String lawyerId, String name, int yrsOfExp, String address, String email, int contact) {
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

    public int getYrsOfExp() {
        return yrsOfExp;
    }

    public void setYrsOfExp(int yrsOfExp) {
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

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
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
