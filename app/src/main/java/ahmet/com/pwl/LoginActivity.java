package ahmet.com.pwl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Model.Users;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_username)
    EditText mInputUsername;
    @BindView(R.id.input_password)
    EditText mInputpassword;

    private AlertDialog mDialog;

    @OnClick(R.id.btn_login)
    void clickedToLogin() {

        String username = mInputUsername.getText().toString();
        String password = mInputpassword.getText().toString();

        checkUser(username, password);
    }

    private void checkUser(String username, String password) {


        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .orderByChild("username").equalTo(username)
                .limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        if (!dataSnapshot.exists()) {
                            createUser(username, password);
                        } else {
                            login(username, password);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void login(String username, String password) {

        mDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .orderByChild("username").equalTo(username)
                //.orderByChild("password").equalTo(password)
                .limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            String user = snapshot.child("username").getValue().toString();
                            String pass = snapshot.child("password").getValue().toString();

                            if (user.equals(username) && pass.equals(password)) {
                                mDialog.dismiss();
                                Paper.book().write(Common.KEY_LOGIN, username);
                                Paper.book().write(Common.KEY_USER_ID, snapshot.getKey());
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Wrong in username or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void createUser(String username, String password) {

        mDialog.show();

        Users users = new Users("", username, password, "");
        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .push()
                .setValue(users)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Create user success", Toast.LENGTH_SHORT).show();
                            Paper.book().write(Common.KEY_LOGIN, username);
//                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        init();

        String currentUser = Paper.book().read(Common.KEY_LOGIN);
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void init() {

        Paper.init(this);

        mDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();
    }
}
