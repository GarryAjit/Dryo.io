package com.example.intel.dryoio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentPendingBillsAdapter extends RecyclerView.Adapter<StudentPendingBillsAdapter.ProductViewHolder> {

    private Context context;
    private List<Bill> pendingBills;
    private String studentUsername;
    List<Bill> pendingBillsList = new ArrayList<>();

    public StudentPendingBillsAdapter(Context context, List<Bill> pendingBills,String studentUsername)
    {
        this.context = context;
        this.pendingBills = pendingBills;
        this.studentUsername = studentUsername;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_pending_bills_item, null);
        return new StudentPendingBillsAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        Bill bill = pendingBills.get(position);
        holder.tvUsername.setText(bill.getStudentUsername());
        holder.tvAmount.setText(String.valueOf(bill.getTotalAmount()));

        final String laundryUsername = pendingBills.get(position).getLaundryVendorUsername();
        final String currentDate = pendingBills.get(position).getCurrentDate();
        final String dueDate = pendingBills.get(position).getDueDate();
        final int tshirt = pendingBills.get(position).getTshirtQuantity();
        final int towel = pendingBills.get(position).getTowelQuantity();
        final int shirt = pendingBills.get(position).getShirtQuantity();
        final int pant = pendingBills.get(position).getPantQuantity();
        final int hanky = pendingBills.get(position).getHankyQuantity();
        final int blanket = pendingBills.get(position).getBlanketQuantity();
        final int socks = pendingBills.get(position).getSocksQuantity();
        final int underwear = pendingBills.get(position).getUnderwearQuantity();
        final int total = pendingBills.get(position).getTotalAmount();

        holder.ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Student_laundry_view_bill.class);
                intent.putExtra("username",laundryUsername);
                intent.putExtra("studentUsername",studentUsername);
                intent.putExtra("currentDate",currentDate);
                intent.putExtra("dueDate",dueDate);
                intent.putExtra("tshirt",tshirt);
                intent.putExtra("shirt",shirt);
                intent.putExtra("towel",towel);
                intent.putExtra("pant",pant);
                intent.putExtra("hanky",hanky);
                intent.putExtra("blanket",blanket);
                intent.putExtra("socks",socks);
                intent.putExtra("underwear",underwear);
                intent.putExtra("total",total);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pendingBills.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername, tvAmount, tvBill, tvDue;
        ImageView ivArrow;


        public ProductViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvBill = itemView.findViewById(R.id.tvBill);
            tvDue = itemView.findViewById(R.id.tvDue);
            ivArrow = itemView.findViewById(R.id.ivArrow);

        }
    }
}
