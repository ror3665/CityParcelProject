package com.example.cityparcel.serviceHome;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cityparcel.R;

public class ViewHolderFindParcel extends RecyclerView.ViewHolder {

private TextView textViewTitle;
private TextView textViewDestination;
private TextView textViewPrice;

    ViewHolderFindParcel(View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.textView_itemFP_title);
        textViewDestination = itemView.findViewById(R.id.textView_itemFP_destination);
        textViewPrice = itemView.findViewById(R.id.textView_itemFP_price);
    }

        public void onBind(FindParcelNode data) {
        textViewTitle.setText(data.getTitle());
        textViewDestination.setText(data.getDestination());
        textViewPrice.setText(data.getPrice());
    }
}
