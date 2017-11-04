package com.example.abdulrahman.contact;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.cont);
       // ReadCount();
        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        //listView.setAdapter(arrayAdapter);
        CheckPermission();

    }
final int SQO=90;
    public void CheckPermission(){
        if (Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{
                        Manifest.permission.READ_CONTACTS
                },SQO);
            }
        }
        ReadCount();
    }

    ArrayList<contactm> arrayList=new ArrayList<>();
    public void ReadCount(){
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
    if (cursor.moveToFirst()){
        do{
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            arrayList.add(new contactm(name,number));
        }  while (cursor.moveToNext());
    }
    else {
        Toast.makeText(this, "is empty",Toast.LENGTH_LONG).show();
    }
        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        adp a=new adp(arrayList);
        listView.setAdapter(a);

    }
public void Lis(){
    ReadCount();

}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case SQO:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   ReadCount();
                }
                else {
                    Toast.makeText(this, "not connect permission ",Toast.LENGTH_LONG).show();
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

    public class adp extends BaseAdapter{
ArrayList<contactm> contactms;

        public adp(ArrayList<contactm> contactms) {
            this.contactms = contactms;
        }

        @Override
        public int getCount() {
            return contactms.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater=getLayoutInflater();
            View view1=inflater.inflate(R.layout.list_co,null);
            TextView textView=(TextView) view1.findViewById(R.id.textView4);
            TextView nam=(TextView) view1.findViewById(R.id.textView5);
            textView.setText(contactms.get(i).name);
            nam.setText(contactms.get(i).number);
            return view1;
        }
    }
}
