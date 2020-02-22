package ahmet.com.pwl.Model;

public class WorkShop {

    private String title,  date, costs;

    public WorkShop() {
    }

    public WorkShop(String title, String date, String costs) {
        this.title = title;
        this.date = date;
        this.costs = costs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCosts() {
        return costs;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }
}
