package com.polidea.adapterssample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RowViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;

    public RowViewHolder(View itemView) {
        super(itemView);
        titleTextView = (TextView) itemView.findViewById(R.id.text_view);
    }
}
