package ahmet.com.pwl.SubActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class TeacherDetailsActivity extends AppCompatActivity {

    @BindView(R.id.txt_show_teacher_name)
    TextView mTxtName;
    @BindView(R.id.txt_show_teacher_specialty)
    TextView mTxtSpecialty;
    @BindView(R.id.txt_show_teacher_email)
    TextView mTxtEmail;
    @BindView(R.id.txt_show_teacher_mobile)
    TextView mTxtMobile;
    @BindView(R.id.txt_show_teacher_city)
    TextView mTxtCity;
    @BindView(R.id.txt_show_teacher_zone)
    TextView mTxtZone;
    @BindView(R.id.txt_show_teacher_certificate)
    TextView mTxtCertificate;
    @BindView(R.id.txt_show_teacher_experience)
    TextView mTxtexperience;

    @BindView(R.id.btn_delete_teacher)
    Button mBtnDeleteTeacher;

    private AlertDialog mDialog;

    @OnClick(R.id.btn_delete_teacher)
    void deleteTeacher() {

        if (Common.selectedTeacherDept.equals("1"))
            deleteTeacher(Common.KEY_TEACHER_COMPUTER_CHILD);
        else if (Common.selectedTeacherDept.equals("2"))
            deleteTeacher(Common.KEY_TEACHER_LANGUAGES_CHILD);
        else if (Common.selectedTeacherDept.equals("3"))
            deleteTeacher(Common.KEY_TEACHER_RELAYCOURSES_CHILD);
        else if (Common.selectedTeacherDept.equals("4"))
            deleteTeacher(Common.KEY_TEACHER_HUMANDEVELOPMENT_CHILD);
        else if (Common.selectedTeacherDept.equals("5"))
            deleteTeacher(Common.KEY_TEACHER_OTHERS_CHILD);
    }



    private void updateTeacher(String name, String specialty, String email, String mobile, String city, String zone, String certificates, String experience) {

        if (Common.selectedTeacherDept.equals("1"))
            updateTeacher(Common.KEY_TEACHER_COMPUTER_CHILD, "Add teacher computer success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("2"))
            updateTeacher(Common.KEY_TEACHER_LANGUAGES_CHILD, "Add teacher languages success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("3"))
            updateTeacher(Common.KEY_TEACHER_RELAYCOURSES_CHILD, "Add teacher Relay Courses success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("4"))
            updateTeacher(Common.KEY_TEACHER_HUMANDEVELOPMENT_CHILD, "Add teacher Human Development success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("5"))
            updateTeacher(Common.KEY_TEACHER_OTHERS_CHILD, "Add teacher Others success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
    }

    private void updateTeacher(String keyTeacher, String message, String name, String specialty, String email, String mobile, String city, String zone, String certificates, String experience) {

        mDialog.show();

        Map<String, Object> mMapUpdateTeacher = new HashMap<>();
        mMapUpdateTeacher.put("name", name);
        mMapUpdateTeacher.put("speciality", specialty);
        mMapUpdateTeacher.put("email", email);
        mMapUpdateTeacher.put("mobile", mobile);
        mMapUpdateTeacher.put("city", city);
        mMapUpdateTeacher.put("zone", zone);
        mMapUpdateTeacher.put("certificate", certificates);
        mMapUpdateTeacher.put("experience", experience);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TEACHERS)
                .child(keyTeacher)
                .child(Common.selectedTeacher)
                .updateChildren(mMapUpdateTeacher)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        mDialog.dismiss();
                        Toast.makeText(TeacherDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                        loadTeacherInfo();
                    }

                });
    }


    private void deleteTeacher(String keyTeacher) {

        mDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TEACHERS)
                .child(keyTeacher)
                .child(Common.selectedTeacher)
                .removeValue()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        mDialog.dismiss();
                        Toast.makeText(TeacherDetailsActivity.this, "Delete teacher success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TeacherDetailsActivity.this, TeacherDeptActivity.class));
                        finish();
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(Common.currentTeacher.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        Paper.init(this);
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadTeacherInfo();

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
                            mBtnDeleteTeacher.setVisibility(View.VISIBLE);
                        else
                            mBtnDeleteTeacher.setVisibility(View.GONE);
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

    private void loadTeacherInfo() {

        if (Common.selectedTeacherDept.equals("1"))
            loadTeacherInfo(Common.KEY_TEACHER_COMPUTER_CHILD);
        else if (Common.selectedTeacherDept.equals("2"))
            loadTeacherInfo(Common.KEY_TEACHER_LANGUAGES_CHILD);
        else if (Common.selectedTeacherDept.equals("3"))
            loadTeacherInfo(Common.KEY_TEACHER_RELAYCOURSES_CHILD);
        else if (Common.selectedTeacherDept.equals("4"))
            loadTeacherInfo(Common.KEY_TEACHER_HUMANDEVELOPMENT_CHILD);
        else if (Common.selectedTeacherDept.equals("5"))
            loadTeacherInfo(Common.KEY_TEACHER_OTHERS_CHILD);

    }

    private void loadTeacherInfo(String keyTeacher) {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TEACHERS)
                .child(keyTeacher)
                .child(Common.selectedTeacher)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mTxtName.setText(dataSnapshot.child("name").getValue().toString());
                        mTxtSpecialty.setText(dataSnapshot.child("speciality").getValue().toString());
                        mTxtEmail.setText(dataSnapshot.child("email").getValue().toString());
                        mTxtMobile.setText(dataSnapshot.child("mobile").getValue().toString());
                        mTxtCity.setText(dataSnapshot.child("city").getValue().toString());
                        mTxtZone.setText(dataSnapshot.child("zone").getValue().toString());
                        mTxtCertificate.setText(dataSnapshot.child("certificate").getValue().toString());
                        mTxtexperience.setText(dataSnapshot.child("experience").getValue().toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
