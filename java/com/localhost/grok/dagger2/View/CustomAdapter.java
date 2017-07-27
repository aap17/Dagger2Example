package com.localhost.grok.dagger2.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.localhost.grok.dagger2.R;
import com.localhost.grok.dagger2.workClasses.ContactJson;

import java.util.List;

/**
 * Created by grok on 3/14/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> {

    private List<ContactJson> jsons;


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView field1;
        TextView field2;
        public ItemViewHolder(View itemView) {
            super(itemView);
            field1=(TextView)itemView.findViewById(R.id.field1);
            field2=(TextView)itemView.findViewById(R.id.field2);
        }
    }

    public CustomAdapter(List<ContactJson> jsons)
    {
        this.jsons=jsons;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        ContactJson element = jsons.get(position);
        holder.field1.setText(element.getName());
        holder.field2.setText(element.getDescription());

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        ItemViewHolder holder = new ItemViewHolder(v);
        return holder;
    }

    @Override
    public int getItemCount() {
       return jsons.size();
    }
}
