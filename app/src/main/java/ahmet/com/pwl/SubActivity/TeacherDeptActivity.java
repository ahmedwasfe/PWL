package ahmet.com.pwl.SubActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Fragments.AddTeacherFragment;
import ahmet.com.pwl.Holder.TeacherHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.Teacher;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class TeacherDeptActivity extends AppCompatActivity {

    @BindView(R.id.recycler_teacher_dept)
    RecyclerView mRecyclerTeacherDept;
    @BindView(R.id.frame_layout_teacher)
    FrameLayout frameLayout;
    @BindView(R.id.floating_add)
    FloatingActionButton btnFloatingAction;

    private FirebaseRecyclerAdapter<Teacher, TeacherHolder> mTeacherAdapter;
    private FirebaseRecyclerOptions<Teacher> mTeacherOption;
    private DatabaseReference mDatabase;

    private AlertDialog mDialog;

    @OnClick(R.id.floating_add)
    void floatingActionButton() {

        mRecyclerTeacherDept.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        Common.setFragmrnt(AddTeacherFragment.getInstance(), R.id.frame_layout_teacher, getSupportFragmentManager());
        btnFloatingAction.setVisibility(View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dept);

        ButterKnife.bind(this);

        if (Locale.getDefault().getLanguage().equals("en"))
            getSupportActionBar().setTitle(Common.teachersDepartment.getNameEn());
        else
            getSupportActionBar().setTitle(Common.teachersDepartment.getNameAr());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frameLayout.setVisibility(View.GONE);
        mRecyclerTeacherDept.setVisibility(View.VISIBLE);

        init();

        Paper.init(this);
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadTeacher();

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
                            btnFloatingAction.setVisibility(View.VISIBLE);
                        else
                            btnFloatingAction.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadTeacher() {

        if (Common.selectedTeacherDept.equals("1"))
            loadTeacher(Common.KEY_TEACHER_COMPUTER_CHILD);
        else if (Common.selectedTeacherDept.equals("2"))
            loadTeacher(Common.KEY_TEACHER_LANGUAGES_CHILD);
        else if (Common.selectedTeacherDept.equals("3"))
            loadTeacher(Common.KEY_TEACHER_RELAYCOURSES_CHILD);
        else if (Common.selectedTeacherDept.equals("4"))
            loadTeacher(Common.KEY_TEACHER_HUMANDEVELOPMENT_CHILD);
        else if (Common.selectedTeacherDept.equals("5"))
            loadTeacher(Common.KEY_TEACHER_OTHERS_CHILD);
    }

    private void loadTeacher(String keyTeacher) {


        Query teacherQuery = mDatabase.child(Common.KEY_TEACHERS).child(keyTeacher);

        mTeacherOption = new FirebaseRecyclerOptions.Builder<Teacher>()
                .setQuery(teacherQuery, Teacher.class)
                .build();

        mTeacherAdapter = new FirebaseRecyclerAdapter<Teacher, TeacherHolder>(mTeacherOption) {
            @Override
            protected void onBindViewHolder(@NonNull TeacherHolder holder, int position, @NonNull Teacher teacher) {

                    holder.mTxtTeacherName.setText(teacher.getName());
                    holder.mTxtTeacherCity.setText(teacher.getCity());
                    mDialog.dismiss();

                    holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
                        @Override
                        public void onItemClickListener(View view, int position) {

                            Common.currentTeacher = teacher;
                            Common.selectedTeacher = mTeacherAdapter.getRef(position).getKey();
                           // Common.teachersDepartment.getNameEn();
                            startActivity(new Intent(TeacherDeptActivity.this, TeacherDetailsActivity.class));
                        }
                    });
            }

            @NonNull
            @Override
            public TeacherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View layoutView = LayoutInflater.from(TeacherDeptActivity.this)
                        .inflate(R.layout.raw_teacher, parent, false);

                return new TeacherHolder(layoutView);
            }

        };

        mRecyclerTeacherDept.setAdapter(mTeacherAdapter);
    }

    private void init() {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        mDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();

        mRecyclerTeacherDept.setHasFixedSize(true);
        mRecyclerTeacherDept.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mTeacherAdapter != null)
            mTeacherAdapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mTeacherAdapter != null)
            mTeacherAdapter.startListening();
    }

    @Override
    protected void onStop() {

        if (mTeacherAdapter != null)
            mTeacherAdapter.stopListening();

        super.onStop();
    }
}
