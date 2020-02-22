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
import ahmet.com.pwl.Holder.ActivitesHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.Activites;
import ahmet.com.pwl.R;
import ahmet.com.pwl.SubActivity.ActivitesActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class ActivitesFragment extends Fragment {


    @BindView(R.id.recycler_activites)
    RecyclerView mRecyclerActives;
    @BindView(R.id.floating_add_activites)
    FloatingActionButton btnFabAddActivity;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Activites, ActivitesHolder> mActivtesAdapter;
    private FirebaseRecyclerOptions<Activites> mActivitesOptions;

    @OnClick(R.id.floating_add_activites)
    void clickedAddActivity(){

        mRecyclerActives.setVisibility(View.GONE);
        Common.setFragmrnt(AddActivitesFragment.getInstance(), R.id.frame_layout_home,
                getActivity().getSupportFragmentManager());
        btnFabAddActivity.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static ActivitesFragment instance;
    public static ActivitesFragment getInstance(){
        return instance == null ? new ActivitesFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_activites, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        Paper.init(getActivity());
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadActivites();

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
                            btnFabAddActivity.setVisibility(View.VISIBLE);
                        else
                            btnFabAddActivity.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadActivites() {

        Query activitesQuery = mDatabaseReference.child(Common.KEY_ACTIVITES_CHILD);
        mActivitesOptions = new FirebaseRecyclerOptions.Builder<Activites>()
                .setQuery(activitesQuery, Activites.class)
                .build();

        mActivtesAdapter = new FirebaseRecyclerAdapter<Activites, ActivitesHolder>(mActivitesOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ActivitesHolder holder, int position, @NonNull Activites activites) {

                holder.mTxtActivtyTitle.setText(activites.getTitle());
                holder.mTxtActivtyDate.setText(activites.getDate());

                holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
                    @Override
                    public void onItemClickListener(View view, int position) {

                        Common.currentActivites = activites;
                        Common.selectedActivites = mActivtesAdapter.getRef(position).getKey();
                        startActivity(new Intent(getActivity(), ActivitesActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public ActivitesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View layoutView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.raw_activites, parent, false);

                return new ActivitesHolder(layoutView);
            }
        };

        mRecyclerActives.setAdapter(mActivtesAdapter);
    }

    private void init() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
       // mDatabaseReference.keepSynced(true);

        mRecyclerActives.setHasFixedSize(true);
        mRecyclerActives.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mActivtesAdapter != null)
            mActivtesAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mActivtesAdapter != null)
            mActivtesAdapter.startListening();
    }

    @Override
    public void onStop() {

        if (mActivtesAdapter != null)
            mActivtesAdapter.stopListening();

        super.onStop();
    }
}
