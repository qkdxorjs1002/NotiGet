package com.teamhub.notiget.ui.widget.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

public class ContactFragment extends Fragment {

    private ContactViewModel viewModel;
    private View root;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        root = inflater.inflate(R.layout.widget_contact, container, false);

        // TODO: init reference

        return root;
    }
}