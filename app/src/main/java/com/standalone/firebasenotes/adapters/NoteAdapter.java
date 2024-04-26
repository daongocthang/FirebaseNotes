package com.standalone.firebasenotes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.controllers.FireStoreHelper;
import com.standalone.firebasenotes.fragments.NoteDialogFragment;
import com.standalone.firebasenotes.interfaces.ItemInterface;
import com.standalone.firebasenotes.models.Note;
import com.standalone.firebasenotes.utils.ProgressDialog;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements ItemInterface {

    ArrayList<Note> itemList;
    final AppCompatActivity activity;
    FireStoreHelper<Note> helper;

    public NoteAdapter(AppCompatActivity activity) {
        this.activity = activity;
        helper = new FireStoreHelper<>("notes");

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

    @Override
    public void editItem(int pos) {
        Note note = itemList.get(pos);
        Bundle args = new Bundle();
        args.putSerializable("note", note);
        NoteDialogFragment fragment = new NoteDialogFragment();
        fragment.setArguments(args);
        fragment.show(activity.getSupportFragmentManager(), NoteDialogFragment.TAG);
    }

    @Override
    public void removeItem(int pos) {
        Note note = itemList.get(pos);
        itemList.remove(pos);
        notifyItemRemoved(pos);

        ProgressDialog progress = new ProgressDialog(activity);
        progress.show();
        helper.remove(note.getKey()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progress.dismiss();
            }
        });
    }

    @Override
    public Context getContext() {
        return this.activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(Note note) {
        for (int i = 0; i < itemList.size(); i++) {
            String key = itemList.get(i).getKey();
            if (note.getKey().equals(key)) {
                itemList.set(i, note);
                notifyDataSetChanged();
                return;
            }
        }
        itemList.add(note);
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

    public void fetchData() {
        ProgressDialog progress = new ProgressDialog(activity);
        progress.show();
        helper.fetch(Note.class, new FireStoreHelper.OnFetchCompleteListener<Note>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onFetchComplete(ArrayList<Note> data) {
                progress.dismiss();
                itemList = data;
                notifyDataSetChanged();
            }
        });
    }
}
