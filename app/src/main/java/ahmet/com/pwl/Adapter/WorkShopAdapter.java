package ahmet.com.pwl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ahmet.com.pwl.Holder.WorkShopHolder;
import ahmet.com.pwl.Model.WorkShop;
import ahmet.com.pwl.R;

public class WorkShopAdapter extends RecyclerView.Adapter<WorkShopHolder> {

    private Context mContext;
    private List<WorkShop> mListWorkshop;
    private LayoutInflater inflater;

    public WorkShopAdapter(Context mContext, List<WorkShop> mListWorkshop) {
        this.mContext = mContext;
        this.mListWorkshop = mListWorkshop;

        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public WorkShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = inflater.inflate(R.layout.raw_work_shop, parent, false);

        return new WorkShopHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkShopHolder holder, int position) {

        holder.mTxtWorkshopTitle.setText(mListWorkshop.get(position).getTitle());
        holder.mTxtWorkshopDate.setText(mListWorkshop.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mListWorkshop.size();
    }
}
