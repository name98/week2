package com.example.week2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.week2.R;
import com.example.week2.adapters.NoteItemAdapter;
import com.example.week2.database.DataBaseHelper;
import com.example.week2.control.Router;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    public static final String ARGUMENT_DATABASE_STATE = "StateKey";
    @BindView(R.id.mainFragmentNotesRecycleView)
    public RecyclerView notesRecycleView;
    @BindView(R.id.mainFragmentAddNewNoteButton)
    public Button newNoteButton;
    private Unbinder unBinder = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        unBinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        assert arguments != null;
        boolean isEmptyMode = arguments.getBoolean(ARGUMENT_DATABASE_STATE);
        if (!isEmptyMode) {
            initRecycleView();
        } else {
            TextView emptyDataBase = view.findViewById(R.id.mainFragmentNoNotesTextView);
            emptyDataBase.setVisibility(View.VISIBLE);
        }
        initAddButton();
    }

    private void initRecycleView() {
        NoteItemAdapter noteItemAdapter = new NoteItemAdapter();
        noteItemAdapter.setNotes(DataBaseHelper.getSortedNotes(getContext()));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        notesRecycleView.setAdapter(noteItemAdapter);
        notesRecycleView.setLayoutManager(layoutManager);
    }

    private void initAddButton() {
        newNoteButton.setOnClickListener(v -> Router.addEditFragmentEditModeNewNoteMode(getContext()));
    }

    public static MainFragment newInstance(boolean isEmpty) {
        Bundle args = new Bundle();
        args.putBoolean(ARGUMENT_DATABASE_STATE, isEmpty);
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }
}
