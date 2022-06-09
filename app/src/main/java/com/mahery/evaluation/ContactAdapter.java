package com.mahery.evaluation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Viewholder>  {

    private Context context;
    private ArrayList<Contact> contactList;

    // Constructor
    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liste_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Contact model = contactList.get(position);
        holder.nom.setText(model.getNom());
        holder.numero.setText(model.getNumero());
        holder.image.setImageResource(model.getImage());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return contactList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView nom, numero;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            nom = itemView.findViewById(R.id.nom);
            numero = itemView.findViewById(R.id.numero);
        }
    }
}
