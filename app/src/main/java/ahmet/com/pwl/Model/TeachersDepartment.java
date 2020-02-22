package ahmet.com.pwl.Model;

public class TeachersDepartment {

    private String nameEn, nameAr, icon;
    private int image;

    public TeachersDepartment() {
    }

    public TeachersDepartment(String nameEn, String nameAr, int image) {
        this.nameEn = nameEn;
        this.nameAr = nameAr;
        this.image = image;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }
}
