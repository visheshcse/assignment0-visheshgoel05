package com.example.helloworld;

import androidx.fragment.app.Fragment;

public class DetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DetailsFragment();
    }
}
