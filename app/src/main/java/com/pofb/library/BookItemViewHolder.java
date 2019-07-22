package com.pofb.library;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class BookItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final TextView title;

    public BookItemViewHolder(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.book_title);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(v.getContext(), MainActivity.class);
        it.putExtra("hue", (Parcelable) v.getTag());
        v.getContext().startActivity(it);
    }
}
