package com.example.kenlow.rentasaur2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

        final String tenantName_data = tenant_list.get(position).getTenant_name();

        holder.setTenantNameText(tenantName_data);

        final String tenantProperty_name = tenant_list.get(position).getTenant_property();

        holder.setTenantPropertyText(tenantProperty_name);

        final String tenantPhone_data = tenant_list.get(position).getTenant_phone();



        final String tenant_id = tenant_list.get(position).tenantId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Tenant ID: " + tenant_id, Toast.LENGTH_SHORT).show();
                Intent tenant_profile_intent = new Intent(context, TenantProfile.class);
                tenant_profile_intent.putExtra("tenant_name", tenantName_data);
                tenant_profile_intent.putExtra("tenant_id", tenant_id);
                tenant_profile_intent.putExtra("tenant_property", tenantProperty_name);
                tenant_profile_intent.putExtra("tenant_phone",tenantPhone_data);
                context.startActivity(tenant_profile_intent);
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
        private TextView tenantPropertyView;

        public ViewHolder(View itemView) {
            super(itemView);
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
    }
}

