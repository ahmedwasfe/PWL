package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RevenueHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // @BindView(R.id.txt_revenue_name)
    public TextView mTxtRevenueName;
   // @BindView(R.id.txt_revenue_date_)
    public TextView mTxtRevenueDate;

    private OnRecyclerItemClick onRecyclerItemClick;

    public RevenueHolder(@NonNull View itemView) {
        super(itemView);

       // ButterKnife.bind(this, itemView);
        mTxtRevenueName = itemView.findViewById(R.id.txt_revenue_name);
        mTxtRevenueDate = itemView.findViewById(R.id.txt_revenue_date_);


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
