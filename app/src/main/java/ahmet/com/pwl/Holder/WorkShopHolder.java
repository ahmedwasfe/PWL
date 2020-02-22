package ahmet.com.pwl.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkShopHolder extends RecyclerView.ViewHolder {

  //  @BindView(R.id.txt_workshop_title)
    public TextView mTxtWorkshopTitle;
   // @BindView(R.id.txt_workshop_date)
    public TextView mTxtWorkshopDate;

    public WorkShopHolder(@NonNull View itemView) {
        super(itemView);

     //   ButterKnife.bind(this, itemView);;
        mTxtWorkshopTitle = itemView.findViewById(R.id.txt_workshop_title);
        mTxtWorkshopDate = itemView.findViewById(R.id.txt_workshop_date);
    }
}
