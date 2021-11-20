package org.co2tokens.app.ui.transfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.co2tokens.app.databinding.FragmentTransferBinding;

public class TransferFragment extends Fragment {

    private TransferViewModel transferViewModel;
    private FragmentTransferBinding binding;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        transferViewModel =
                new ViewModelProvider( this ).get( TransferViewModel.class );

        binding = FragmentTransferBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        final TextView textView = binding.viewMyAddress;
        transferViewModel.getText().observe( getViewLifecycleOwner(), textView::setText );
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}