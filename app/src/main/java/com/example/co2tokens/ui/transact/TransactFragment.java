package com.example.co2tokens.ui.transact;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.co2tokens.app.R;
import org.co2tokens.app.databinding.FragmentTransactBinding;

public class TransactFragment extends Fragment {

    private TransactViewModel transactViewModel;
    private FragmentTransactBinding binding;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        transactViewModel =
                new ViewModelProvider( this ).get( TransactViewModel.class );

        binding = FragmentTransactBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        final TextView textView = binding.viewMyAddress;
        transactViewModel.getText().observe( getViewLifecycleOwner(), textView::setText );



        binding.qrCodeShow.setOnClickListener( view -> {
            final View alertDialog= inflater.inflate( R.layout.alert_qr_code, null);
            ImageView imageView = alertDialog.findViewById(R.id.qr_code);
            imageView.setImageBitmap( createBitmap( binding.viewMyAddress.getText().toString() ) );

            AlertDialog.Builder alert = new AlertDialog.Builder( getContext() );
            alert.setView(alertDialog);
            alert.setCancelable( true );
            alert.show();
        } );

        return root;
    }


    private Bitmap createBitmap( String content ) {

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode( content,
                    BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch ( WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}