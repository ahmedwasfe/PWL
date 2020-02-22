package ahmet.com.pwl.Model;

public class Student {

    private String name, mobile, city, zone;

    public Student() {
    }

    public Student(String name, String mobile, String city, String zone) {
        this.name = name;
        this.mobile = mobile;
        this.city = city;
        this.zone = zone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
