package ahmet.com.pwl.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Holder.StudentHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.Student;
import ahmet.com.pwl.R;
import ahmet.com.pwl.SubActivity.StudentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {

    @BindView(R.id.recycler_student)
    RecyclerView mRecyclerStudent;
    @BindView(R.id.floating_add_student)
    FloatingActionButton btnFabAddStudent;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Student, StudentHolder> mStudentAdapter;
    private FirebaseRecyclerOptions<Student> mStudentOptions;

    @OnClick(R.id.floating_add_student)
    void clickedAddStudent(){

        mRecyclerStudent.setVisibility(View.GONE);
        Common.setFragmrnt(AddStudentFragment.getInstance(), R.id.frame_layout_home,
                getActivity().getSupportFragmentManager());
        btnFabAddStudent.setVisibility(View.GONE);
    }

    private static StudentFragment instance;
    public  static StudentFragment getInstance(){

        return instance == null ? new StudentFragment(): instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView= inflater.inflate ( R.layout.fragment_student, container, false );

        ButterKnife.bind(this, layoutView);

        init();

        Paper.init(getActivity());
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadStudents();

        return layoutView;
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
                            btnFabAddStudent.setVisibility(View.VISIBLE);
                        else
                            btnFabAddStudent.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void init() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
       // mDatabaseReference.keepSynced(true);

        mRecyclerStudent.setHasFixedSize(true);
        mRecyclerStudent.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
    }

    private void loadStudents() {

        Query studentQuery = mDatabaseReference.child(Common.KEY_STUDENTS_CHILD);
        mStudentOptions = new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(studentQuery, Student.class)
                .build();

        mStudentAdapter = new FirebaseRecyclerAdapter<Student, StudentHolder>(mStudentOptions) {
            @Override
            protected void onBindViewHolder(@NonNull StudentHolder holder, int position, @NonNull Student student) {

                holder.mTxtStudentName.setText(student.getName());
                holder.mTxtStudentCity.setText(student.getCity());
                holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
                    @Override
                    public void onItemClickListener(View view, int position) {
                        Common.currentStudent = student;
                        Common.selectedStudent = mStudentAdapter.getRef(position).getKey();
                        startActivity(new Intent(getActivity(), StudentActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View layoutView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.raw_student, parent, false);

                return new StudentHolder(layoutView);
            }
        };

        mRecyclerStudent.setAdapter(mStudentAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mStudentAdapter != null)
            mStudentAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mStudentAdapter != null)
            mStudentAdapter.startListening();
    }

    @Override
    public void onStop() {

        if (mStudentAdapter != null)
            mStudentAdapter.stopListening();

        super.onStop();
    }
}
