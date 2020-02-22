package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // @BindView(R.id.txt_course_name)
    public TextView mTxtCourseName;
   // @BindView(R.id.txt_course_teacher_name)
    public TextView mTxtCourseTeacherName;

    private OnRecyclerItemClick onRecyclerItemClick;

    public CourseHolder(@NonNull View itemView) {
        super(itemView);

     //   ButterKnife.bind(this, itemView);
        mTxtCourseName = itemView.findViewById(R.id.txt_course_name);
        mTxtCourseTeacherName = itemView.findViewById(R.id.txt_course_teacher_name);


        itemView.setOnClickListener(this);
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    @Override
    public void onClick(View v) {
        onRecyclerItemClick.onItemClickListener(v, getAdapterPosition());
    }
}
