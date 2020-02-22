package ahmet.com.pwl.Fragments;


import android.app.AlertDialog;
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
import ahmet.com.pwl.Holder.CourseHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.Courses;
import ahmet.com.pwl.R;
import ahmet.com.pwl.SubActivity.CoursesActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {

    @BindView(R.id.recycler_courses)
    RecyclerView mRecyclerCourses;
    @BindView(R.id.floating_add_course)
    FloatingActionButton mFabAddCourse;

    @OnClick(R.id.floating_add_course)
    void btnFloatingAction(){

        mRecyclerCourses.setVisibility(View.GONE);
        Common.setFragmrnt(AddCourseFragment.getInstance(), R.id.frame_layout_home,
                getActivity().getSupportFragmentManager());
        mFabAddCourse.setVisibility(View.GONE);
    }

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Courses, CourseHolder> mCourseAdapter;
    private FirebaseRecyclerOptions<Courses> mCourseOptions;

    private AlertDialog mDialog;

    private static CoursesFragment instance;
    public static CoursesFragment getInstance() {
        return instance == null ? new CoursesFragment() : instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate ( R.layout.fragment_courses, container, false );

        ButterKnife.bind (this, layoutView);


        init();

        Paper.init(getActivity());
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadCourses();

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
                            mFabAddCourse.setVisibility(View.VISIBLE);
                        else
                            mFabAddCourse.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadCourses() {

      //  mDialog.show();;

        Query courseQuery = mDatabaseReference.child(Common.KEY_TRAINING_COURSES_CHILD);
        mCourseOptions = new FirebaseRecyclerOptions.Builder<Courses>()
                .setQuery(courseQuery, Courses.class)
                .build();
        mCourseAdapter = new FirebaseRecyclerAdapter<Courses, CourseHolder>(mCourseOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CourseHolder holder, int position, @NonNull Courses courses) {

                holder.mTxtCourseName.setText(courses.getCourse());
                holder.mTxtCourseTeacherName.setText(courses.getTeacher());

                mDialog.dismiss();

                holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
                    @Override
                    public void onItemClickListener(View view, int position) {

                        Common.currentCourses = courses;
                        Common.selectedCourses = mCourseAdapter.getRef(position).getKey();
                        startActivity(new Intent(getActivity(), CoursesActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View layoutView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.raw_course, parent, false);

                return new CourseHolder(layoutView);
            }
        };

        mRecyclerCourses.setAdapter(mCourseAdapter);
    }

    private void init() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
       // mDatabaseReference.keepSynced(true);

        mRecyclerCourses.setHasFixedSize(true);
        mRecyclerCourses.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));

        mDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setCancelable(false)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mCourseAdapter != null)
            mCourseAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();


        if (mCourseAdapter != null)
            mCourseAdapter.startListening();
    }

    @Override
    public void onStop() {


        if (mCourseAdapter != null)
            mCourseAdapter.stopListening();

        super.onStop();
    }
}
