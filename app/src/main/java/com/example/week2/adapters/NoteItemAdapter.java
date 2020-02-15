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
        holder.bind(note,position);
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
        TextView titleTextView;
        @BindView(R.id.noteItemDescriptionTextView)
        TextView descriptionTextView;
        @BindView(R.id.noteItemPaneCardView)
        CardView paneCardView;
        @BindView(R.id.noteItemDeleteNoteImageView)
        ImageView deleteNoteImageView;
        @BindView(R.id.noteItemPinNoteImageView)
        ImageView pinNoteImageView;
        @BindView(R.id.noteItemCopyNoteImageView)
        ImageView copyNoteImageView;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        private void setPaneColor(String color){
            paneCardView.setCardBackgroundColor(MyColors.provideColorByName(color,itemView.getContext()));
            FrameLayout topBorderFrameLayout = itemView.findViewById(R.id.noteItemBorderColorFrameLayout);
            topBorderFrameLayout.setBackgroundColor(MyColors.provideDarkColorByName(color,itemView.getContext()));
        }

        private void initPin(int id){
            pinNoteImageView.setImageResource(R.drawable.ic_unpined);
            if(notes.get(id).isSignificance())
                pinNoteImageView.setImageResource(R.drawable.ic_pined);
        }

        private void setPaneListener(final int id){
            Context context = itemView.getContext();
            paneCardView.setOnClickListener(v -> Router.addEditFragmentEditModeNewNoteMode(context,notes.get(id).getId()));
            copyNoteImageView.setOnClickListener(v -> {
                DataBaseHelper.insert(itemView.getContext(),notes.get(id));
                reLoadFragment();
            });
            deleteNoteImageView.setOnClickListener(v -> {
                DataBaseHelper.deleteNote(context, notes.get(id));
                reLoadFragment();
            });
            pinNoteImageView.setOnClickListener(v -> {
                NoteItem note = notes.get(id);
                note.setSignificance(!notes.get(id).isSignificance());
                DataBaseHelper.upDate(context,note);
                reLoadFragment();
            });
        }

        private void setTitleTextView(String text){
            titleTextView.setText(text);
        }

        private void setDescriptionTextView(String text){
            descriptionTextView.setText(text);
        }

        private void reLoadFragment(){
            Router.reloadMainFragment(itemView.getContext());
        }

        void bind(NoteItem note, int position){
            setTitleTextView(note.getTitle());
            setPaneListener(position);
            setPaneColor(note.getColor());
            setDescriptionTextView(note.getDescription());
            initPin(position);
        }

    }
}
