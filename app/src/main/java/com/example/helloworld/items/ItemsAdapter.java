package com.example.helloworld.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> itemsList = new ArrayList();

    public void setData(List<Item> itemsList){
        this.itemsList.clear();
        this.itemsList = itemsList;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private  TextView itemTitle;
        private  TextView itemValue;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.text_view_item_title);
            itemValue = itemView.findViewById(R.id.text_view_item_value);
        }
        private void bind(Item item){
            itemTitle.setText(item.getTitle());
            itemValue.setText(item.getValue());
        }
    }
}
