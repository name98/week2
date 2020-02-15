package com.example.week2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.week2.R;
import com.example.week2.adapters.ColorItemAdapter;
import com.example.week2.database.DataBaseHelper;
import com.example.week2.control.Router;
import com.example.week2.items.MyColors;
import com.example.week2.items.NoteItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;


public class EditFragment extends Fragment {

    private NoteItem note;
    private boolean isEditMode;
    private static final String ARGUMENT_EDIT_MODE = "EditModeKey";
    private static final String ARGUMENT_NOTE_ID = "NoteId";
    @BindView(R.id.editFragmentEditTitleEditText)
    public EditText title;
    @BindView(R.id.editFragmentEditDecryptionEditText)
    public EditText description;
    private Unbinder unBinder = null;
    @BindView(R.id.editFragmentMainPaneLinerLayout)
    public LinearLayout noteBackgroundLinerLayout;
    @BindView(R.id.editFragmentColorPaletteRecycleView)
    public RecyclerView colorsPane;
    private ColorItemAdapter colorsPaneAdapter = new ColorItemAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment, container, false);
        unBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        assert arguments != null;
        isEditMode = arguments.getBoolean(ARGUMENT_EDIT_MODE, false);
        if (isEditMode) {
            note = provideNoteItemById(arguments.getInt(ARGUMENT_NOTE_ID));
            editMode();
        } else {
            createMode();
        }
        initBottomPane();
        setColorPane();
    }

    private NoteItem provideNoteItemById(int noteItemId) {
        return DataBaseHelper.getNoteById(getContext(), noteItemId);
    }

    private void createMode() {
        note = new NoteItem();
        note.setColor("WHITE");
    }

    private void editMode() {
        title.setText(note.getTitle());
        description.setText(note.getDescription());
    }

    private void setColorPane(){
        noteBackgroundLinerLayout.setBackgroundColor(getResources().getColor(MyColors.provideColorByName(note.getColor())));
    }

    private void initBottomPane() {
        colorsPaneAdapter.setNoteBackgroundColor(note.getColor());
        colorsPaneAdapter.setAllItemColors(MyColors.provideDataColors());
        LinearLayoutManager colorsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        colorsPane.setAdapter(colorsPaneAdapter);
        colorsPane.setLayoutManager(colorsLayoutManager);
    }

    public void reloadColorsNoteItem(String color) {
        note.setColor(color);
        setColorPane();
    }

    @Override
    public void onDestroy() {
        Context fragmentContext = getContext();
        if (isEditMode) {
            if (note.getDescription()==null && note.getTitle()==null)
                DataBaseHelper.deleteNote(fragmentContext,note);
            else
                DataBaseHelper.upDate(getContext(), note);
        } else {
            if (note.getTitle()!=null || note.getDescription()!=null)
                DataBaseHelper.insert(fragmentContext, note);
        }
        Router.reloadMainFragment(fragmentContext);
        super.onDestroy();
        unBinder.unbind();
    }

    @OnTextChanged(value = R.id.editFragmentEditTitleEditText, callback = AFTER_TEXT_CHANGED)
    void titleAfterTextChanged(Editable text) {
        if (text!=null) {
            note.setTitle(text.toString());
        } else note.setTitle(null);
    }

    @OnTextChanged(value = R.id.editFragmentEditDecryptionEditText, callback = AFTER_TEXT_CHANGED)
    void descriptionAfterTextChanged(Editable text) {
        if (!text.toString().isEmpty()) {
            note.setDescription(text.toString());
        } else note.setDescription(null);
    }

    static public EditFragment newInstance(int noteId, boolean editMode) {
        Bundle args = new Bundle();
        args.putBoolean(ARGUMENT_EDIT_MODE, editMode);
        args.putInt(ARGUMENT_NOTE_ID, noteId);
        EditFragment editFragment = new EditFragment();
        editFragment.setArguments(args);
        return editFragment;
    }

    static public EditFragment newInstance() {
        Bundle args = new Bundle();
        args.putBoolean(ARGUMENT_EDIT_MODE, false);
        EditFragment fragment = new EditFragment();
        fragment.setArguments(args);
        return fragment;
    }




}
