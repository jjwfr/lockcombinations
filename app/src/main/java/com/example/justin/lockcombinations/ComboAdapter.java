// JUSTIN WEI, 91618787

package com.example.justin.lockcombinations;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ComboAdapter extends BaseAdapter{


    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Combination> mData;

    public ComboAdapter(Context context, ArrayList<Combination> items){

        mContext = context;
        mData = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount(){

        return mData.size();

    }

    @Override
    public Combination getItem(int position){

        return mData.get(position);

    }

    @Override
    public long getItemId(int position){

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Combination combo = (Combination) mData.get(position);

        View toReturn = mInflater.inflate(R.layout.listitem_combo, parent, false);

        TextView comboNo = (TextView) toReturn.findViewById(R.id.combination);
        TextView date = (TextView) toReturn.findViewById(R.id.date);

        comboNo.setText(combo.getNo1() + " - " + combo.getNo2() + " - " + combo.getNo3());
        date.setText(combo.getCreated().toString().substring(0,19));

        return toReturn;

    }




}
