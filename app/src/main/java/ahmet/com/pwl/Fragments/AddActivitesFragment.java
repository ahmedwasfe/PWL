package ahmet.com.pwl.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Model.Activites;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddActivitesFragment extends Fragment {

    @BindView(R.id.input_activites_title)
    EditText mInputTitle;
    @BindView(R.id.input_activites_costs)
    EditText mInputCost;
    @BindView(R.id.input_activites_inpartnership)
    EditText mInputInPartnership;

    @BindView(R.id.txt_activites_date)
    TextView mTxtDate;

    private AlertDialog mDialog;

    @OnClick(R.id.img_activites_date)
    void getDate(){
        Common.showDialogToGetDate(getActivity(), mTxtDate);
    }

    @OnClick(R.id.btn_add_activites)
    void clickedAddActicity(){

        String title = mInputTitle.getText().toString();
        double cost = Double.parseDouble(mInputCost.getText().toString());
        String inPartnership = mInputInPartnership.getText().toString();
        String date = mTxtDate.getText().toString();

        addActivity(title, cost, inPartnership, date);
    }

    private void addActivity(String title, double cost, String inPartnership, String date) {

        Activites activietes = new Activites(title, date, cost, inPartnership);

        mDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_ACTIVITES_CHILD)
                .push()
                .setValue(activietes)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            mDialog.dismiss();;
                            Toast.makeText(getActivity(), "Add Activty success", Toast.LENGTH_SHORT).show();
                            Common.setFragmrnt(ActivitesFragment.getInstance(), R.id.frame_layout_home,
                                    getActivity().getSupportFragmentManager());
                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static AddActivitesFragment instance;
    public static AddActivitesFragment getInstance(){
        return instance == null ? new AddActivitesFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_activites, container, false);

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
