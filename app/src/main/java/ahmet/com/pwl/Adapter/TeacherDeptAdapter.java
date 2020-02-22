package ahmet.com.pwl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Holder.TeacherDepartmentHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.TeachersDepartment;
import ahmet.com.pwl.R;
import ahmet.com.pwl.SubActivity.TeacherDeptActivity;

public class TeacherDeptAdapter extends RecyclerView.Adapter<TeacherDepartmentHolder> {

    private Context mContext;
    private List<TeachersDepartment> mListTeacherDept;

    private LayoutInflater inflater;

    public TeacherDeptAdapter(Context mContext, List<TeachersDepartment> mListTeacherDept) {
        this.mContext = mContext;
        this.mListTeacherDept = mListTeacherDept;

        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public TeacherDepartmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = inflater.inflate(R.layout.raw_teacher_dept, parent, false);

        return new TeacherDepartmentHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherDepartmentHolder holder, int position) {

          if (Locale.getDefault().getLanguage().equals("en"))
            holder.mTxtDepartmentName.setText(mListTeacherDept.get(position).getNameEn());
          else
            holder.mTxtDepartmentName.setText(mListTeacherDept.get(position).getNameAr());

        Picasso.get().load(mListTeacherDept.get(position).getIcon()).into(holder.mImgDepartmentIcon);

        holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
            @Override
            public void onItemClickListener(View view, int position) {
                Common.teachersDepartment = mListTeacherDept.get(position);
               // Common.selectedTeacherDept = mTeacherDeptAdapter.getRef(position).getKey();
                mContext.startActivity(new Intent(mContext, TeacherDeptActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListTeacherDept.size();
    }
}
