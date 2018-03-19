package com.example.kenlow.rentasaur2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ken Low on 12-Mar-18.
 */

public class PropertyRecyclerAdapter extends RecyclerView.Adapter<PropertyRecyclerAdapter.ViewHolder> {

    // Receiving Data Over Here
    public List<PropertyPost> property_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;


    public PropertyRecyclerAdapter(List<PropertyPost> property_list, Context context){
        this.property_list = property_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_list_item,parent, false);

        context = parent.getContext();

        firebaseFirestore = FirebaseFirestore.getInstance();

        ViewHolder viewHolder = new ViewHolder(view,context, property_list );

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        // Receive data as string (the description)
        String desc_data = property_list.get(position).getDesc();

        holder.setDescText(desc_data);

        String image_url = property_list.get(position).getImage_url();
        holder.setPropertyImage(image_url);

        String user_id = property_list.get(position).getUser_id();

        // User Data will be retrieved here
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    // retrieve user name from user id
                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image");

//                    holder.setOwnerData(userName, userImage);

                } else {

                    // Firebase Exception


                }
            }
        });



    }

    // Populate number of items in the recycler view
    @Override
    public int getItemCount() {

        return property_list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final List<PropertyPost> property_list;
        private View mView;

        private TextView descView;

        private ImageView propertyImageView;

        private Context context;


        public ViewHolder(View itemView, Context context, List<PropertyPost> property_list) {
            super(itemView);
            this.property_list = property_list;
            this.context = context;
            itemView.setOnClickListener(this);

            mView = itemView;
        }
        //-------------When user clicks on a card----------------///
        @Override
        public void onClick(View v) {
        int position = getAdapterPosition();
            PropertyPost property_list = this.property_list.get(position);

            Intent property_profile_intent = new Intent(context,PropertyProfile.class);

            property_profile_intent.putExtra("property_profile_image", property_list.getImage_url());
            property_profile_intent.putExtra("property_profile_title", property_list.getDesc());
            property_profile_intent.putExtra("property_profile_address",property_list.getAddress_line_1() + " " + property_list.getAddress_line_2());
            property_profile_intent.putExtra("property_profile_rental","RM " + property_list.getMonthly_rental());
            property_profile_intent.putExtra("property_profile_info",property_list.getExtra_info());

            this.context.startActivity(property_profile_intent);


        }

        //-------------------Set Address of the property----------------///
        public void setDescText(String descText){
            descView = mView.findViewById(R.id.property_desc);
            descView.setText(descText);
        }

        public void setPropertyImage(String downloadUri){
            propertyImageView = mView.findViewById(R.id.property_image);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.post_placeholder);
            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).into(propertyImageView);


        }



//        public void setOwnerData(String name, String image){
//
//            propertyOwnerImage = mView.findViewById(R.id.property_owner_image);
//            propertyOwnerName = mView.findViewById(R.id.property_owner_name);
//
//            propertyOwnerName.setText(name);
//
//            // Placeholder thumbnail
//            RequestOptions placeholderOption = new RequestOptions();
//            placeholderOption.placeholder(R.drawable.profile_placeholder);
//
//            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(image).into(propertyOwnerImage);
//
//
//        }

    }
}
