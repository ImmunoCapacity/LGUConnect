package com.lguconnect.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lguconnect.R;
import com.lguconnect.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    public interface Listener { void onClick(Category c); }
    private final List<Category> items = new ArrayList<>();
    private final Listener listener;

    public CategoryAdapter(Listener listener) { this.listener = listener; }

    public void submit(List<Category> list) {
        items.clear(); items.addAll(list); notifyDataSetChanged();
    }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        Category c = items.get(position);
        h.name.setText(c.getName());
        try {
            int color = Color.parseColor(c.getColor());
            h.iconBg.setCardBackgroundColor(color);
        } catch (Exception e) {
            h.iconBg.setCardBackgroundColor(Color.GRAY);
        }
        h.itemView.setOnClickListener(v -> listener.onClick(c));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        CardView iconBg;
        TextView name;
        Holder(@NonNull View v) {
            super(v);
            iconBg = v.findViewById(R.id.cardCategoryIcon);
            name = v.findViewById(R.id.tvCategoryName);
        }
    }
}
