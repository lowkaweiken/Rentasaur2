package com.example.kenlow.rentasaur2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TenantFragment extends Fragment {

    private RecyclerView tenant_list_view;
    private List<TenantPost>  tenant_list;

    private FirebaseFirestore firebaseFirestore;
    private TenantRecyclerAdapter tenantRecyclerAdapter;

    public TenantFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tenant, container, false);

        tenant_list = new ArrayList<>();
        tenant_list_view = view.findViewById(R.id.tenant_list_view);

        tenantRecyclerAdapter = new TenantRecyclerAdapter(getContext(),tenant_list);

        tenant_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        tenant_list_view.setAdapter(tenantRecyclerAdapter);


        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Tenant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String tenantId = doc.getDocument().getId();

                        TenantPost tenantPost = doc.getDocument().toObject(TenantPost.class).withId(tenantId);
                        tenant_list.add(tenantPost);

                        tenantRecyclerAdapter.notifyDataSetChanged();





                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}