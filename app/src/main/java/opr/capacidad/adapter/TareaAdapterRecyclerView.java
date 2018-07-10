package opr.capacidad.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import opr.capacidad.R;
import opr.capacidad.model.Tarea;

public class TareaAdapterRecyclerView extends RecyclerView.Adapter<TareaAdapterRecyclerView.TareaViewHolder> {

    private ArrayList<Tarea> tareas;
    private int resource;
    private Activity activity;

    public TareaAdapterRecyclerView(ArrayList<Tarea> tareas, int resource, Activity activity) {
        this.tareas = tareas;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public TareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TareaViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.tareaName.setText(tarea.getName());
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {

        private TextView tareaName;

        public TareaViewHolder(View itemView) {
            super(itemView);

            tareaName = itemView.findViewById(R.id.tareaName);

        }
    }
}
