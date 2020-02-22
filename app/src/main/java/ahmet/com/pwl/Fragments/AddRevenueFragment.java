package ahmet.com.pwl.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Model.Revenue;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddRevenueFragment extends Fragment {

    @BindView(R.id.input_revenue_name)
    EditText mInputRevenueName;
    @BindView(R.id.input_revenue_costs)
    EditText mInputRevenueCosts;
    @BindView(R.id.input_expenses_name)
    EditText mInputExpensesname;
    @BindView(R.id.input_expenses_costs)
    EditText mInputExpensesCosts;
    @BindView(R.id.txt_revenue_date)
    TextView mTxtRevenueDate;

    private AlertDialog mDialog;

    @OnClick(R.id.img_revenue_date)
    void showRevenueDate(){
        Common.showDialogToGetDate(getActivity(), mTxtRevenueDate);
    }

    @OnClick(R.id.btn_add_revenue)
    void clickedAddRevenue(){

        String revenueName = mInputRevenueName.getText().toString();
        double revenueCosts = Double.parseDouble(mInputRevenueCosts.getText().toString());
        String expensesName = mInputExpensesname.getText().toString();
        double expensesCosts = Double.parseDouble(mInputExpensesCosts.getText().toString());
        String revenueDate = mTxtRevenueDate.getText().toString();

        addRevenue(revenueName, revenueCosts, expensesName, expensesCosts, revenueDate);
    }

    private void addRevenue(String revenueName, double revenueCosts, String expensesName, double expensesCosts, String revenueDate) {

        mDialog.show();

        Revenue revenue = new Revenue(revenueName, revenueCosts, expensesName, expensesCosts, revenueDate);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_REVENUE_CHILD)
                .push()
                .setValue(revenue)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), "Add revenue success", Toast.LENGTH_SHORT).show();
                            Common.setFragmrnt(RevenuesFragment.getInstance(), R.id.frame_layout_home,
                                    getActivity().getSupportFragmentManager());
                        }
                    }
                });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static AddRevenueFragment instance;
    public static AddRevenueFragment getInstance(){
        return instance == null ? new AddRevenueFragment() : instance;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_revenue, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        return layoutView;
    }

    private void init() {

        mDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setCancelable(false)
                .build();
    }
}
