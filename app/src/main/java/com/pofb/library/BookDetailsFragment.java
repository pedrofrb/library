package com.pofb.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pofb.library.model.Book;
import com.pofb.library.model.User;
import com.pofb.library.tasks.LoginTask;
import com.pofb.library.tasks.RenovationBooksTask;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.protocol.HttpContext;


public class BookDetailsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "book";

    private Book book;


    public BookDetailsFragment() {
        // Required empty public constructor
    }


    public static BookDetailsFragment newInstance(Book b) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, b);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = (Book) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_details, container, false);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onBa(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView bookName = (TextView) getActivity().findViewById(R.id.book_name_book_details);
        TextView libraryName = (TextView) getActivity().findViewById(R.id.name_library_book_details);
        TextView circulationDate = (TextView) getActivity().findViewById(R.id.circulation_date_book_details);
        bookName.setText(book.getTitle());
        libraryName.setText(book.getOriginLibrary());
        circulationDate.setText(book.getCirculationCode());
        Button button = (Button) view.findViewById(R.id.confirm_renovation_books_details);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpContext localContext = null;

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                User user = new User(sharedPreferences.getString("username", ""), sharedPreferences.getString("password", ""));
                LoginTask login = new LoginTask();
                try {
                    localContext = login.execute(user).get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                RenovationBooksTask renovationBooksTask = new RenovationBooksTask(book);
                renovationBooksTask.execute(localContext);
            }
        });
    }


}