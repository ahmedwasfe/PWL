package ahmet.com.pwl.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Model.Teacher;
import ahmet.com.pwl.R;
import ahmet.com.pwl.SubActivity.TeacherDeptActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddTeacherFragment extends Fragment {

    @BindView(R.id.input_teacher_computer_name)
    EditText mInputTeacherName;
    @BindView(R.id.input_teacher_computer_specialty)
    EditText mInputTeacherSpecialty;
    @BindView(R.id.input_teacher_computer_email)
    EditText mInputTeacherEmail;
    @BindView(R.id.input_teacher_computer_mobile)
    EditText mInputTeacherMobile;
    @BindView(R.id.input_teacher_computer_city)
    EditText mInputTeacherCity;
    @BindView(R.id.input_teacher_computer_zone)
    EditText mInputTeacherZone;
    @BindView(R.id.input_teacher_computer_certificates)
    EditText mInputTeacherCertificates;
    @BindView(R.id.input_teacher_computer_experience)
    EditText mInputTeacherExperience;


    private AlertDialog mDialog;


    @OnClick(R.id.btn_add_teacher_computer)
    void addNewTeacher(){

        String name = mInputTeacherName.getText().toString();
        String specialty = mInputTeacherSpecialty.getText().toString();
        String email = mInputTeacherEmail.getText().toString();
        String mobile = mInputTeacherMobile.getText().toString();
        String city = mInputTeacherCity.getText().toString();
        String zone = mInputTeacherZone.getText().toString();
        String certificates = mInputTeacherCertificates.getText().toString();
        String experience = mInputTeacherExperience.getText().toString();

        addTeacher(name, specialty, email, mobile, city, zone, certificates, experience);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static AddTeacherFragment instance;
    public static AddTeacherFragment getInstance(){
        return instance == null ? new AddTeacherFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_teacher, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        return layoutView;
    }

    private void addTeacher(String name, String specialty, String email, String mobile, String city, String zone, String certificates, String experience){

        if (Common.selectedTeacherDept.equals("1"))
            addTeacher(Common.KEY_TEACHER_COMPUTER_CHILD,"Add teacher computer success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("2"))
            addTeacher(Common.KEY_TEACHER_LANGUAGES_CHILD,"Add teacher languages success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("3"))
            addTeacher(Common.KEY_TEACHER_RELAYCOURSES_CHILD,"Add teacher Relay Courses success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("4"))
            addTeacher(Common.KEY_TEACHER_HUMANDEVELOPMENT_CHILD,"Add teacher Human Development success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
        else if (Common.selectedTeacherDept.equals("5"))
            addTeacher(Common.KEY_TEACHER_OTHERS_CHILD,"Add teacher Others success",
                    name, specialty, email, mobile, city, zone, certificates, experience);
    }

    private void init(){
        mDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(getActivity())
                .build();
    }

    private void addTeacher(String reference, String message, String name, String specialty, String email, String mobile, String city, String zone, String certificates, String experience) {

        mDialog.show();

        Teacher teacher = new Teacher(name, specialty, email, mobile, city, zone, certificates, experience);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_TEACHERS)
                .child(reference)
                .push()
                .setValue(teacher)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        mDialog.dismiss();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), TeacherDeptActivity.class));
                    }
                });
    }
}
