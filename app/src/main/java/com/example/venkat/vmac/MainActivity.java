package com.example.venkat.vmac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    Button scanqr;
    private ZXingScannerView mscanner;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref,ref;
    DatabaseReference mach1,inv1,medi1,medi2,q1,q2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // myref = firebaseDatabase.getReference("Machines").child();

        scanqr = (Button)findViewById(R.id.scanqr);

        mscanner = new ZXingScannerView(this);

//
//


    }

    @Override
    public void onResume() {
        super.onResume();
        mscanner.setResultHandler(this); // Register ourselves as a handler for scan results.
        mscanner.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mscanner.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {

        Toast.makeText(this,result.getText(),Toast.LENGTH_LONG).show();
        Log.v("WOw", result.getText()); // Prints scan results
        String res = result.getText();
        Intent Qr_res = new Intent(this,QR_Result.class);
        Qr_res.putExtra("Qr_re",res);
        startActivity(Qr_res);
        mscanner.resumeCameraPreview(this);

//        DatabaseReference myref = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Machines").child("machine1").child("Inventory").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                inventory invent = dataSnapshot.getValue(inventory.class);
//                Log.w("String", String.valueOf(invent.getQuantity().toString()));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


    }

    public void qrclk(View view) {
        setContentView(mscanner);

    }


}
