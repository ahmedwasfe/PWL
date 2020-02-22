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

public class StudentActivity extends AppCompatActivity {

    @BindView(R.id.txt_show_student_name)
    TextView mTxtStudentName;
    @BindView(R.id.txt_show_student_mobile)
    TextView mTxtStudentMobile;
    @BindView(R.id.txt_show_student_city)
    TextView mTxtStudentCity;
    @BindView(R.id.txt_show_student_zone)
    TextView mTxtStudentZone;

    @BindView(R.id.btn_delete_student)
    Button mBtnDeleteStudent;

    private AlertDialog mDialog;

    @OnClick(R.id.btn_delete_student)
    void clickedDeleteStudent(){

        deleteStudent();
    }

    private void deleteStudent() {

        mDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_STUDENTS_CHILD)
                .child(Common.selectedStudent)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            mDialog.dismiss();;
                            Toast.makeText(StudentActivity.this, "Deletd student success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StudentActivity.this, HomeActivity.class));
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        ButterKnife.bind(this);

        init();

        Paper.init(this);
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadStudentInfo();

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
                            mBtnDeleteStudent.setVisibility(View.VISIBLE);
                        else
                            mBtnDeleteStudent.setVisibility(View.GONE);
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

    private void loadStudentInfo() {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_STUDENTS_CHILD)
                .child(Common.selectedStudent)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mTxtStudentName.setText(dataSnapshot.child("name").getValue().toString());
                        mTxtStudentMobile.setText(dataSnapshot.child("mobile").getValue().toString());
                        mTxtStudentCity.setText(dataSnapshot.child("city").getValue().toString());
                        mTxtStudentZone.setText(dataSnapshot.child("zone").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
