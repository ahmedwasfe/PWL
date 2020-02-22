package ahmet.com.pwl.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import ahmet.com.pwl.Holder.MarketerHolder;
import ahmet.com.pwl.Interface.OnRecyclerItemClick;
import ahmet.com.pwl.Model.Marketer;
import ahmet.com.pwl.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class MarketersFragment extends Fragment {

    @BindView(R.id.recycler_marketers)
    RecyclerView mRecyclerMarketers;
    @BindView(R.id.floating_add_marketer)
    FloatingActionButton btnFabAddMarketer;


    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Marketer, MarketerHolder> mMarketersAdapter;
    private FirebaseRecyclerOptions<Marketer> mMarketerOptions;

    @OnClick(R.id.floating_add_marketer)
    void clickedAddMarketer(){

        mRecyclerMarketers.setVisibility(View.GONE);
        Common.setFragmrnt(AddMarketersFragment.getInstance(), R.id.frame_layout_home,
                getActivity().getSupportFragmentManager());
        btnFabAddMarketer.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static MarketersFragment instance;
    public static MarketersFragment getInstance(){
        return instance == null ? new MarketersFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_marketers, container, false);

        ButterKnife.bind(this, layoutView);

        init();

        Paper.init(getActivity());
        String userId = Paper.book().read(Common.KEY_USER_ID);
        getUserType(userId);

        loadMarketers();

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
                            btnFabAddMarketer.setVisibility(View.VISIBLE);
                        else
                            btnFabAddMarketer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadMarketers() {

        Query marketerQuery = mDatabaseReference.child(Common.KEY_MARKETERS_CHILD);
        mMarketerOptions = new FirebaseRecyclerOptions.Builder<Marketer>()

                .setQuery(marketerQuery, Marketer.class)
                .build();

        mMarketersAdapter = new FirebaseRecyclerAdapter<Marketer, MarketerHolder>(mMarketerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull MarketerHolder holder, int position, @NonNull Marketer marketer) {

                holder.mTxtMarketerName.setText(marketer.getName());

                holder.setOnRecyclerItemClick(new OnRecyclerItemClick() {
                    @Override
                    public void onItemClickListener(View view, int position) {

                        Toast.makeText(getContext(), marketer.getName() + "\n" + marketer.getMobile(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public MarketerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View layoutView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.raw_marketer, parent, false);

                return new MarketerHolder(layoutView);
            }
        };

        mRecyclerMarketers.setAdapter(mMarketersAdapter);
    }

    private void init() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
       // mDatabaseReference.keepSynced(true);

        mRecyclerMarketers.setHasFixedSize(true);
        mRecyclerMarketers.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mMarketersAdapter != null)
            mMarketersAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mMarketersAdapter != null)
            mMarketersAdapter.startListening();
    }

    @Override
    public void onStop() {

        if (mMarketersAdapter != null)
            mMarketersAdapter.stopListening();

        super.onStop();
    }
}
