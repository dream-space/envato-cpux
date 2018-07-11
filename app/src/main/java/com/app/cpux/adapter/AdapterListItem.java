package com.app.cpux.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.cpux.R;
import com.app.cpux.model.TheInfo;

import java.util.ArrayList;


public class AdapterListItem extends ArrayAdapter<TheInfo> {

    LayoutInflater lInflater;
    Context ctx;
    ArrayList<TheInfo> mIdMap = new ArrayList<>();

    public AdapterListItem(Context context, int textViewResourceId, ArrayList<TheInfo> prod) {
        super(context, textViewResourceId, prod);
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ctx = context;
        for (int i = 0; i < prod.size(); i++) {
            mIdMap.add(prod.get(i));
        }
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.item_list_info, parent, false);
        if (!mIdMap.get(position).getName().equals("-")) {
            ((TextView) view.findViewById(R.id.textView1)).setText(mIdMap.get(position).getName());
            ((TextView) view.findViewById(R.id.textView2)).setText(mIdMap.get(position).getValue());
        } else {
            //Toast.makeText(ctx, "ok", Toast.LENGTH_SHORT).show();
            ((TextView) view.findViewById(R.id.textView1)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.textView2)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.textView3)).setVisibility(View.GONE);
            LinearLayout lyt = (LinearLayout) view.findViewById(R.id.lyt);
            lyt.setBackgroundColor(ctx.getResources().getColor(R.color.darker_grey));
            lyt.getLayoutParams().height = 2;
        }
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
