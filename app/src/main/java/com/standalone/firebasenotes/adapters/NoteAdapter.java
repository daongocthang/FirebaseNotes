package com.standalone.firebasenotes.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    ArrayList<Note> itemList;
    OnItemClickListener onItemClickListener;

    public NoteAdapter() {
    }

    public View instantiateItemView(@LayoutRes int resId, @NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(instantiateItemView(R.layout.item_note, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = itemList.get(position);

        holder.tvTitle.setText(note.getTitle());
        holder.tvSubtitle.setText(note.getContent());

        if (onItemClickListener != null)
            holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(note));
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(ArrayList<Note> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.el_title);
            tvSubtitle = itemView.findViewById(R.id.el_subtitle);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Note note);
    }
}
