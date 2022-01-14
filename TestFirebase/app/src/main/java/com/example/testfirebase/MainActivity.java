package com.example.testfirebase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    DatabaseReference algemeen,woorden;
    private RecyclerView recyclerView;
    ItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Reference naar woorden database
        woorden = FirebaseDatabase.getInstance("https://testfirebase-ae505-default-rtdb.europe-west1.firebasedatabase.app/").getReference("woorden");
        //Klaarzetten recyclerview
        woorden.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String msg = singleSnapshot.getValue().toString();
                    Log.d("tester", msg);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Donny", "Failed to read value.", error.toException());
            }
        });

        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // bouwen van de dataquery
        FirebaseRecyclerOptions<Woord> options
                = new FirebaseRecyclerOptions.Builder<Woord>()
                .setQuery(woorden, Woord.class)
                .build();
        // bouwen van de adapter met query
        adapter = new ItemAdapter(options);
        // koppelen van de adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}