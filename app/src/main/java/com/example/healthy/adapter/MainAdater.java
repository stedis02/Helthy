package com.example.healthy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthy.BD.Constants;
import com.example.healthy.BD.DBManager;
import com.example.healthy.Notes.Note;
import com.example.healthy.NotesActivity;
import com.example.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Note> notelist;

    public MainAdater(Context context) {
        this.context = context;
        notelist = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @NonNull
    @Override
    public   RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType) {
            case 0:
                View view = LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
                return new MainNoteViewHolder(view, context, notelist);
            case 2:
                View view2 = LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
                return new MainDoctorViewHolder(view2, context);
            default:
                View view3 = LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
                return new MainDefaultViewHolder(view3);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0: MainNoteViewHolder mainNoteViewHolder = (MainNoteViewHolder)holder;
                mainNoteViewHolder.setData(notelist.get(position));
                break;
            case 2: MainDoctorViewHolder mainDoctorViewHolder = (MainDoctorViewHolder)holder;
            break;
        }
    }




    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public void updateAdapter(List<Note> newList) {
        notelist.clear();
        notelist.addAll(newList);
        notifyDataSetChanged();

    }

    class MainDefaultViewHolder extends RecyclerView.ViewHolder{
        public MainDefaultViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MainNoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public MainNoteViewHolder(@NonNull View itemView, Context context, List<Note> notelist) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitel);
            noteDesc = itemView.findViewById(R.id.noteDesc);
            itemView.setOnClickListener(this);
            this.context = context;
            this.notelist = notelist;
        }

        private List<Note> notelist;
        private Context context;
        private TextView noteTitle;
        private TextView noteDesc;

        public void setData(Note note) {
            String Title = note.getDiscription();
            if (Title.length() > 20) {
                Title = Title.substring(0, 20) + "...";
            }
            noteTitle.setText(note.getTitle());
            String Disc = note.getDiscription();
            if (Disc.length() > 20) {
                Disc = Disc.substring(0, 20) + "...";
            }
            noteDesc.setText(Disc);

        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, NotesActivity.class);
            intent.putExtra(Constants.ListKey, notelist.get(getAdapterPosition()));
            intent.putExtra(Constants.NoteEditKey, false);
            context.startActivity(intent);

        }
    }

    class MainDoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MainDoctorViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = context;
        }
        private final Context context;

            @Override
        public void onClick(View view) {

        }
    }

    public void DeleteItem(int position, DBManager dbManager) {
        dbManager.DeleteNote(notelist.get(position).getId());
        notelist.remove(position);
        notifyItemRangeChanged(0, notelist.size());
        notifyItemRemoved(position);
    }
}
