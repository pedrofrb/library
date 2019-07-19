package com.pofb.library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pofb.library.model.Book;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookItemViewHolder> {

    private List<Book> books;

    public BookListAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BookItemViewHolder viewHolder = new BookItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_main, viewGroup, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemViewHolder bookItemViewHolder, int position) {
        Book book = books.get(position);
        bookItemViewHolder.title.setText(book.getTitle());

    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
