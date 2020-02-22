package ahmet.com.pwl.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ahmet.com.pwl.Adapter.TeachersDepartmentAdapter;
import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Holder.TeacherDepartmentHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.Teacher;
import ahmet.com.pwl.Model.TeachersDepartment;
import ahmet.com.pwl.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ahmet.com.pwl.SubActivity.TeacherDeptActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class TeachersDeptFragment extends Fragment {

    @BindView(R.id.reycler_teachers)
    RecyclerView mRecyclerTeachers;

    private AlertDialog mDialog;

    private DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<TeachersDepartment, TeacherDepartmentHolder> mTeacherDeptAdapter;
    FirebaseRecyclerOptions<TeachersDepartment> mTeacherDeptOptopns;

    private static TeachersDeptFragment instance;
    public static TeachersDeptFragment getInstance(){
        return instance == null ? new TeachersDeptFragment() : instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate( R.layout.fragment_dept_teachers, container, false);

        ButterKnife.bind (this, layoutView);

        init();
        loadTeachersDepartment();


        return layoutView;
    }


    private void init() {

        mDatabase = FirebaseDatabase.getInstance().getReference();
       // mDatabase.keepSynced(true);

        mDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setCancelable(false)
                .build();

        mRecyclerTeachers.setHasFixedSize(true);
        mRecyclerTeachers.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
    }

    private void loadTeachersDepartment() {

       // mDialog.show();

        Query teacherDeptQuery = mDatabase.child(Common.KEY_TEACHER_DEPARTMENT);
        mTeacherDeptOptopns = new FirebaseRecyclerOptions.Builder<TeachersDepartment>()
                .setQuery(teacherDeptQuery, TeachersDepartment.class)
                .build();

        mTeacherDeptAdapter = new FirebaseRecyclerAdapter<TeachersDepartment, TeacherDepartmentHolder>(mTeacherDeptOptopns) {
            @Override
            protected void onBindViewHolder(@NonNull TeacherDepartmentHolder holder, int position, @NonNull TeachersDepartment teachersDepartment) {
//
                if (Locale.getDefault().getLanguage().equals("en"))
                    holder.mTxtDepartmentName.setText(teachersDepartment.getNameEn());
                else
                    holder.mTxtDepartmentName.setText(teachersDepartment.getNameAr());

                Picasso.get().load(teachersDepartment.getIcon()).into(holder.mImgDepartmentIcon);

//                mDialog.dismiss();

                holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
                    @Override
                    public void onItemClickListener(View view, int position) {

                        Common.teachersDepartment = teachersDepartment;
                        Common.selectedTeacherDept = mTeacherDeptAdapter.getRef(position).getKey();
                        startActivity(new Intent(getActivity(), TeacherDeptActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public TeacherDepartmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View layoutView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.raw_teacher_dept, parent, false);
                return new TeacherDepartmentHolder(layoutView);
            }
        };

        mRecyclerTeachers.setAdapter(mTeacherDeptAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();

        if (mTeacherDeptAdapter != null)
            mTeacherDeptAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mTeacherDeptAdapter != null)
            mTeacherDeptAdapter.startListening();
    }

    @Override
    public void onStop() {

        if (mTeacherDeptAdapter != null)
            mTeacherDeptAdapter.stopListening();

        super.onStop();
    }
}
