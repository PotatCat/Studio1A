package com.example.studiomerge;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.util.Log;
import android.widget.Toast;

import javax.annotation.Nullable;
import java.util.List;
import java.util.ArrayList;

public class view_Bookings extends AppCompatActivity {

    private static final String TAG = "view_Booking";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__bookings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewBooking();

    }

    protected void viewBooking() {
        super.onStart();

        data = findViewById(R.id.viewData);

        db.collection("bookings")
                .whereEqualTo("donorEmail", "sumanyu@gmail.com")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<String> bookings = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("charityName") != null) {
                                bookings.add(doc.getString("charityName"));
                            }
                            if (doc.get("date") != null) {
                                bookings.add(doc.getString("date"));
                            }
                            if (doc.get("time") != null) {
                                bookings.add(doc.getString("time"));
                            }

                        }
                        Log.d(TAG,"Bookings" + bookings);
                    }

                });


    }
}
