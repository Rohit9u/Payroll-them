package Recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import model.ListEmployee;
import com.example.payrollthem.R;
import com.example.payrollthem.ui.Employees.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
//import com.example.payrollthem.ui.Employees.jbjbjb;


public class EmployeeListView  extends ArrayAdapter<ListEmployee> {
    ArrayList<ListEmployee> arrayEmployee;
    AlertDialog.Builder builder;
    Context context;
    AlertDialog dialog;
    String image;
    List<ListEmployee> listEmployees;
    public EmployeeListView(Context context,List<ListEmployee> listE){
        super(context,0,listE);
        this.context=context;
        this.listEmployees=listE;
        ArrayList<ListEmployee> arrayList = new ArrayList<>();
        this.arrayEmployee = arrayList;
        arrayList.addAll(this.listEmployees);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.employee_list,null);
        TextView name=view.findViewById(R.id.empnamelist);
        TextView id=view.findViewById(R.id.empid);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseAuth auth;
        FirebaseUser currentUser;
        String currentUserid;
        String userid;
        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        currentUserid=currentUser.getUid();
        userid=currentUserid.substring(0,7);
        CircleImageView imageView=view.findViewById(R.id.profilepicemp);
        //RelativeLayout relativeLayout=view.findViewById(R.id.relativelistlayout);
        final ImageView dp=view.findViewById(R.id.profilepicemp);
        ListEmployee lemp=listEmployees.get(position);
        String x=lemp.getName();
        String[] y=x.split(" ");
        String namez=y[0];
        name.setText(namez);
        id.setText(lemp.getEmployeesId());
        DocumentReference documentReference=db.collection("Users").document(userid).collection("Employee").document(lemp.getEmployeesId());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Picasso.get().load(value.getString("image_url")).placeholder(R.drawable.dpc).fit().into(dp);
            }
        });

        return view;
    }
    public void filter(String chartext) {
        String chartext2 = chartext.toLowerCase(Locale.getDefault());
        this.listEmployees.clear();
        if (chartext2.length() == 0) {
            this.listEmployees.addAll(this.arrayEmployee);
        } else {
            Iterator<ListEmployee> it = this.arrayEmployee.iterator();
            while (it.hasNext()) {
                ListEmployee emp = it.next();
                if (emp.getName().toLowerCase(Locale.getDefault()).contains(chartext2) || emp.getEmployeesId().contains(chartext2)) {
                    this.listEmployees.add(emp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
