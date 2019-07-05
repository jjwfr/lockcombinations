// JUSTIN WEI, 91618787

package com.example.justin.lockcombinations;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ForgotFragment.OnButtonListener, AddFragment.OnButtonListener{

    private int filterno1 = -1;
    private int filterno2 = -1;
    private int filterno3 = -1;
    ArrayList<Combination> comboArray = new ArrayList<>();
    ArrayList<Combination> filteredArray = new ArrayList<>();
    GridView myGrid;
    ComboAdapter myAdapter;
    LinearLayout buttonForgot;
    LinearLayout buttonAdd;
    TextView buttonText;
    TextView statusText;
    private int maxId = 0;

    private FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_about:
                DialogFragment aboutDialog = new AboutFragment();
                aboutDialog.show(getFragmentManager(),"aboutFragment");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void addCombo(int no1, int no2, int no3){

        // adds a combination to the local ArrayList, as well as adds the combination to Google Firebase Database.

        myRef = database.getReference("Combos");
        myRef.child(Integer.toString(maxId)).setValue(new Combination(no1,no2,no3,new Date(),maxId));
        comboArray.add(new Combination(no1,no2,no3,new Date(),maxId++));
        myRef = database.getReference("maxId");
        myRef.setValue(maxId);
        updateFilter();
        myAdapter.notifyDataSetChanged();

    }


    @Override
    public void filterValues(int no1, int no2, int no3){

        // changes the filter numbers, and refreshes the list adapter.

        filterno1 = no1;
        filterno2 = no2;
        filterno3 = no3;

        updateFilter();

        myAdapter.notifyDataSetChanged();

    }

    public void updateFilter(){

        //sift through ComboArray, and imports combinations that qualify through filterd numbers into filterArray.
        //filterArray is used for display through list adapter.

        filteredArray.clear();
        for(Combination c : comboArray){

            if(c.hasNo(filterno1) && c.hasNo(filterno2) && c.hasNo(filterno3)){
                filteredArray.add(c);
            }

        }

        myAdapter.notifyDataSetChanged();

        //changes "forgot combination?" button to "clear filters" button, as well as updates status text to show current filters.

        if(filterno1 != -1 || filterno2 != -1 || filterno3 != -1) {
            buttonText.setText("Clear filter");
            buttonForgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearFilters();
                }
            });
            String filterString = "Filtered with [ ";
            if(filterno1 != -1)
                filterString += filterno1;
            else filterString += "-";
            filterString += " / ";
            if(filterno2 != -1)
                filterString += filterno2;
            else filterString += "-";
            filterString += " / ";
            if(filterno3 != -1)
                filterString += filterno3;
            else filterString += "-";
            filterString += " ]";
            statusText.setText(filterString);
            buttonForgot.setBackground(getDrawable(R.drawable.button_red));
        }


    }


    private void removeCombo(Combination c){

        //removes combination from comboArray, filteredArray, and google Firebase database.

        filteredArray.remove(c);
        comboArray.remove(c);
        myRef = database.getReference("Combos/" + c.getId());
        myRef.removeValue();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // initialize db

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("maxId");

        // get maxId value to assign new Ids when adding combinations

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long max = (Long)dataSnapshot.getValue();
                maxId = max.intValue();
                Log.i("test","Max id is " + maxId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // read all existing combinations from database and load them into comboArray.

        myRef = database.getReference("Combos");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){

                    Combination combo = snap.getValue(Combination.class);
                    comboArray.add(combo);
                    updateFilter();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // gridview based list

        myGrid = (GridView) findViewById(R.id.comboList);
        myAdapter = new ComboAdapter(this, filteredArray);
        myGrid.setAdapter(myAdapter);

        // long click to delete combination from arraylists and firebase.

        myGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Combination c = filteredArray.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AppDialog);
                builder.setTitle("Remove combination?");
                builder.setMessage("Confirm deletion of: " + c.getNo1() + " - " + c.getNo2() + " - " + c.getNo3());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        removeCombo(c);
                        myAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();


                return false;
            }
        });

        buttonText = (TextView) findViewById(R.id.text_button);
        statusText = (TextView) findViewById(R.id.status_text);

        // open dialog to input filter numbers

        buttonForgot = (LinearLayout) findViewById(R.id.leftbutton);
        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilterDialog();
            }
        });

        // open dialog to input new combo

        buttonAdd = (LinearLayout) findViewById(R.id.rightbutton);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addFragment = new AddFragment();
                addFragment.show(getFragmentManager(),"addFragment");
            }
        });




    }

    //open filter dialog
    public void openFilterDialog(){
        DialogFragment forgotFragment = new ForgotFragment();
        forgotFragment.show(getFragmentManager(),"forgotFragment");
    }


    // clear filters, reset and display all combos
    public void clearFilters(){
        filterno1 = -1;
        filterno2 = -1;
        filterno3 = -1;
        updateFilter();
        buttonForgot.setBackground(getDrawable(R.drawable.button));
        buttonText.setText("Forgot combination?");
        statusText.setText(R.string.status_all);
        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilterDialog();
            }
        });
    }

}
