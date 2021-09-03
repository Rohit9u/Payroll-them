package Recycler;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payrollthem.R;
import com.example.payrollthem.ui.Department.job_title;

import java.util.List;

import model.ListDepartment;

public class DepartmentRecyclerView extends RecyclerView.Adapter<DepartmentRecyclerView.ViewHolder> {
    private Context context;
    private List<ListDepartment> listDepartments;
    public DepartmentRecyclerView(Context context, List<ListDepartment> listDepartments) {
        this.context = context;
        this.listDepartments = listDepartments;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.department_list,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentRecyclerView.ViewHolder holder, int position) {
    final ListDepartment ldepart=listDepartments.get(position);
    holder.DepartName.setText("Department Name: "+ldepart.getDepartmentName());
    holder.DepartID.setText("Department Id: "+ldepart.getDepartmentId());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeFragment(ldepart.getDepartmentId(),ldepart.getDepartmentName());
        }
    });

    }

    private void changeFragment(String departmentId,String departmentName) {
        job_title jobttl=new job_title();
        Bundle args=new Bundle();
        args.putString("Department id",departmentId);
        args.putString("Department Name",departmentName);
        jobttl.setArguments(args);
        FragmentManager fragmentManager =((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.departmentfragment,jobttl);
        fragmentTransaction.commit();

    }


    @Override
    public int getItemCount() {
        return listDepartments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView DepartName, DepartID;
        public ImageButton delete;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            DepartName=itemView.findViewById(R.id.namedept);
            DepartID=itemView.findViewById(R.id.idDept);
            linearLayout=itemView.findViewById(R.id.departmentllyout);
            context=ctx;

//            delete=itemView.findViewById(R.id.deleteButton);
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int Position=getAdapterPosition();
//                    ListDepartment listd=listDepartments.get(Position);
//                    //deleteItem(listd.get);
//                }
//            });
        }

//        private void deleteItem(int id) {
//        }
    }
}
