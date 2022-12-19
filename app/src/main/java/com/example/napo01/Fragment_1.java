package com.example.napo01;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.napo01.R;

public class Fragment_1 extends Fragment {
    private PageChangeListener listener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag1, container, false);

        return rootView;
    }

    public void setListener(PageChangeListener listener) {
        this.listener = listener;
    }
    @Override
    public void onResume() {
        super.onResume();
        this.listener = (PageChangeListener) getArguments().getSerializable("listener");
        listener.onPagechange(1);
    }
}
