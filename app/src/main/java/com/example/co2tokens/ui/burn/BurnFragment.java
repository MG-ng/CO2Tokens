package com.example.co2tokens.ui.burn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        final TextView availableView = binding.burnAvailable;
        burnViewModel.getAvailable().observe( getViewLifecycleOwner(), availableView::setText );
        burnViewModel.getBurnStatus().observe( getViewLifecycleOwner(), status -> {
            AlertDialog.Builder alert = new AlertDialog.Builder( getContext() );
            alert.setTitle( "Burning Successful!" );
            alert.setMessage( status );
            alert.setCancelable( true );
            alert.show();
        });

        binding.burnConfirm.setOnClickListener( view -> {
            double amount = Integer.parseInt( String.valueOf( binding.burnAmount.getText() ) );
            burnViewModel.burnAmt( amount );
        } );


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        binding.burnConfirm.setOnClickListener( view -> {
            new AlertDialog.Builder( getActivity() )
                    .setTitle( "Burn address" )
                    .setMessage( "Your specified amount was sent to:" +
                            "\n\n ")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton( android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface dialog, int which ) {
                            // Continue with delete operation
                        }
                    } )

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton( android.R.string.no, null )
                    .setIcon( android.R.drawable.ic_dialog_alert )
                    .show();
        } );
        */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}