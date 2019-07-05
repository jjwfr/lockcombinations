// JUSTIN WEI, 91618787

package com.example.justin.lockcombinations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotFragment extends DialogFragment {

    public interface OnButtonListener{

        public void filterValues(int no1, int no2, int no3);

    }

    private OnButtonListener onButtonListener;

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
        View dialogView = inflater.inflate(R.layout.dialog_forgot, null);

        final EditText no1 = (EditText) dialogView.findViewById(R.id.edittext_no1);
        final EditText no2 = (EditText) dialogView.findViewById(R.id.edittext_no2);
        final EditText no3 = (EditText) dialogView.findViewById(R.id.edittext_no3);

        no1.setTransformationMethod(null);
        no2.setTransformationMethod(null);
        no3.setTransformationMethod(null);

        no1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(no1.getText().toString().length() == 2){
                    no2.requestFocus();
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
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        LinearLayout submitButton = (LinearLayout) dialogView.findViewById(R.id.button_search);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t1, t2, t3;
                if(!no1.getText().toString().trim().isEmpty())
                    t1 = Integer.parseInt(no1.getText().toString());
                else t1 = -1;
                if(!no2.getText().toString().trim().isEmpty())
                    t2 = Integer.parseInt(no2.getText().toString());
                else t2 = -1;
                if(!no3.getText().toString().trim().isEmpty())
                    t3 = Integer.parseInt(no3.getText().toString());
                else t3 = -1;
                onButtonListener.filterValues(t1,t2,t3);
                dismiss();
            }
        });

        builder.setView(dialogView);
        return builder.create();

    }

}
