package com.pofb.library.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pofb.library.adapters.BookListAdapter;
import com.pofb.library.R;
import com.pofb.library.model.HttpContextBundle;
import com.pofb.library.model.User;
import com.pofb.library.tasks.CheckLentBooksTask;
import com.pofb.library.tasks.LoginTask;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.protocol.HttpContext;


public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter bookListAdapter;
    private RecyclerView.LayoutManager layoutManager;



    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.books_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        CheckLentBooksTask check = new CheckLentBooksTask();
        check.execute(HttpContextBundle.getContext());

        try {
            bookListAdapter = new BookListAdapter(check.get(), (BookListAdapter.ItemClickListener) getActivity());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        recyclerView.setAdapter(bookListAdapter);

    }


}
