package com.example.week2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week2.R;
import com.example.week2.database.DataBaseHelper;

import com.example.week2.items.MyColors;
import com.example.week2.items.NoteItem;
import com.example.week2.control.Router;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.ItemHolder> {

    private ArrayList<NoteItem> notes=new ArrayList<>();
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.note_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        NoteItem note = notes.get(position);
        holder.setDecryption(note.getDescription());
        holder.setPaneColor(note.getColor());
        holder.setTitle(note.getTitle());
        holder.setPaneListener(position);
        holder.initPin(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(ArrayList<NoteItem> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.noteItemTitleTextView)
        TextView title;
        @BindView(R.id.noteItemDescriptionTextView)
        TextView decryption;
        @BindView(R.id.noteItemPaneCardView)
        CardView pane;
        @BindView(R.id.noteItemDeleteNoteImageView)
        ImageView deleteNote;
        @BindView(R.id.noteItemPinNoteImageView)
        ImageView pinNote;
        @BindView(R.id.noteItemCopyNoteImageView)
        ImageView copyNote;


        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void setPaneColor(String color){
            pane.setCardBackgroundColor(MyColors.provideColorByName(color,itemView.getContext()));
            FrameLayout topBorderFrameLayout = itemView.findViewById(R.id.noteItemBorderColorFrameLayout);
            topBorderFrameLayout.setBackgroundColor(MyColors.provideDarkColorByName(color,itemView.getContext()));
        }
        void initPin(int id){
            pinNote.setImageResource(R.drawable.ic_unpined);
            if(notes.get(id).isSignificance())
                pinNote.setImageResource(R.drawable.ic_pined);
        }
        void setPaneListener(final int id){
            Context context = itemView.getContext();
            pane.setOnClickListener(v -> Router.addEditFragmentEditModeNewNoteMode(context,notes.get(id).getId()));

            copyNote.setOnClickListener(v -> {
                DataBaseHelper.insert(itemView.getContext(),notes.get(id));
                reLoadFragment();
            });
            deleteNote.setOnClickListener(v -> {
                DataBaseHelper.deleteNote(context, notes.get(id));
                reLoadFragment();
            });
            pinNote.setOnClickListener(v -> {
                NoteItem note = notes.get(id);
                note.setSignificance(!notes.get(id).isSignificance());
                DataBaseHelper.upDate(context,note);
                reLoadFragment();
            });
        }

        void setTitle(String text){
            title.setText(text);
        }
        void setDecryption(String text){
            decryption.setText(text);
        }

        void reLoadFragment(){
            Router.reloadMainFragment(itemView.getContext());
        }
    }
}
