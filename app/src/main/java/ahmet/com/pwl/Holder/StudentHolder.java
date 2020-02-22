package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // @BindView(R.id.txt_student_name)
    public TextView mTxtStudentName;
   // @BindView(R.id.txt_student_city)
    public TextView mTxtStudentCity;

    private OnRecyclerItemClick onRecyclerItemClick;

    public StudentHolder(@NonNull View itemView) {
        super(itemView);

     //   ButterKnife.bind(this, itemView);
        mTxtStudentName = itemView.findViewById(R.id.txt_student_name);
        mTxtStudentCity = itemView.findViewById(R.id.txt_student_city);

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
