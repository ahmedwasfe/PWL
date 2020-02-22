package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   // @BindView(R.id.txt_marketer_name)
    public TextView mTxtMarketerName;

    private OnRecyclerItemClick onRecyclerItemClick;

    public MarketerHolder(@NonNull View itemView) {
        super(itemView);

       // ButterKnife.bind(this, itemView);

        mTxtMarketerName = itemView.findViewById(R.id.txt_marketer_name);

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
