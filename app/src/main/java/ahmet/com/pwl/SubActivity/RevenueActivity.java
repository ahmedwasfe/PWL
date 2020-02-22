package ahmet.com.pwl.SubActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.HomeActivity;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class RevenueActivity extends AppCompatActivity {

    @BindView(R.id.txt_show_revenue_name)
    TextView mTxtRevenueName;
    @BindView(R.id.txt_show_revenue_cost)
    TextView mTxtRevenueCost;
    @BindView(R.id.txt_show_expenses_name)
    TextView mTxtExpensesName;
    @BindView(R.id.txt_show_expenses_cost)
    TextView mTxtExpensesCost;
    @BindView(R.id.txt_show_total_amount)
    TextView mTxtTotalAmount;

    @BindView(R.id.btn_delete_revenue)
    Button mBtnDeleteRevenue;

    private AlertDialog mDialog;

    @OnClick(R.id.btn_delete_revenue)
    void clickedDeleteRevenue(){
        deleteRevenue();
    }

    private void deleteRevenue() {

        mDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_REVENUE_CHILD)
                .child(Common.selectedRevenue)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(RevenueActivity.this, "Delete Revenue success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RevenueActivity.this, HomeActivity.class));
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.revenues);

        init();

        Paper.init(this);
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadRevenueInfo();
    }

    private void getUserType(String userId) {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String userType = dataSnapshot.child("userType").getValue().toString();
                        if (userType.equals(getString(R.string.admin)))
                            mBtnDeleteRevenue.setVisibility(View.VISIBLE);
                        else
                            mBtnDeleteRevenue.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void init() {

        mDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();
    }

    private void loadRevenueInfo() {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_REVENUE_CHILD)
                .child(Common.selectedRevenue)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            mTxtRevenueName.setText(dataSnapshot.child("revenueName").getValue().toString());
                            mTxtRevenueCost.setText(dataSnapshot.child("revenueCosts").getValue().toString());
                            mTxtExpensesName.setText(dataSnapshot.child("expensesName").getValue().toString());
                            mTxtExpensesCost.setText(dataSnapshot.child("expensesCosts").getValue().toString());

                            double revenueCost = Double.parseDouble(dataSnapshot.child("revenueCosts").getValue().toString());
                            double expensesCost = Double.parseDouble(dataSnapshot.child("expensesCosts").getValue().toString());

                            mTxtTotalAmount.setText(String.valueOf(revenueCost - expensesCost));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
