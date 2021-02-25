package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class McStudentsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new McStudentsListFragment();
    }

}