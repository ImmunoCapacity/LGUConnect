package com.lguconnect.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lguconnect.R;
import com.lguconnect.models.Announcement;
import com.lguconnect.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.Holder> {
    public interface Listener {
        void onOpen(Announcement a);
        default void onEdit(Announcement a) {}
        default void onDelete(Announcement a) {}
    }

    private final List<Announcement> items = new ArrayList<>();
    private final Listener listener;
    private final boolean adminMode;

    public AnnouncementAdapter(boolean adminMode, Listener listener) {
        this.adminMode = adminMode;
        this.listener = listener;
    }

    public void submit(List<Announcement> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        Announcement item = items.get(position);
        h.title.setText(item.getTitle());
        h.body.setText(item.getBody());
        h.category.setText(item.getCategory());
        h.unreadDot.setVisibility(item.isUnread() ? View.VISIBLE : View.INVISIBLE);
        h.time.setText(TimeUtils.relativeTime(item.getPostedAtMillis()));

        try { h.category.setBackgroundColor(Color.parseColor(
                item.getCategoryColor() == null ? "#607D8B" : item.getCategoryColor()));
        } catch (Exception e) { h.category.setBackgroundColor(Color.GRAY); }

        // Status badge (admin mode)
        if (h.tvStatus != null) {
            if (adminMode) {
                h.tvStatus.setVisibility(View.VISIBLE);
                Announcement.Status st = item.getStatus();
                h.tvStatus.setText(st.name());
                switch (st) {
                    case PUBLISHED: h.tvStatus.setBackgroundColor(Color.parseColor("#388E3C")); break;
                    case DRAFT:     h.tvStatus.setBackgroundColor(Color.parseColor("#F4511E")); break;
                    case EXPIRED:   h.tvStatus.setBackgroundColor(Color.parseColor("#607D8B")); break;
                }
            } else {
                h.tvStatus.setVisibility(View.GONE);
            }
        }

        h.itemView.setOnClickListener(v -> listener.onOpen(item));
        if (h.edit != null) h.edit.setVisibility(adminMode ? View.VISIBLE : View.GONE);
        if (h.delete != null) h.delete.setVisibility(adminMode ? View.VISIBLE : View.GONE);
        if (h.edit != null) h.edit.setOnClickListener(v -> listener.onEdit(item));
        if (h.delete != null) h.delete.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        TextView category, title, body, time, tvStatus;
        View unreadDot;
        MaterialButton edit, delete;

        Holder(@NonNull View v) {
            super(v);
            category = v.findViewById(R.id.tvCategory);
            title = v.findViewById(R.id.tvTitle);
            body = v.findViewById(R.id.tvBodyPreview);
            time = v.findViewById(R.id.tvTimestamp);
            unreadDot = v.findViewById(R.id.viewUnreadDot);
            tvStatus = v.findViewById(R.id.tvStatus);
            edit = v.findViewById(R.id.btnEdit);
            delete = v.findViewById(R.id.btnDelete);
        }
    }
}
