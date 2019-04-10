package com.example.intel.dryoio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CompleteBillsAdapter  extends RecyclerView.Adapter<CompleteBillsAdapter.ProductViewHolder>
{
    private Context context;
    private List<Bill> pendingBills;
    private String laundryUsername;

    public CompleteBillsAdapter(Context context, List<Bill> pendingBills,String laundryUsername)
    {
        this.context = context;
        this.pendingBills = pendingBills;
        this.laundryUsername = laundryUsername;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pending_bills_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        Bill bill = pendingBills.get(position);
        holder.tvUsername.setText(bill.getStudentUsername());
        holder.tvAmount.setText(String.valueOf(bill.getTotalAmount()));

        holder.ivArrow.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return pendingBills.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername, tvAmount;
        ImageView ivArrow;


        public ProductViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            ivArrow = itemView.findViewById(R.id.ivArrow);

        }
    }
}
