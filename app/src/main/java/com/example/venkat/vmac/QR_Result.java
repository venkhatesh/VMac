package com.example.venkat.vmac;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.glxn.qrgen.android.QRCode;

import java.util.HashMap;
import java.util.Map;

public class QR_Result extends AppCompatActivity {
    TextView pt_id,dr_id,med_id,price,pt_sus;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref,myqty;
    Task<Void> ref;
    String[] data;
    //ImageView qr_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__result);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myref = firebaseDatabase.getReference("Machines").child("machine1").child("nodemcu");
        Bundle bundle = getIntent().getExtras();
        FirebaseApp.initializeApp(this);
        String info = bundle.getString("Qr_re");
        pt_id = (TextView)findViewById(R.id.pt_id);
        dr_id = (TextView)findViewById(R.id.dr_id);
        med_id = (TextView)findViewById(R.id.med_id);
        price = (TextView)findViewById(R.id.price);
        pt_sus = (TextView)findViewById(R.id.pt_sus);
        data = info.split(",");
        pt_id.setText("Patient Id:-"+data[1]);
        dr_id.setText("Doctor Id:-"+data[0]);
        med_id.setText("Medicine Name:-"+data[2]);
        price.setText("Price:-"+56);
        Inventory();
        Log.w("Tag",info);
    }

    public void pay_clk(View view) {
        pt_sus.setText("Payment Successfull");
        myref = firebaseDatabase.getReference("Machines").child("machine1").child("nodemcu");
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("med1",data[2].toString());
        myref.updateChildren(hashMap);
        HashMap<String,Object> hm = new HashMap<>();
        hm.put(data[0].toString(),data[2].toString());
        ref = firebaseDatabase.getReference("Patients").child(data[1].toString()).updateChildren(hm);



    }

    void Inventory(){
        myref = firebaseDatabase.getReference("Machines").child("machine1").child("Inventory");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.w("Child", dataSnapshot.getValue().toString());
                    String value = dataSnapshot.getValue().toString();
                    Log.w("'Parent",value);

                value = value.substring(1, value.length()-1);           //remove curly brackets
                String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
                Map<String,String> map = new HashMap<>();

                for(String pair : keyValuePairs)                        //iterate over the pairs
                {
                    String[] entry = pair.split("=");                   //split the pairs to get key and value
                    map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                }

                Integer qty = Integer.valueOf(map.get("quantity"));
                if(qty<=1){
                    myqty = firebaseDatabase.getReference("Messages");
                    HashMap<String,Object> ms = new HashMap<>();
                    ms.put("Machine1",qty);
                    myqty.updateChildren(ms);

                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void clkbk(View view) {
        this.finish();
    }
}
