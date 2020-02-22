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

public class ActivitesActivity extends AppCompatActivity {

    @BindView(R.id.txt_show_activity_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_show_activity_cost)
    TextView mTxtCost;
    @BindView(R.id.txt_show_activity_date)
    TextView mTxtDate;
    @BindView(R.id.txt_show_activity_inpartnership)
    TextView mTxtInPartnerShip;

    @BindView(R.id.btn_delete_activity)
    Button mBtnDeleteActivity;

    private AlertDialog mDialog;

    @OnClick(R.id.btn_delete_activity)
    void clickedDeleteActivity(){
        deleteActivity();
    }

    private void deleteActivity() {

        mDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_ACTIVITES_CHILD)
                .child(Common.selectedActivites)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(ActivitesActivity.this, "delete activity success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivitesActivity.this, HomeActivity.class));
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activites);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.activities);


        init();

        Paper.init(this);
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadACtivitiesInfo();
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
                            mBtnDeleteActivity.setVisibility(View.VISIBLE);
                        else
                            mBtnDeleteActivity.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadACtivitiesInfo() {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_ACTIVITES_CHILD)
                .child(Common.selectedActivites)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mTxtTitle.setText(dataSnapshot.child("title").getValue().toString());
                        mTxtCost.setText(dataSnapshot.child("costs").getValue().toString());
                        mTxtDate.setText(dataSnapshot.child("date").getValue().toString());
                        mTxtInPartnerShip.setText(dataSnapshot.child("inPartnership").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void init() {

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .build();
    }
}
