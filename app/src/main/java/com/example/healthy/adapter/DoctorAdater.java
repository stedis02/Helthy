package com.example.healthy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthy.Activity.DoctorInformationActivity;
import com.example.healthy.BD.Constants;
import com.example.healthy.BD.DBManager;
import com.example.healthy.Doctors.Doctor;

import com.example.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdater extends RecyclerView.Adapter<DoctorAdater.MainViewHolder> {
    private Context context;
    private List<Doctor> doctorlist;

    public DoctorAdater(Context context) {
        this.context = context;
        doctorlist = new ArrayList<>();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_list, parent, false);
        return new MainViewHolder(view, context, doctorlist);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.setData(doctorlist.get(position));
    }

    @Override
    public int getItemCount() {
        return doctorlist.size();
    }

    public void updateAdapter(List<Doctor> newList){
        doctorlist.clear();
        doctorlist.addAll(newList);
        notifyDataSetChanged();

    }


// doctor list
    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public MainViewHolder(@NonNull View itemView, Context context,  List<Doctor> doctorlist) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitel);
            noteDesc = itemView.findViewById(R.id.noteDesc);
            itemView.setOnClickListener(this);
            this.context = context;
            this.doctorlist = doctorlist;
        }
        private  List<Doctor> doctorlist;
        private Context context;
        private TextView noteTitle;
        private TextView noteDesc;
public void setData(Doctor doctor){
    String Name = doctor.getName();
    noteTitle.setText(Name);

}

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DoctorInformationActivity.class);
            intent.putExtra(Constants.ListKey, doctorlist.get(getAdapterPosition()));
            intent.putExtra(Constants.NoteEditKey, false);
            context.startActivity(intent);

        }
    }
    public void DeleteItem(int position, DBManager dbManager){
        dbManager.DeleteDoctor(doctorlist.get(position).getId());
        doctorlist.remove(position);
        notifyItemRangeChanged(0, doctorlist.size());
        notifyItemRemoved(position);
    }
}