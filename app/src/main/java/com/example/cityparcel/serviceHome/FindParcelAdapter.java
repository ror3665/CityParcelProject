package com.example.cityparcel.serviceHome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cityparcel.R;
import java.util.ArrayList;

public class FindParcelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FindParcelNode> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_parcel, parent, false);
        return new ViewHolderFindParcel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderFindParcel)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(FindParcelNode data) {
        listData.add(data);
    }
}