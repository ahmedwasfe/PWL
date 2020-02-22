package ahmet.com.pwl.Model;

import java.io.Serializable;

public class Activites {

    private String title, date, inPartnership;
    private double costs;

    public Activites() {
    }

    public Activites(String title, String date, double costs, String inPartnership) {
        this.title = title;
        this.date = date;
        this.costs = costs;
        this.inPartnership = inPartnership;
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

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    public String getInPartnership() {
        return inPartnership;
    }

    public void setInPartnership(String inPartnership) {
        this.inPartnership = inPartnership;
    }
}
