package com.victorcharl.weatherforecastapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class FirebaseOperations {

    static DatabaseReference weather() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("list");
        myRef.keepSynced(true);

        return myRef;
    }

}
