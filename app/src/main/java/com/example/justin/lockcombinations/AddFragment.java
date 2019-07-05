// JUSTIN WEI, 91618787

package com.example.justin.lockcombinations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddFragment extends DialogFragment{

    public interface OnButtonListener{

        public void addCombo(int no1, int no2, int no3);

    }

    public OnButtonListener onButtonListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof OnButtonListener)
            onButtonListener = (OnButtonListener) context;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.ThemeOverlay_AppCompat_Dialog_Alert);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add, null);

        final EditText no1 = (EditText) dialogView.findViewById(R.id.edittext_no1);
        final EditText no2 = (EditText) dialogView.findViewById(R.id.edittext_no2);
        final EditText no3 = (EditText) dialogView.findViewById(R.id.edittext_no3);
        final LinearLayout submitButton = (LinearLayout) dialogView.findViewById(R.id.button_search);
        final TextView buttonText = (TextView) dialogView.findViewById(R.id.text_addbutton);

        no1.setTransformationMethod(null);
        no2.setTransformationMethod(null);
        no3.setTransformationMethod(null);

        no1.requestFocus();

        no1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(no1.getText().toString().length() == 2){
                    no2.requestFocus();
                }
                if(!no1.getText().toString().trim().isEmpty()&&!no2.getText().toString().trim().isEmpty()&&!no3.getText().toString().trim().isEmpty()){
                    buttonText.setTextColor(getResources().getColor(R.color.lightGray));
                    submitButton.setBackground(getActivity().getDrawable(R.drawable.button));
                    submitButton.setClickable(true);
                }
                else{
                    buttonText.setTextColor(getResources().getColor(R.color.darkerGray));
                    submitButton.setBackground(getActivity().getDrawable(R.drawable.button_gray));
                    submitButton.setClickable(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        no2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(no2.getText().toString().length() == 2){
                    no3.requestFocus();
                }
                if(!no1.getText().toString().trim().isEmpty()&&!no2.getText().toString().trim().isEmpty()&&!no3.getText().toString().trim().isEmpty()){
                    buttonText.setTextColor(getResources().getColor(R.color.lightGray));
                    submitButton.setBackground(getActivity().getDrawable(R.drawable.button));
                    submitButton.setClickable(true);
                }
                else{
                    buttonText.setTextColor(getResources().getColor(R.color.darkerGray));
                    submitButton.setBackground(getActivity().getDrawable(R.drawable.button_gray));
                    submitButton.setClickable(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        no3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!no1.getText().toString().trim().isEmpty()&&!no2.getText().toString().trim().isEmpty()&&!no3.getText().toString().trim().isEmpty()){
                    buttonText.setTextColor(getResources().getColor(R.color.lightGray));
                    submitButton.setBackground(getActivity().getDrawable(R.drawable.button));
                    submitButton.setClickable(true);
                }
                else{
                    buttonText.setTextColor(getResources().getColor(R.color.darkerGray));
                    submitButton.setBackground(getActivity().getDrawable(R.drawable.button_gray));
                    submitButton.setClickable(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonListener.addCombo(Integer.parseInt(no1.getText().toString()),Integer.parseInt(no2.getText().toString()),Integer.parseInt(no3.getText().toString()));

                dismiss();
            }
        });

        builder.setView(dialogView);
        Dialog ret = builder.create();
        ret.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return ret;

    }

}
