package com.example.studiomerge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class booking_Time extends AppCompatActivity {


    private EditText editTime, editDonorID, editDate, editCharity;
    private static final String TAG = "booking_Time";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__time);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        addbooking();


    }

        public void addbooking() {

        Button button;

                editDonorID = findViewById(R.id.editText);
                editDate = findViewById(R.id.editText3);
                editTime = findViewById(R.id.editText2);
                editCharity = findViewById(R.id.editText4);
                button = findViewById(R.id.button4);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Map<String, Object> data = new HashMap<>();

                        data.put("donorEmail", editDonorID.getText().toString());
                        data.put("charityName", editCharity.getText().toString());
                        data.put("date", editDate.getText().toString());
                        data.put("time", editTime.getText().toString());
                        data.put("state", "booked");


                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("bookings")
                                .add(data)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }

                                });

                      startActivity(new Intent(booking_Time.this,Profile.class));
                    }
                });


        }
        public void cancelbooking(String name) {

            Map<String, Object> data = new HashMap<>();

            data.put("state", "cancelled");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("bookings").document(name)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        }

    /** Use the next section for displaying bookings
     *  May need to use the viewbooking page to select a booking for the cancelbooking field
     *  send the selected booking's ID to the cancelbooking function as name
     */

        public void viewbooking() { //use this section for displaying bookings

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("bookings")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    System.out.println(document.getData());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        public void searchCharity(String name) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference charityRef = db.collection("users");

            Query query = charityRef.whereEqualTo("type", "charity")
                    .whereEqualTo("name", name)
                    .orderBy("date", Query.Direction.DESCENDING)
                    .orderBy("time", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            //display result here
                        }
                    }
                }
            });
        }
    }






