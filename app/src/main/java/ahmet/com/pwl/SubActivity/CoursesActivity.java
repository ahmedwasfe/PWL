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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.HomeActivity;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class CoursesActivity extends AppCompatActivity {

    @BindView(R.id.txt_show_course_name)
    TextView mTxtCourseName;
    @BindView(R.id.txt_show_course_teacher_name)
    TextView mTxtCourseTeacher;
    @BindView(R.id.txt_show_course_start)
    TextView mTxtStartCourse;
    @BindView(R.id.txt_show_course_end)
    TextView mTxtEndCourse;

    @BindView(R.id.btn_delete_course)
    Button mBtnDeleteCourse;

    private AlertDialog mDialog;



    @OnClick(R.id.btn_delete_course)
    void clickedDeleteCourse(){

        deleteCourse();
    }


    private void updateCourse(String course, String teacher, String startCourse, String endCourse) {

        mDialog.show();

        Map<String, Object> mMapUpdateCourse = new HashMap<>();
        mMapUpdateCourse.put("course", course);
        mMapUpdateCourse.put("teacher", teacher);
        mMapUpdateCourse.put("startCourse", startCourse);
        mMapUpdateCourse.put("endCourse", endCourse);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TRAINING_COURSES_CHILD)
                .child(Common.selectedCourses)
                .updateChildren(mMapUpdateCourse)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(CoursesActivity.this, "Update course success", Toast.LENGTH_SHORT).show();
                            //loadCourseInfoFromDatabase();
                        }
                    }
                });
    }

    private void deleteCourse() {

        mDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TRAINING_COURSES_CHILD)
                .child(Common.selectedCourses)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(CoursesActivity.this, "Deleted course success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CoursesActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(R.string.courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        Paper.init(this);
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadCourseInfo();
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
                            mBtnDeleteCourse.setVisibility(View.VISIBLE);
                        else
                            mBtnDeleteCourse.setVisibility(View.GONE);
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

    private void loadCourseInfo() {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TRAINING_COURSES_CHILD)
                .child(Common.selectedCourses)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mTxtCourseName.setText(dataSnapshot.child("course").getValue().toString());
                        mTxtCourseTeacher.setText(dataSnapshot.child("teacher").getValue().toString());
                        mTxtStartCourse.setText(dataSnapshot.child("startCourse").getValue().toString());
                        mTxtEndCourse.setText(dataSnapshot.child("endCourse").getValue().toString());
//
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
