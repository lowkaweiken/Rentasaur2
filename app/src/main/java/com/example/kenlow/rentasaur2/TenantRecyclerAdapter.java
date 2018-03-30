package com.example.kenlow.rentasaur2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ken Low on 28-Mar-18.
 */

public class TenantRecyclerAdapter extends RecyclerView.Adapter<TenantRecyclerAdapter.ViewHolder>{

    private static final String TAG = "TENANTRECYCLERADAPTER";
    public List<TenantPost> tenant_list;
    public Context context;
    public Date start_date;
    public Date end_date;
    public int daysRemaining;

    public TenantRecyclerAdapter(Context context, List<TenantPost> tenant_list){
        this.tenant_list = tenant_list;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenant_list_item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String tenantName_data = tenant_list.get(position).getTenant_name();

        holder.setTenantNameText(tenantName_data);

        final String tenantProperty_name = tenant_list.get(position).getTenant_property();

        holder.setTenantPropertyText(tenantProperty_name);

        final String tenantPhone_data = tenant_list.get(position).getTenant_phone();


        final String tenantMonthlyRental_data = tenant_list.get(position).getTenant_monthly_rental();
        final String tenantEmail_data = tenant_list.get(position).getTenant_email();
        final String tenant_start_date_data = tenant_list.get(position).getTenant_start_date();
        final String tenant_end_date_data = tenant_list.get(position).getTenant_end_date();

        startTime();

        holder.setDaysRemainingText("Rent Due in\n "+String.valueOf(daysRemaining) + " Days");

        //get first letter of each String item
        String firstLetter = String.valueOf(tenantName_data.charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.imageView.setImageDrawable(drawable);

        final String tenant_id = tenant_list.get(position).tenantId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Tenant Profile Accessed Succesfully " , Toast.LENGTH_SHORT).show();
                Intent tenant_profile_intent = new Intent(context, TenantProfile.class);
                tenant_profile_intent.putExtra("tenant_name", tenantName_data);
                tenant_profile_intent.putExtra("tenant_id", tenant_id);
                tenant_profile_intent.putExtra("tenant_property", tenantProperty_name);
                tenant_profile_intent.putExtra("tenant_phone",tenantPhone_data);

                tenant_profile_intent.putExtra("tenant_monthly_rental", tenantMonthlyRental_data);
                tenant_profile_intent.putExtra("tenant_email",tenantEmail_data);
                tenant_profile_intent.putExtra("tenant_start_date", tenant_start_date_data);
                tenant_profile_intent.putExtra("tenant_end_date",tenant_end_date_data);

                context.startActivity(tenant_profile_intent);

            }
        });
    }

    private void startTime() {
        long difference = getRemainingDays();
        daysRemaining = (int) (difference/(1000*60*60*24));

        Log.v(TAG, "DIFFERENCE IS: " + difference);
        Log.v(TAG, "DAYS REMAINING IS: " + daysRemaining);
    }

    //--------------------Logic Here-------------------//
    private long getRemainingDays() {

        Calendar dueDate = Calendar.getInstance();
        dueDate.set(Calendar.DATE, dueDate.getActualMaximum(Calendar.DATE));

        Calendar today = Calendar.getInstance();

        return dueDate.getTimeInMillis() - today.getTimeInMillis();

    }

    @Override
    public int getItemCount() {
        return tenant_list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        private View mView;
        private TextView tenantNameView;
        private TextView tenantPropertyView;
        private ImageView imageView;
        public TextView txtDaysRemain;



        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.avatar_image);
            mView = itemView;
        }

        public void setTenantNameText(String tenantNameText){
            tenantNameView = mView.findViewById(R.id.tenant_name);
            tenantNameView.setText(tenantNameText);
        }


        public void setTenantPropertyText(String tenantProperty_name) {
            tenantPropertyView = mView.findViewById(R.id.tenant_property);
            tenantPropertyView.setText(tenantProperty_name);
        }

        public void setDaysRemainingText(String daysRemainingText) {
            txtDaysRemain = mView.findViewById(R.id.txtDaysRemain);

            if (daysRemaining < 1) {
                txtDaysRemain.setText("RENT DUE TODAY");
                txtDaysRemain.setTextColor(Color.RED);
                return;
            }else if(daysRemaining < 8){
                txtDaysRemain.setTextColor(Color.RED);
            } else if (daysRemaining > 7 ){
                txtDaysRemain.setTextColor(Color.GREEN);
            }

            txtDaysRemain.setText(daysRemainingText);


        }
    }
}

