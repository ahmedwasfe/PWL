package ahmet.com.pwl.Model;

public class Teacher {

    private String name, speciality, email, mobile, city, zone, certificate, experience;

    public Teacher() {
    }

    public Teacher(String name, String speciality, String email, String mobile, String city, String zone, String certificate, String experience) {
        this.name = name;
        this.speciality = speciality;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.zone = zone;
        this.certificate = certificate;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
