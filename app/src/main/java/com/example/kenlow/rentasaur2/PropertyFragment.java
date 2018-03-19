package com.example.kenlow.rentasaur2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PropertyFragment extends Fragment {

    private RecyclerView property_list_view;
    private List<PropertyPost> property_list;

    private FirebaseFirestore firebaseFirestore;
    private PropertyRecyclerAdapter propertyRecyclerAdapter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String current_user_id;

    public Context context;



    public PropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_property, container, false);

        property_list = new ArrayList<>();

        property_list_view = view.findViewById(R.id.property_list_view);

        firebaseFirestore = FirebaseFirestore.getInstance();

        propertyRecyclerAdapter = new PropertyRecyclerAdapter(property_list, this.context);

        property_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        property_list_view.setAdapter(propertyRecyclerAdapter);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        // Start retrieving data for your property post
        // SnapshotListner help collect data in real time

        Query firstQuery = firebaseFirestore.collection("Posts").whereEqualTo("user_id", current_user_id).orderBy("timestamp", Query.Direction.DESCENDING);

        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {


            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                try{
                    // Check for document change
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        // Check if the item is added
                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            PropertyPost propertyPost = doc.getDocument().toObject(PropertyPost.class);
                            property_list.add(propertyPost);

                            // Notify adapter for change
                            propertyRecyclerAdapter.notifyDataSetChanged();

                        }

                    }
                } catch (NullPointerException npe ){
                  // Do Something

                };
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
