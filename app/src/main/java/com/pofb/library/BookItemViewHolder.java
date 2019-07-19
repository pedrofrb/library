package com.pofb.library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class BookItemViewHolder extends RecyclerView.ViewHolder {

    final TextView title;

    public BookItemViewHolder(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.book_title);
    }
}
