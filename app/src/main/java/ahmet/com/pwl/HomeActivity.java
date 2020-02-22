package ahmet.com.pwl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import ahmet.com.pwl.Common.Common;
import ahmet.com.pwl.Fragments.ActivitesFragment;
import ahmet.com.pwl.Fragments.CoursesFragment;
import ahmet.com.pwl.Fragments.MarketersFragment;
import ahmet.com.pwl.Fragments.RevenuesFragment;
import ahmet.com.pwl.Fragments.StudentFragment;
import ahmet.com.pwl.Fragments.TeachersDeptFragment;
import ahmet.com.pwl.Fragments.SettingsFragment;
import ahmet.com.pwl.Fragments.WorkShopFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.tool_bar_home)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mActionDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Common.setFragmrnt(TeachersDeptFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());

        Paper.init(this);

        initNavigationView();

    }

    private void initNavigationView() {

        mActionDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionDrawerToggle);
        mActionDrawerToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_teachers:
                        Common.setFragmrnt(TeachersDeptFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.teachers);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_courses:
                        Common.setFragmrnt(CoursesFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.courses);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_workshops:
                        Common.setFragmrnt(WorkShopFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.workshops);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_students:
                        Common.setFragmrnt(StudentFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.students);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_activities:
                        Common.setFragmrnt(ActivitesFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.activities);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_revenues:
                        Common.setFragmrnt(RevenuesFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.revenues);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_marketers:
                        Common.setFragmrnt(MarketersFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.marketers);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settings:
                        Common.setFragmrnt(SettingsFragment.getInstance(), R.id.frame_layout_home, getSupportFragmentManager());
                        getSupportActionBar().setTitle(R.string.settings);
                        mDrawerLayout.closeDrawers();
                        break;
                }

                return true;
            }
        });

        View headerView = mNavigationView.getHeaderView(0);
        TextView mTxtUsername = headerView.findViewById(R.id.txt_user_name);
        String userId = Paper.book().read(Common.KEY_USER_ID);
        if (userId != null)
            loadUserInfo(userId, mTxtUsername);


    }

    private void loadUserInfo(String userId, TextView mTxtUsername) {

        FirebaseDatabase.getInstance().getReference()
                .child(Common.KEY_USERS_CHILD)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mTxtUsername.setText(dataSnapshot.child("name").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mActionDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
