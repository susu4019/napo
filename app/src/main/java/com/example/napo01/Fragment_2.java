package com.example.napo01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.napo01.R;

public class Fragment_2 extends Fragment {
    private PageChangeListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag2, container, false);

        return rootView;
    }

    public void setListener(PageChangeListener listener) {
        this.listener = listener;
    }
    @Override
    public void onResume() {
        super.onResume();
        this.listener = (PageChangeListener) getArguments().getSerializable("listener");
        listener.onPagechange(2);
    }
}
