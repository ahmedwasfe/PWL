package ahmet.com.pwl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ahmet.com.pwl.Model.TeachersDepartment;
import ahmet.com.pwl.R;

public class TeachersDepartmentAdapter extends ArrayAdapter {

    private List<TeachersDepartment> mListTeachersDepartments;
    private Context mContext;

    public TeachersDepartmentAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects, List<TeachersDepartment> mListTeachersDepartments) {
        super(context, resource, textViewResourceId, objects);
        this.mContext = context;
        this.mListTeachersDepartments = mListTeachersDepartments;
    }


    @Nullable
    @Override
    public Object getItem(int position) {

        return mListTeachersDepartments.get(position);
    }

    @Override
    public int getCount() {

        return mListTeachersDepartments.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View layoutView = LayoutInflater.from(mContext)
                .inflate(R.layout.raw_teacher_dept,null, false);

        TextView mTxtDeptName = layoutView.findViewById(R.id.txt_department_main_name);
        mTxtDeptName.setText(mListTeachersDepartments.get(position).getNameEn());

        return layoutView;
    }
}
