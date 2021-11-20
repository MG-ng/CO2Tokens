package org.co2tokens.app.ui.burn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

        final TextView textView = binding.textNotifications;
        burnViewModel.getText().observe( getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged( @Nullable String s ) {
                textView.setText( s );
            }
        } );
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}