package com.example.cityparcel.deliveryman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityparcel.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ParcelManagementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ParcelNode> listData = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private Context context;
    public ParcelManagementAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parcel_management, parent, false);
        return new ParcelManagementAdapter.ViewHolderParcelManagement(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((ParcelManagementAdapter.ViewHolderParcelManagement) holder).onBind(listData.get(position));

        ((ParcelManagementAdapter.ViewHolderParcelManagement) holder).deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listData.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(ParcelNode data) {
        listData.add(data);
    }

    private ParcelManagementAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(ParcelManagementAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolderParcelManagement extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDestination;
        private TextView textViewPrice;
        private Button deleteBtn;
        ViewHolderParcelManagement(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_itemPM_title);
            textViewDestination = itemView.findViewById(R.id.textView_itemPM_destination);
            textViewPrice = itemView.findViewById(R.id.textView_itemPM_price);
            deleteBtn = itemView.findViewById(R.id.button_itemPM_completed);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

        }

        public void onBind(ParcelNode data) {
            String price = NumberFormat.getCurrencyInstance(Locale.KOREA).format(Integer.parseInt(data.getPrice())); //원화 표시
            final String index = Integer.toString(data.getIndex());
            textViewTitle.setText(data.getTitle());
            textViewDestination.setText("목적지: " + data.getDestination());
            textViewPrice.setText("운송비용: " + price);

        }
    }
}
