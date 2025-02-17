package com.berete.go4lunch.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.berete.go4lunch.R;

import java.util.Collections;

/**
 * A fragment representing a list of Items.
 */
public class conversationsListFragment extends Fragment {


    public conversationsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.conversation_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ConversationsListAdapter(Collections.singletonList("TODO")));

        return view;
    }
}