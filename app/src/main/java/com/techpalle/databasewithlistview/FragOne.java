package com.techpalle.databasewithlistview;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragOne extends Fragment {
    EditText ed1,ed2;
    Button b1;
    ListView lv;
    MyDatabase myDatabase;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;


    public FragOne() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //open database here
        myDatabase=new MyDatabase(getActivity());
        myDatabase.open();//after super

    }

    @Override
    public void onDestroy() {
        myDatabase.close();//before super
        super.onDestroy();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_frag_one, container, false);
        ed1= (EditText) v.findViewById(R.id.edittext1);
        ed2= (EditText) v.findViewById(R.id.edittext2);
        b1= (Button) v.findViewById(R.id.button1);
        lv= (ListView) v.findViewById(R.id.listview1);
        //code for displaying database table information on listview
        //a-get cursor from table
        cursor=myDatabase.queryStudent();
        //b-establish link betn cursor and cursor adapter
        simpleCursorAdapter=new SimpleCursorAdapter(getActivity(),R.layout.row,cursor,new String[]{"_id","sname","ssub"},
                new int[]{R.id.textView,R.id.textView2,R.id.textView3});
        //c-establish link between cursoradapter to listview
        lv.setAdapter(simpleCursorAdapter);
        //handling list view item clicks
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //request cursor to move the position
                cursor.moveToPosition(position);
                int sno=cursor.getInt(0);
                String name=cursor.getString(1);
                String sub=cursor.getString(2);
                Toast.makeText(getActivity(), ""+sno+"-"+name+"-"+sub, Toast.LENGTH_SHORT).show();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code for inserting in to table
                String name=ed1.getText().toString();
                String sub=ed2.getText().toString();
                myDatabase.insertStudent(name,sub);
                cursor.requery();
                Toast.makeText(getActivity(), "inserted row", Toast.LENGTH_SHORT).show();
                ed1.setText("");
                ed2.setText("");
                ed1.requestFocus();

            }
        });

        return v;
    }

}
