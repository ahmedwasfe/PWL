package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // @BindView(R.id.txt_teacher_name)
    public TextView mTxtTeacherName;
   // @BindView(R.id.txt_teacher_city)
    public TextView mTxtTeacherCity;

    private OnRecyclerItemClick onRecyclerItemClick;

    public TeacherHolder(@NonNull View itemView) {
        super(itemView);

     //   ButterKnife.bind(this, itemView);
        mTxtTeacherName = itemView.findViewById(R.id.txt_teacher_name);
        mTxtTeacherCity = itemView.findViewById(R.id.txt_teacher_city);

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
