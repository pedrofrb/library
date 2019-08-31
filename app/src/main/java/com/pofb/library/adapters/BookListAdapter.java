package com.pofb.library.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pofb.library.R;
import com.pofb.library.model.Book;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookItemViewHolder> {

    private List<Book> books;
    private ItemClickListener clickListener;

    public BookListAdapter(List<Book> books, BookListAdapter.ItemClickListener itemClickListener) {
        this.books = books;

        this.clickListener = itemClickListener;
    }

    public List<Book> getBooks() {
        return books;
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
        bookItemViewHolder.itemView.setTag(book);

    }


    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    public class BookItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView title;

        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.book_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, books.get(getAdapterPosition()));
        }

    }

    public interface ItemClickListener {
        void onClick(View view, Book b);
    }
}
