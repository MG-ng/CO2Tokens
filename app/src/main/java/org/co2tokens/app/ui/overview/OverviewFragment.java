package org.co2tokens.app.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.co2tokens.app.databinding.FragmentOverviewBinding;

public class OverviewFragment extends Fragment {

    private OverviewViewModel overviewViewModel;
    private FragmentOverviewBinding binding;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        overviewViewModel =
                new ViewModelProvider( this ).get( OverviewViewModel.class );

        binding = FragmentOverviewBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        final TextView balanceOverview = binding.balanceOverview;
        overviewViewModel.getBalance().observe( getViewLifecycleOwner(), balanceOverview::setText );
        final TextView txsView = binding.txs;
        overviewViewModel.getTxs().observe( getViewLifecycleOwner(), txsView::setText );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}