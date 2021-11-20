package org.co2tokens.app.ui.burn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.co2tokens.app.databinding.FragmentBurnBinding;

public class BurnFragment extends Fragment {

    private BurnViewModel burnViewModel;
    private FragmentBurnBinding binding;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        burnViewModel =
                new ViewModelProvider( this ).get( BurnViewModel.class );

        binding = FragmentBurnBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        final TextView textView = binding.textBurnRecent;
        burnViewModel.getText().observe( getViewLifecycleOwner(), textView::setText );
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}