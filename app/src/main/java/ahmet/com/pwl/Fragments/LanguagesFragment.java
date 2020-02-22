package ahmet.com.pwl.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ahmet.com.pwl.R;
import butterknife.ButterKnife;

public class LanguagesFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static LanguagesFragment instance;
    public static LanguagesFragment getInstance(){
        return instance == null ? new LanguagesFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_languages, container, false);

        ButterKnife.bind(this, layoutView);

        return layoutView;
    }
}
