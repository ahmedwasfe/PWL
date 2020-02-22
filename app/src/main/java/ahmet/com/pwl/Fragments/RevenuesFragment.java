package ahmet.com.pwl.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Holder.RevenueHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.Revenue;
import ahmet.com.pwl.R;
import ahmet.com.pwl.SubActivity.RevenueActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class RevenuesFragment extends Fragment {

    @BindView(R.id.recycler_revenue)
    RecyclerView mRecyclerRevenue;
    @BindView(R.id.floating_add_revenue)
    FloatingActionButton btnAddRevenue;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Revenue, RevenueHolder> mRevenueAdapter;
    private FirebaseRecyclerOptions<Revenue> mRevenueOptions;

    @OnClick(R.id.floating_add_revenue)
    void cilckedaddRevenue(){

        mRecyclerRevenue.setVisibility(View.GONE);
        Common.setFragmrnt(AddRevenueFragment.getInstance(), R.id.frame_layout_home,
                getActivity().getSupportFragmentManager());
        btnAddRevenue.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static RevenuesFragment instance;
    public static RevenuesFragment getInstance(){
        return instance == null ? new RevenuesFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_revenues, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        Paper.init(getActivity());
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadRevenue();

        return layoutView;

    }

    private void getUserType(String userId) {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String userType = dataSnapshot.child("userType").getValue().toString();
                        if (userType.equals(getString(R.string.admin)))
                            btnAddRevenue.setVisibility(View.VISIBLE);
                        else
                            btnAddRevenue.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void init() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
      //  mDatabaseReference.keepSynced(true);

        mRecyclerRevenue.setHasFixedSize(true);
        mRecyclerRevenue.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
    }

    private void loadRevenue() {

        Query query = mDatabaseReference.child(Common.KEY_REVENUE_CHILD);
        mRevenueOptions = new FirebaseRecyclerOptions.Builder<Revenue>()
                .setQuery(query, Revenue.class)
                .build();

        mRevenueAdapter = new FirebaseRecyclerAdapter<Revenue, RevenueHolder>(mRevenueOptions) {
            @Override
            protected void onBindViewHolder(@NonNull RevenueHolder holder, int position, @NonNull Revenue revenue) {

                holder.mTxtRevenueName.setText(revenue.getRevenueName());
                holder.mTxtRevenueDate.setText(revenue.getRevenueDate());

                holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
                    @Override
                    public void onItemClickListener(View view, int position) {
                        Common.currentRevenue = revenue;
                        Common.selectedRevenue = mRevenueAdapter.getRef(position).getKey();
                        startActivity(new Intent(getActivity(), RevenueActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public RevenueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View latoutView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.raw_revenue, parent, false);

                return new RevenueHolder(latoutView);
            }
        };

        mRecyclerRevenue.setAdapter(mRevenueAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mRevenueAdapter != null)
            mRevenueAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRevenueAdapter != null)
            mRevenueAdapter.startListening();
    }

    @Override
    public void onStop() {

        if (mRevenueAdapter != null)
            mRevenueAdapter.stopListening();

        super.onStop();
    }
}
