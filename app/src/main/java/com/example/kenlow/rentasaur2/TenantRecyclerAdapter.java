package com.example.kenlow.rentasaur2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ken Low on 28-Mar-18.
 */

public class TenantRecyclerAdapter extends RecyclerView.Adapter<TenantRecyclerAdapter.ViewHolder>{

    public List<TenantPost> tenant_list;
    public Context context;

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

        String tenantName_data = tenant_list.get(position).getTenant_name();

        holder.setTenantNameText(tenantName_data);

        final String tenant_id = tenant_list.get(position).tenantId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Tenant ID: " + tenant_id, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tenant_list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        private View mView;
        private TextView tenantNameView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTenantNameText(String tenantNameText){
            tenantNameView = mView.findViewById(R.id.tenant_name);
            tenantNameView.setText(tenantNameText);
        }




    }
}

