package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // @BindView(R.id.txt_activites_name)
    public TextView mTxtActivtyTitle;
   // @BindView(R.id.txt_activites_date)
    public TextView mTxtActivtyDate;

    private OnRecyclerItemClick onRecyclerItemClick;

    public ActivitesHolder(@NonNull View itemView) {
        super(itemView);

      //  ButterKnife.bind(this, itemView);

        mTxtActivtyTitle = itemView.findViewById(R.id.txt_activites_name);
        mTxtActivtyDate = itemView.findViewById(R.id.txt_activites_date);

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
