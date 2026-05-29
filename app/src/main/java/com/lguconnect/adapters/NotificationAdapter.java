package com.lguconnect.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lguconnect.R;
import com.lguconnect.models.NotificationItem;
import com.lguconnect.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {
    private final List<NotificationItem> items = new ArrayList<>();

    public void submit(List<NotificationItem> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        NotificationItem n = items.get(position);
        h.title.setText(n.getTitle());
        h.body.setText(n.getBody());
        h.time.setText(TimeUtils.relativeTime(n.getTimeMillis()));
        h.unreadDot.setVisibility(n.isUnread() ? View.VISIBLE : View.INVISIBLE);
        boolean emergency = n.getType() == NotificationItem.Type.EMERGENCY;
        h.icon.setText(emergency ? "⚠" : "ℹ");
        h.icon.setBackgroundColor(emergency ?
                android.graphics.Color.parseColor("#FFEBEE") :
                android.graphics.Color.parseColor("#E3F2FD"));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        TextView icon, title, body, time;
        View unreadDot;
        Holder(@NonNull View v) {
            super(v);
            icon = v.findViewById(R.id.tvNotifIcon);
            title = v.findViewById(R.id.tvNotifTitle);
            body = v.findViewById(R.id.tvNotifBody);
            time = v.findViewById(R.id.tvNotifTime);
            unreadDot = v.findViewById(R.id.viewNotifUnread);
        }
    }
}
