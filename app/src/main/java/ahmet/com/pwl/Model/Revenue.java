package ahmet.com.pwl.Model;

public class Revenue {

    private String revenueName, expensesName, revenueDate;
    private double revenueCosts, expensesCosts;

    public Revenue() {
    }

    public Revenue(String revenueName, double revenueCosts, String expensesName, double expensesCosts, String revenueDate) {

        this.revenueName = revenueName;
        this.revenueCosts = revenueCosts;
        this.expensesName = expensesName;
        this.expensesCosts = expensesCosts;
        this.revenueDate = revenueDate;
    }

    public String getRevenueName() {
        return revenueName;
    }

    public void setRevenueName(String revenueName) {
        this.revenueName = revenueName;
    }

    public double getRevenueCosts() {
        return revenueCosts;
    }

    public void setRevenueCosts(double revenueCosts) {
        this.revenueCosts = revenueCosts;
    }

    public String getExpensesName() {
        return expensesName;
    }

    public void setExpensesName(String expensesName) {
        this.expensesName = expensesName;
    }

    public double getExpensesCosts() {
        return expensesCosts;
    }

    public void setExpensesCosts(double expensesCosts) {
        this.expensesCosts = expensesCosts;
    }

    public String getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(String revenueDate) {
        this.revenueDate = revenueDate;
    }
}
