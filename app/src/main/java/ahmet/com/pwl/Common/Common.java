package ahmet.com.pwl.Common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ahmet.com.pwl.Model.Activites;
import ahmet.com.pwl.Model.Courses;
import ahmet.com.pwl.Model.Revenue;
import ahmet.com.pwl.Model.Student;
import ahmet.com.pwl.Model.TeachersDepartment;
import ahmet.com.pwl.Model.Teacher;
import ahmet.com.pwl.Model.Users;
import ahmet.com.pwl.R;

public class Common {


    public static final String KEY_LOGIN = "LogIn";
    public static final String KEY_USER_ID = "UserId";

    // Teachers Department
    public static final String KEY_TEACHER_DEPARTMENT = "TeachersDepartment";

    public static final String KEY_TEACHERS = "Teachers";
    public static final String KEY_TEACHER_COMPUTER_CHILD = "TeachersComputer";
    public static final String KEY_TEACHER_LANGUAGES_CHILD = "TeachersLanguages";
    public static final String KEY_TEACHER_RELAYCOURSES_CHILD = "TeachersRelayCourses";
    public static final String KEY_TEACHER_HUMANDEVELOPMENT_CHILD = "TeachersHumanDevelopment";
    public static final String KEY_TEACHER_OTHERS_CHILD = "TeachersOthers";
    public static final String KEY_USERS_CHILD = "Users";

    // Department
    public static final String KEY_TRAINING_COURSES_CHILD = "TrainingCourses";
    public static final String KEY_WORKSHOP_CHILD = "Workshops";
    public static final String KEY_STUDENTS_CHILD = "Students";
    public static final String KEY_MARKETERS_CHILD = "Marketers";
    public static final String KEY_ACTIVITES_CHILD = "Activites";
    public static final String KEY_REVENUE_CHILD = "Revenues";

    public static TeachersDepartment teachersDepartment;
    public static Teacher currentTeacher;
    public static Courses currentCourses;
    public static Student currentStudent;
    public static Revenue currentRevenue;
    public static Activites currentActivites;
    public static Users currentUser;

    public static String selectedTeacher;
    public static String selectedTeacherDept;
    public static String selectedCourses;
    public static String selectedStudent;
    public static String selectedRevenue;
    public static String selectedActivites;
    public static String selectedUser;

    public static void setFragmrnt(Fragment fragmrnt, int id, FragmentManager fragmentManager){
        fragmentManager.beginTransaction ()
                .replace (id, fragmrnt)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    public static void showDialogToGetDate(Context mContext, TextView mTxtDateCourse) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(mContext, (view, year1, month1, dayOfMonth) -> mTxtDateCourse.setText(dayOfMonth + "/" +( month1 + 1 )+ "/" + year1), year, month, day);

        mDatePicker.show();
    }

    public static void selectUserType(Context mContext, MaterialSpinner materialSpinner){

        List<String> mlitsUserType = new ArrayList<>();
        mlitsUserType.add(mContext.getString(R.string.please_select_user_type));
        mlitsUserType.add(mContext.getString(R.string.admin));
        mlitsUserType.add(mContext.getString(R.string.employee));

        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, mlitsUserType);
        materialSpinner.setAdapter(arrayAdapter);
    }


}
