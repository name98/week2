package com.example.week2.adapters;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week2.R;
import com.example.week2.items.MyColors;
import com.example.week2.control.Router;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorItemAdapter extends RecyclerView.Adapter<ColorItemAdapter.ItemHolder> {
    private String noteBackgroundColor;
    private ArrayList<String> colors;

    public void setNoteBackgroundColor(String noteBackgroundColor) {
        this.noteBackgroundColor = noteBackgroundColor;
        notifyDataSetChanged();
    }

    public void setAllItemColors(ArrayList<String> colors) {
        this.colors = colors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.color_item,
                        parent,
                        false
                ));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        String color = colors.get(position);
        holder.initValues(color);
        holder.setColorListener(color);

    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.colorItemDoneImageView)
        ImageView doneImageView;
        @BindView(R.id.colorItemFrameColorFrameLayout)
        FrameLayout colorFrameFrameLayout;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void initValues(String color){
            Drawable background = colorFrameFrameLayout.getBackground();
            doneImageView.setVisibility(View.INVISIBLE);
            if(background instanceof ShapeDrawable){
                ShapeDrawable shapeDrawable = (ShapeDrawable) background;
                shapeDrawable.getPaint().setColor(MyColors.provideColorByName(color,itemView.getContext()));
            }
            else if (background instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) background;
                gradientDrawable.setColor(MyColors.provideColorByName(color,itemView.getContext()));
            }
            if(color.equals(noteBackgroundColor)){
                doneImageView.setVisibility(View.VISIBLE);
            }
        }
        void setColorListener(final String color){
            colorFrameFrameLayout.setOnClickListener(v -> {

                sendMessageToFragment(color);
                setNoteBackgroundColor(color);

            });
        }
        void sendMessageToFragment(String color){
            Router.reloadEditFragment(itemView.getContext(),color);
        }

    }
}
