package com.kratos47mhs.ganjoor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kratos47mhs.ganjoor.model.Drawer;
import com.kratos47mhs.ganjoor.R;

public class GanjoorAdaptor extends BaseAdapter {
    private static final int LENGTH = 9;
    private Context context;
    private Drawer[] drawers;

    public ClickListener listener;

    public interface ClickListener {
        void onClick(int i);
    }

    private class ViewHolder {
        public ImageView icon;
        public View item;
        public TextView title;

        private ViewHolder() {
        }
    }

    public GanjoorAdaptor(Context context2, ClickListener clickListener) {
        this.context = context2;
        this.listener = clickListener;
        populate();
    }

    private void populate() {
        this.drawers = new Drawer[9];
        this.drawers[0] = new Drawer(2, R.drawable.ic_border, R.string.save);
        this.drawers[1] = new Drawer(7, R.drawable.ic_adfreered, R.string.no_ad);
        this.drawers[2] = new Drawer(5, R.drawable.ic_apps, R.string.Other_Apps);
        this.drawers[3] = new Drawer(8, R.drawable.ic_rate, R.string.rate_app);
        this.drawers[4] = new Drawer(4, R.drawable.ic_email, R.string.contact_title);
        this.drawers[5] = Drawer.divider();
        this.drawers[6] = new Drawer(1, R.drawable.drawer_about, R.string.about);
        this.drawers[7] = new Drawer(10, R.drawable.ic_help, R.string.help);
        this.drawers[8] = new Drawer(9, R.drawable.ic_report, R.string.privacy);
    }

    public int getCount() {
        return 9;
    }

    public Drawer getItem(int i) {
        return this.drawers[i];
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return this.drawers[i].type > 0 ? 1 : 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final Drawer item = getItem(i);
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (item.type != 0) {
                assert layoutInflater != null;
                view = layoutInflater.inflate(R.layout.drawer_item, viewGroup, false);
                ViewHolder viewHolder2 = new ViewHolder();
                viewHolder2.icon = view.findViewById(R.id.icon);
                viewHolder2.title = view.findViewById(R.id.title);
                viewHolder2.item = view.findViewById(R.id.item);
                view.setTag(viewHolder2);
                viewHolder = viewHolder2;
            } else {
                assert layoutInflater != null;
                view = layoutInflater.inflate(R.layout.drawer_separator, viewGroup, false);
            }
        }
        if (item.type > 0) {
            if (viewHolder == null) {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.title.setText(item.title);
            viewHolder.item.setOnClickListener(view1 -> GanjoorAdaptor.this.listener.onClick(item.type));
            viewHolder.icon.setImageResource(item.resId);
        }
        return view;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public boolean isEnabled(int i) {
        return this.drawers[i].type > 1;
    }
}
