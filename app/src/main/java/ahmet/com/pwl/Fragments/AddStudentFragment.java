package ahmet.com.pwl.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Model.Student;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddStudentFragment extends Fragment {

    @BindView(R.id.input_student_name)
    EditText mInputStudentName;
    @BindView(R.id.input_student_mobile)
    EditText mInputStudentMobile;
    @BindView(R.id.input_student_city)
    EditText mInputStudentCity;
    @BindView(R.id.input_student_zone)
    EditText mInputStudentZone;

    private AlertDialog mDialog;

    @OnClick(R.id.btn_add_student)
    void addNewStudent(){

        String name = mInputStudentName.getText().toString();
        String mobile = mInputStudentMobile.getText().toString();
        String city = mInputStudentCity.getText().toString();
        String zone = mInputStudentZone.getText().toString();

        addNewStudent(name, mobile, city, zone);
    }

    private void addNewStudent(String name, String mobile, String city, String zone) {

        mDialog.show();

        Student student = new Student(name, mobile, city, zone);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_STUDENTS_CHILD)
                .push()
                .setValue(student)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), "Add student success", Toast.LENGTH_SHORT).show();
                            Common.setFragmrnt(StudentFragment.getInstance(), R.id.frame_layout_home,
                                    getActivity().getSupportFragmentManager());
                        }
                    }
                });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static AddStudentFragment instance;
    public static AddStudentFragment getInstance(){
        return instance == null ? new AddStudentFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_student, container, false);

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
