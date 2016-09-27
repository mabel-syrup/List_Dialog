package com.surber.matthew.listdialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by wo1624bu on 9/27/16.
 */
public class ColorPickerDialogFragment extends DialogFragment{

    final CharSequence[] COLOR_CHOICES = {"RED", "GREEN", "BLUE", "PURPLE"};

    final int[] COLOR_VALUES = {Color.RED, Color.rgb(25,200,25), Color.rgb(25,75,200), Color.rgb(200,100,255)};

    interface ColorDialogSelectionListener {
        void colorSelected(int color);
    }

    private ColorDialogSelectionListener mListener;

    public static ColorPickerDialogFragment newInstance() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View colorPicker = inflater.inflate(R.layout.color_picker, null);
        final TextView redValue = (TextView) colorPicker.findViewById(R.id.value_red);
        final TextView greenValue = (TextView) colorPicker.findViewById(R.id.value_green);
        final TextView blueValue = (TextView) colorPicker.findViewById(R.id.value_blue);
        final ImageView colorPreview = (ImageView) colorPicker.findViewById(R.id.color_preview);
        final SeekBar redSB = (SeekBar) colorPicker.findViewById(R.id.sk_red);
        final SeekBar greenSB = (SeekBar) colorPicker.findViewById(R.id.sk_green);
        final SeekBar blueSB = (SeekBar) colorPicker.findViewById(R.id.sk_blue);

        final int[] currentColor = {0,0,0};

        redSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                System.out.println("Red changed!");
                redValue.setText(progress);
                currentColor[0] = progress;
                colorPreview.setBackgroundColor(Color.rgb(currentColor[0],currentColor[1],currentColor[2]));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setTitle("Choose a background color")
                .setView(R.layout.color_picker)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.colorSelected(Color.rgb(redSB.getProgress(),greenSB.getProgress(),blueSB.getProgress()));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
                /*.setItems(COLOR_CHOICES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        mListener.colorSelected(COLOR_VALUES[which]);
                    }
                });*/
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ColorDialogSelectionListener) {
            mListener = (ColorDialogSelectionListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement ColorDialogSelectionListener");
        }
    }
}
