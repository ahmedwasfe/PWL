package ahmet.com.pwl.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Interface.LoadDepartmetMain;
import ahmet.com.pwl.Model.Courses;
import ahmet.com.pwl.Model.TeachersDepartment;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddCourseFragment extends Fragment implements LoadDepartmetMain {


    @BindView(R.id.input_course_name)
    EditText mInputCourseName;

    @BindView(R.id.spinner_department)
    MaterialSpinner mSpinnerDepatmet;
    @BindView(R.id.spinner_teachers)
    MaterialSpinner mSpinnerTeachers;

    @BindView(R.id.txt_start_course_date)
    TextView mTxtStartCourse;
    @BindView(R.id.txt_end_course_date)
    TextView mTxtEndCourse;

    private DatePickerDialog mDatePicker;
    private AlertDialog mDialog;

    private String teacherName;

    private LoadDepartmetMain loadDepartmetMain;

    @OnClick(R.id.img_show_start_date)
    void dialogStartCourse(){

        showDialogToGetDate(mTxtStartCourse);
    }

    @OnClick(R.id.img_show_end_date)
    void dialogEndCourse(){

        showDialogToGetDate(mTxtEndCourse);
    }

    @OnClick(R.id.btn_add_course)
    void addNewCourse(){

        String courseName = mInputCourseName.getText().toString();
        String startCourse = mTxtStartCourse.getText().toString();
        String endCourse = mTxtEndCourse.getText().toString();

        addNewCourse(courseName, teacherName, startCourse, endCourse);
    }

    private void addNewCourse(String courseName, String teacherName, String startCourse, String endCourse) {

        mDialog.show();

        Courses courses = new Courses(courseName, teacherName, startCourse, endCourse);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TRAINING_COURSES_CHILD)
                .push()
                .setValue(courses)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), "Add Course success", Toast.LENGTH_SHORT).show();
                            Common.setFragmrnt(CoursesFragment.getInstance(), R.id.frame_layout_home,
                                    getActivity().getSupportFragmentManager());
                        }
                    }
                });
    }

    private void showDialogToGetDate(TextView mTxtDateCourse) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = new DatePickerDialog(getActivity(), (view, year1, month1, dayOfMonth) -> mTxtDateCourse.setText(dayOfMonth + "/" +( month1 + 1 )+ "/" + year1), year, month, day);

        mDatePicker.show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static AddCourseFragment instance;
    public static AddCourseFragment getInstance(){
        return instance == null ? new AddCourseFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_courses, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        loadTeachersDepartment();

        return layoutView;
    }

    private void loadTeachersDepartment() {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TEACHER_DEPARTMENT)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<TeachersDepartment> mListTeachersDepartments = new ArrayList<>();
                        TeachersDepartment department = null;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            department = snapshot.getValue(TeachersDepartment.class);
                            Log.d("DEPARTMENT", department.getNameEn());
                            mListTeachersDepartments.add(department);
                        }
                        loadDepartmetMain.onLoadDepartmetMainSuccess(mListTeachersDepartments);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        loadDepartmetMain.onLoadDepartmetMainFailed(databaseError.getMessage());
                    }
                });
    }

    private void init() {

        loadDepartmetMain = this;

        mDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(getActivity())
                .build();
    }

    @Override
    public void onLoadDepartmetMainSuccess(List<TeachersDepartment> mListTeachersDepartment) {

        mSpinnerDepatmet.setItems(mListTeachersDepartment);
//        TeachersDepartmentAdapter departmentAdapter = new TeachersDepartmentAdapter(getActivity(),
//                R.layout.raw_teacher,
//                R.id.txt_department_main_name,
//                null,
//                mListTeachersDepartment);
//        mSpinnerDepatmet.setAdapter(departmentAdapter);
    }

    @Override
    public void onLoadDepartmetMainFailed(String error) {

        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
