package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherDepartmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // @BindView(R.id.txt_department_main_name)
    public TextView mTxtDepartmentName;
   // @BindView(R.id.img_department_main_icon)
    public ImageView mImgDepartmentIcon;

    OnRecyclerItemClick onRecyclerItemClick;

    public TeacherDepartmentHolder(@NonNull View itemView) {
        super(itemView);

       // ButterKnife.bind(this, itemView);

        mTxtDepartmentName = itemView.findViewById(R.id.txt_department_main_name);
        mImgDepartmentIcon = itemView.findViewById(R.id.img_department_main_icon);

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
