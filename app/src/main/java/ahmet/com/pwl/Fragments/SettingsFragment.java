package ahmet.com.pwl.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.LoginActivity;
import ahmet.com.pwl.Model.Users;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class SettingsFragment extends Fragment {


    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_username)
    TextView mTxtUsername;
    @BindView(R.id.txt_user_type)
    TextView mTxtUserType;

    @BindView(R.id.card_add_user)
    CardView mCardAddUser;
    @BindView(R.id.card_user_type)
    CardView mCardUserType;

    private String userID;
    private String userType;
    private String newUserType;

    private android.app.AlertDialog mDialog;

    @OnClick(R.id.card_user_name)
    void clickedName() {
        showDialogUpdateName();
    }

    @OnClick(R.id.card_user_type)
    void clickedUserType() {
        showDialogUpdateUserType();
    }

    @OnClick(R.id.card_add_user)
    void clickedAddUser() {
        showDialogAddNewUser();
    }

    private void showDialogAddNewUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflater = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_user, null, false);
        builder.setView(inflater);

        EditText mInputName = inflater.findViewById(R.id.input_add_user_name);
        EditText mInputUsername = inflater.findViewById(R.id.input_add_user_username);
        EditText mInputPassword = inflater.findViewById(R.id.input_add_user_password);

        MaterialSpinner newUserTypeSpinner = inflater.findViewById(R.id.spinner_add_user_type);
        Common.selectUserType(getActivity(), newUserTypeSpinner);

        newUserTypeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                newUserType = item.toString();
            }
        });

        inflater.findViewById(R.id.btn_add_new_user)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = mInputName.getText().toString();
                        String username = mInputUsername.getText().toString();
                        String password = mInputPassword.getText().toString();

                        if (TextUtils.isEmpty(newUserType))
                            Toast.makeText(getActivity(), getString(R.string.please_select_user_type), Toast.LENGTH_SHORT).show();
                        else
                            addNewUser(name, newUserType, username, password);
                    }
                });

        builder.create();
        builder.show();
    }

    private void addNewUser(String name, String newUserType, String username, String password) {

        mDialog.show();

        Users users = new Users(name,username, password, newUserType);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .push()
                .setValue(users)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), getString(R.string.add_new_user_success), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showDialogUpdateUserType() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View inflater = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_user_type, null, false);

        builder.setView(inflater);
        // builder.setView(R.layout.dialog_add_website);

        MaterialSpinner userTypeSpinner = inflater.findViewById(R.id.spinner_update_user_type);
        Common.selectUserType(getActivity(), userTypeSpinner);

        userTypeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                userType = item.toString();
            }
        });

        inflater.findViewById(R.id.btn_update_user_type).setOnClickListener(v -> {


            if (TextUtils.isEmpty(userType)) {
                Toast.makeText(getActivity(), getString(R.string.please_select_user_type), Toast.LENGTH_SHORT).show();
            } else {
                updateUserType(userType);
            }
        });


        builder.create();
        builder.show();
    }

    private void updateUserType(String userType) {

        mDialog.show();

        Map<String, Object> mMapUserType = new HashMap<>();
        mMapUserType.put("userType", userType);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .child(userID)
                .updateChildren(mMapUserType)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), getString(R.string.update_user_type_success), Toast.LENGTH_SHORT).show();
                            loadUserInfo(userID);
                        }
                    }
                });
    }

    private void showDialogUpdateName() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View inflater = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_name, null, false);

        builder.setView(inflater);
        // builder.setView(R.layout.dialog_add_website);

        EditText mInputName = inflater.findViewById(R.id.input_update_name);

        inflater.findViewById(R.id.btn_update_name).setOnClickListener(v -> {

            String name = mInputName.getText().toString();

            if (TextUtils.isEmpty(name)) {
                mInputName.setError(getString(R.string.please_enter_name));
                return;
            } else
                updateName(name);
        });


        builder.create();
        builder.show();

    }

    private void updateName(String name) {

        mDialog.show();

        Map<String, Object> mMapName = new HashMap<>();
        mMapName.put("name", name);

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .child(userID)
                .updateChildren(mMapName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), getString(R.string.update_name_success), Toast.LENGTH_SHORT).show();
                            loadUserInfo(userID);
                        }
                    }
                });
    }

    @OnClick(R.id.txt_log_out)
    void logOut() {

        Paper.init(getActivity());
        Paper.book().delete(Common.KEY_LOGIN);
        Paper.book().delete(Common.KEY_USER_ID);

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmation)
                .setMessage(R.string.are_you_sure_log_out)
                .setCancelable(true)
                .setPositiveButton(R.string.log_out, (dialogInterface, i) -> {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();

                }).setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static SettingsFragment instance;
    public static SettingsFragment getInstance() {
        return instance == null ? new SettingsFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        Paper.init(getActivity());
        userID = Paper.book().read(Common.KEY_USER_ID);
        if (userID != null)
            loadUserInfo(userID);

        return layoutView;
    }

    private void init() {

        mDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setCancelable(false)
                .build();
    }

    private void loadUserInfo(String userID) {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mTxtName.setText(dataSnapshot.child("name").getValue().toString());
                        mTxtUsername.setText(dataSnapshot.child("username").getValue().toString());
                        mTxtUserType.setText(dataSnapshot.child("userType").getValue().toString());

                        String userType = dataSnapshot.child("userType").getValue().toString();
                        if (userType.equals(getString(R.string.admin))){
                            mCardAddUser.setVisibility(View.VISIBLE);
                            mCardUserType.setEnabled(true);
                        }else{
                            mCardAddUser.setVisibility(View.GONE);
                            mCardUserType.setEnabled(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
