package ahmet.com.pwl.Fragments;

import android.media.MediaCodecList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import ahmet.com.pwl.Adapter.WorkShopAdapter;
import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Holder.WorkShopHolder;
import ahmet.com.pwl.Interface.LoadWorkshopListener;
import ahmet.com.pwl.Model.WorkShop;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

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

import java.util.ArrayList;
import java.util.List;

public class WorkShopFragment extends Fragment implements LoadWorkshopListener {

    @BindView(R.id.recycler_work_shop)
    RecyclerView mRecyclerWorkShop;
    @BindView(R.id.floating_add_work_shop)
    FloatingActionButton btnFloatingAction;

    @OnClick(R.id.floating_add_work_shop)
    void clicedFloating(){

        mRecyclerWorkShop.setVisibility(View.GONE);
        Common.setFragmrnt(AddWorkShopFragment.getInstance(), R.id.frame_layout_home,
                getActivity().getSupportFragmentManager());
        btnFloatingAction.setVisibility(View.GONE);
    }

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<WorkShop, WorkShopHolder> mWorkShopAdapter;
    private FirebaseRecyclerOptions<WorkShop> mWorkShopOptions;

    private LoadWorkshopListener loadWorkshopListener;

    private static WorkShopFragment instance;
    public  static WorkShopFragment getInstance(){
        return instance == null ? new WorkShopFragment () : instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate ( R.layout.fragment_work_shop,container,false );

        ButterKnife.bind(this, layoutView);

        init();

        Paper.init(getActivity());
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadWorkshop();

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
                            btnFloatingAction.setVisibility(View.VISIBLE);
                        else
                            btnFloatingAction.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadWorkshop() {

        mDatabaseReference.child(Common.KEY_WORKSHOP_CHILD)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<WorkShop> mListWorkshop = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            WorkShop workShop = snapshot.getValue(WorkShop.class);
                            mListWorkshop.add(workShop);
                        }

                        loadWorkshopListener.onLoadWorkShopSuccess(mListWorkshop);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        loadWorkshopListener.onLoadWorkShopFailed(databaseError.getMessage());
                    }
                });
    }

    private void loadWorkShop() {

        Query workShopQuery = mDatabaseReference.child(Common.KEY_WORKSHOP_CHILD);
        mWorkShopOptions = new FirebaseRecyclerOptions.Builder<WorkShop>()
                .setQuery(workShopQuery, WorkShop.class)
                .build();

        mWorkShopAdapter = new FirebaseRecyclerAdapter<WorkShop, WorkShopHolder>(mWorkShopOptions) {
            @Override
            protected void onBindViewHolder(@NonNull WorkShopHolder holder, int position, @NonNull WorkShop workShop) {

                holder.mTxtWorkshopTitle.setText(workShop.getTitle());
                holder.mTxtWorkshopDate.setText(workShop.getDate());
            }

            @NonNull
            @Override
            public WorkShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View layoutView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.raw_work_shop, parent, false);

                return new WorkShopHolder(layoutView);
            }
        };

        mRecyclerWorkShop.setAdapter(mWorkShopAdapter);
    }

    private void init() {

        loadWorkshopListener = this;

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
       // mDatabaseReference.keepSynced(true);

        mRecyclerWorkShop.setHasFixedSize(true);
        mRecyclerWorkShop.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mWorkShopAdapter != null)
            mWorkShopAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mWorkShopAdapter != null)
            mWorkShopAdapter.startListening();
    }

    @Override
    public void onStop() {

        if (mWorkShopAdapter != null)
            mWorkShopAdapter.stopListening();

        super.onStop();
    }

    @Override
    public void onLoadWorkShopSuccess(List<WorkShop> mLisWorksop) {

        WorkShopAdapter workShopAdapter = new WorkShopAdapter(getActivity(), mLisWorksop);
        mRecyclerWorkShop.setAdapter(workShopAdapter);

    }

    @Override
    public void onLoadWorkShopFailed(String error) {

        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

    }
}
