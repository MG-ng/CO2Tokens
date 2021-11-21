package com.example.co2tokens.ui.overview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.co2tokens.ApiLink;

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

        final TextView balanceOverviewBTC = binding.balanceOverviewBtc;
        overviewViewModel.getBalance().observe( getViewLifecycleOwner(), balanceOverviewBTC::setText );

        final TextView balanceOverviewCO2 = binding.balanceOverviewCO2t;
        overviewViewModel.getCO2Balance().observe( getViewLifecycleOwner(), balanceOverviewCO2::setText );

        binding.openInBrowser.setOnClickListener( view -> {
            Uri uri = Uri.parse( "https://testnet.xchain.io/address/" + ApiLink.getAddress() );
            Intent intent = new Intent( Intent.ACTION_VIEW, uri );
            startActivity( intent );
        } );

        // TODO: Put real txs in there
//        final TextView txsView = binding.txs;
//        overviewViewModel.getTxs().observe( getViewLifecycleOwner(), txsView::setText );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}