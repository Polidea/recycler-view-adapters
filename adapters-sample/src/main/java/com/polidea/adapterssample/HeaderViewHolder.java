package com.polidea.adapterssample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        titleTextView = (TextView) itemView;
    }
}
