package Recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payrollthem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Templates;

import model.ListJobTitle;

public class JobTItleRecyclerView extends RecyclerView.Adapter<JobTItleRecyclerView.ViewHolder> {
    private Context context;
    private List<ListJobTitle> listJobTitles;

    public JobTItleRecyclerView(Context context, List<ListJobTitle> listJobTitles) {
        this.context = context;
        this.listJobTitles = listJobTitles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cardview_jobtitle,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull JobTItleRecyclerView.ViewHolder holder, int position) {
        ListJobTitle lJobTitle = this.listJobTitles.get(position);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().substring(0, 7);
        holder.departmentname.setText(lJobTitle.getDepartment_Name());
        holder.departmentid.setText(lJobTitle.getDepartment_Id());
        holder.jobtitle.setText("Job Title: " + lJobTitle.getJob_Title());
        holder.jobid.setText("Id: " + lJobTitle.getJob_Id());
        holder.salary.setText("Salary: " + lJobTitle.getJob_Salary());
        final FirebaseFirestore firebaseFirestore = db;
        final String str = userId;
        final ListJobTitle listJobTitle = lJobTitle;
        final int i = position;
        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JobTItleRecyclerView.this.context);
                builder.setMessage("Are you sure! Want to delete? ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(str).collection("Department").document(listJobTitle.getDepartment_Id()).collection("Job Title").document(listJobTitle.getJob_Id());
                        Map<String, Object> obj = new HashMap<>();
                        obj.put("department_Id", FieldValue.delete());
                        obj.put("department_Name", FieldValue.delete());
                        obj.put("job_Id", FieldValue.delete());
                        obj.put("job_Salary", FieldValue.delete());
                        obj.put("job_Title", FieldValue.delete());
                        obj.put("user_id", FieldValue.delete());
                        documentReference.update(obj);
                        JobTItleRecyclerView.this.listJobTitles.remove(i);
                        JobTItleRecyclerView.this.notifyItemChanged(i);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });

    }
    public void delete() {
    }
    @Override
    public int getItemCount() {
        return listJobTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView departmentname;
        private TextView departmentid;
        private TextView jobtitle;
        private TextView jobid;
        private TextView salary;
        private ImageView delete;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            departmentname=itemView.findViewById(R.id.deparname);
            departmentid=itemView.findViewById(R.id.departid);
            jobid=itemView.findViewById(R.id.titleidss);
            jobtitle=itemView.findViewById(R.id.jobtitless);
            delete = (ImageView) itemView.findViewById(R.id.deleteJob);
            salary=itemView.findViewById(R.id.salaryss);
            context=ctx;
        }
    }
}
