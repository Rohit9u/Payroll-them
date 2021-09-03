package Recycler;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.payrollthem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import model.nameNid;

public class payHistoryList extends ArrayAdapter<nameNid>{
    private Context context;
    private List<nameNid> nameNidList;

    public payHistoryList(Context context,List<nameNid> nameNidList) {
        super(context,0, nameNidList);
        this.context = context;
        this.nameNidList = nameNidList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.payment_his_list,null);
        TextView empname=v.findViewById(R.id.empnamehist);
        TextView empid=v.findViewById(R.id.empidhist);
        final CircleImageView imageView=v.findViewById(R.id.profilepicemphist);
        nameNid nd=nameNidList.get(position);
        empid.setText(nd.getEmployeeId());
        String x=nd.getEmployeeName();
        String[] namearray=x.split(" ");
        String y=namearray[0];
        empname.setText(y);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseAuth auth;
        FirebaseUser currentUser;
        String userid;
        String curruserid;
        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        curruserid=currentUser.getUid();
        userid=curruserid.substring(0,7);
        DocumentReference documentReference=db.collection("Users").document(userid).collection("Employee").document(nd.getEmployeeId());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Picasso.get().load(value.getString("image_url")).placeholder(R.drawable.dpc).fit().into(imageView);
            }
        });
        return v;
    }
}
