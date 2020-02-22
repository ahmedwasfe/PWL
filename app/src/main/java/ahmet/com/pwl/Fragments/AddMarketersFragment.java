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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.HomeActivity;
import ahmet.com.pwl.Model.Marketer;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddMarketersFragment extends Fragment {

    @BindView(R.id.input_marketer_name)
    EditText mInputMarketerName;
    @BindView(R.id.input_marketer_mobile)
    EditText mInputMarketerMobile;

    private AlertDialog mDialog;

    @OnClick(R.id.btn_add_marketer)
    void cilckedAddMarketer(){

        String name = mInputMarketerName.getText().toString();
        String mobile = mInputMarketerMobile.getText().toString();

        addMarketer(name, mobile);
    }

    private void addMarketer(String name, String mobile) {

        mDialog.show();;

        Marketer marketer = new Marketer(name, mobile);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_MARKETERS_CHILD)
                .push()
                .setValue(marketer)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), "Add Marketer success", Toast.LENGTH_SHORT).show();
                            Common.setFragmrnt(MarketersFragment.getInstance(), R.id.frame_layout_home,
                                    getActivity().getSupportFragmentManager());
                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static AddMarketersFragment instance;
    public static AddMarketersFragment getInstance(){
        return instance == null ? new AddMarketersFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_marketer, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        return layoutView;
    }

    private void init() {

        mDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(getActivity())
                .build();
    }
}
