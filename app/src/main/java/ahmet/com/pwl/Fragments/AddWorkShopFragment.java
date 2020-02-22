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
import ahmet.com.pwl.Model.WorkShop;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddWorkShopFragment extends Fragment {


    @BindView(R.id.input_work_shop_title)
    EditText mInputTitle;
    @BindView(R.id.input_work_shop_costs)
    EditText mInputCosts;
    @BindView(R.id.txt_work_shop_date)
    TextView mTxtWorkShopDate;

    private AlertDialog mDialog;

    @OnClick(R.id.img_work_shop_date)
    void dialogWorkShop(){
        Common.showDialogToGetDate(getActivity(), mTxtWorkShopDate);
    }

    @OnClick(R.id.btn_add_work_shop)
    void clickedAddWorkShop(){

        String title = mInputTitle.getText().toString();
        String costs = mInputCosts.getText().toString();
        String date = mTxtWorkShopDate.getText().toString();

        String costsValue = costs + " ILS";

        addWorkShop(title, costsValue, date);
    }

    private void addWorkShop(String title, String costs, String date) {

        mDialog.show();

        WorkShop workShop = new WorkShop(title, date, costs);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_WORKSHOP_CHILD)
                .push()
                .setValue(workShop)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), "Add workshop success", Toast.LENGTH_SHORT).show();
                            Common.setFragmrnt(WorkShopFragment.getInstance(), R.id.frame_layout_home,
                                    getActivity().getSupportFragmentManager());
                        }
                    }
                });
    }


    private static AddWorkShopFragment instance;
    public static AddWorkShopFragment getInstance(){
        return instance == null ? new AddWorkShopFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_work_shop, container, false);

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
