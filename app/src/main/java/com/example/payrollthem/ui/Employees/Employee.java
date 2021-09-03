package com.example.payrollthem.ui.Employees;

import Recycler.EmployeeListView;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.payrollthem.GMailSender;
import com.example.payrollthem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import model.ListEmployee;

public class Employee extends Fragment implements SearchView.OnQueryTextListener {
    private EditText Addresses;
    public EditText Age;
    private Spinner Department;
    private EditText Email;
    private EditText EmpName;
    private Spinner Gender;
    public ArrayList<String> IDDEpar;
    public EditText JoiningDate;
    private EditText Phone;
    public String Salary;
    private EditText accountNO;
    public ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter<String> arrayAdapter2;
    public ArrayAdapter<String> arrayAdapter3;
    private FirebaseAuth authfb;
    public String body;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builder2;
    String coroporate;
    private String curruserid;
    public int date;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<String> departmentName;
    public String departmentText;
    public String deptid;
    public AlertDialog dialog;
    public AlertDialog dialog2;
    public String emailid;
    public List<ListEmployee> employeeList;
    public EmployeeListView employeeListView;
    String employeeName;
    private String empn;
    private FirebaseUser firebuser;
    private ImageView floationg;
    public String[] gender = {"Male", "Female"};
    private Spinner jobTitle;
    public String jobTitles;
    public ArrayList<String> joblist;
    public ListView listView;
    public int month;
    public String mvpid;
    private TextView next;
    private String password;
    public ProgressBar pbar;
    private EditText pfrateset;
    public ProgressBar progress2;
    private int random;
    private String recipent;
    private Button savebtn;
    SearchView searchView;
    GMailSender sender;
    public String sex;
    public String subject;
    public String useremai;
    public String userid;
    public int year;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.from(getContext()).inflate(R.layout.employee_fragment, container, false);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        authfb = instance;
        FirebaseUser currentUser = instance.getCurrentUser();
        firebuser = currentUser;
        String uid = currentUser.getUid();
        curruserid = uid;
        userid = uid.substring(0, 7);
        random = new Random(System.currentTimeMillis()).nextInt(10000) + 10000;
        floationg = (ImageView) v.findViewById(R.id.addicon);
        listView = (ListView) v.findViewById(R.id.listview);
        searchView = (SearchView) v.findViewById(R.id.search);
        getActivity().getWindow().setSoftInputMode(2);
        db.collection("Users").document(this.userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Employee.this.coroporate = documentSnapshot.getString("Corporate name");
                }
            }
        });
        searchView.setOnQueryTextListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee.this.mvpid = ((ListEmployee) adapterView.getItemAtPosition(i)).getEmployeesId();
                Employee employee = Employee.this;
                employee.employeeinfofrag(employee.mvpid);
            }
        });
        employeeList = new ArrayList();
        floationg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createPopup();
            }
        });
        return v;
    }

    public void employeeinfofrag(String G) {
        employeeFragment empfag = new employeeFragment();
        Bundle argu = new Bundle();
        argu.putString("employeeno", G);
        empfag.setArguments(argu);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.employeelist, empfag);
        ft.commit();
    }

    public void createPopup() {
        builder = new AlertDialog.Builder(getView().getContext());
        View view = getLayoutInflater().inflate(R.layout.popupadd, (ViewGroup) null);
        departmentName = new ArrayList<>();
        IDDEpar = new ArrayList<>();
        next = view.findViewById(R.id.next);
        Age = view.findViewById(R.id.emp_age);
        EmpName =view.findViewById(R.id.emp_name);
        pfrateset = view.findViewById(R.id.pfund);
        JoiningDate =view.findViewById(R.id.joining_dates);
        pbar =view.findViewById(R.id.cardProgress);
        Email =view.findViewById(R.id.emailemp);
        Department = view.findViewById(R.id.emp_dept);
        Gender =view.findViewById(R.id.gender);
        Phone = view.findViewById(R.id.emp_ph);
        accountNO =  view.findViewById(R.id.accountNo);
        Addresses = view.findViewById(R.id.address);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, this.departmentName);
        arrayAdapter = arrayAdapter4;
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Department.setAdapter(this.arrayAdapter);
        retriveData();
        Department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = Employee.this;
                employee.departmentText = (String) employee.departmentName.get(i);
                Employee employee2 = Employee.this;
                String unused = employee2.deptid = (String) employee2.IDDEpar.get(i);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, this.gender);
        arrayAdapter2 = arrayAdapter5;
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(this.arrayAdapter2);
        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = Employee.this;
                sex = employee.gender[i];
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Age.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar mycalander = Calendar.getInstance();
                year=mycalander.get(Calendar.YEAR);
                month=mycalander.get(Calendar.MONTH);
                date=mycalander.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int xxdyear, int xxdmonth, int xxddate) {
                        Age.setText(xxddate + "-" + (xxdmonth + 1) + "-" + xxdyear);
                    }
                }, date, month, year).show();
            }
        });
        JoiningDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar mycalander = Calendar.getInstance();
                year=mycalander.get(Calendar.YEAR);
                month=mycalander.get(Calendar.MONTH);
                date=mycalander.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int xxdyear, int xxdmonth, int xxddate) {
                        JoiningDate.setText(xxddate + "-" + (xxdmonth + 1) + "-" + xxdyear);
                    }
                }, date, month,year).show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveEmpoyeeinfo();
            }
        });
        builder.setView(view);
        AlertDialog create = this.builder.create();
        dialog = create;
        create.show();
    }

    private void retriveData() {
        db.collection("Users").document(userid).collection("Department").whereEqualTo("userid",userid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                    while (it.hasNext()) {
                        QueryDocumentSnapshot listdept = it.next();
                        departmentName.add(listdept.get("departmentName").toString());
                        IDDEpar.add(listdept.get("departmentId").toString());
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
            }
        });
    }

    public void saveEmpoyeeinfo() {
        empn = EmpName.getText().toString().trim();
        String dob = Age.getText().toString().trim();
        emailid = Email.getText().toString().trim();
        String contactno = Phone.getText().toString().trim();
        String Address = Addresses.getText().toString().trim();
        String accountno = accountNO.getText().toString().trim();
        String pfundrate = pfrateset.getText().toString().trim();
        String jdate = JoiningDate.getText().toString().trim();
        int x = Integer.parseInt(pfundrate);
        if (TextUtils.isEmpty(this.empn) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(this.emailid) || TextUtils.isEmpty(this.departmentText) || TextUtils.isEmpty(this.sex) || TextUtils.isEmpty(contactno) || TextUtils.isEmpty(Address) || TextUtils.isEmpty(accountno) || TextUtils.isEmpty(jdate) || TextUtils.isEmpty(pfundrate) || x < 12 || x > 100) {
            Toast.makeText(getActivity(), "All Credentias are important", Toast.LENGTH_LONG).show();
            return;
        }
        pbar.setVisibility(View.VISIBLE);
        Map<String, Object> emObj = new HashMap<>();
        emObj.put("Employee Name", this.empn);
        emObj.put("Date of Birth", dob);
        emObj.put("Email id", this.emailid);
        emObj.put("Contact No", contactno);
        emObj.put("Gender", this.sex);
        emObj.put("Department", this.departmentText);
        emObj.put("Employee Id", String.valueOf(this.random));
        emObj.put("Address", Address);
        emObj.put("Account no", accountno);
        emObj.put("PF Rate", pfundrate);
        emObj.put("Joining Date", jdate);
        emObj.put("User id", this.userid);
        final DocumentReference documentReference = db.collection("Users").document(this.userid).collection("Employee").document(String.valueOf(this.random));
        documentReference.set(emObj).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                Map<String, Object> Obj = new HashMap<>();
                Obj.put("Loan", "0");
                Obj.put("Provident Fund", "0");
                Obj.put("Last Payment", "Not paid yet");
                documentReference.update(Obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void aVoid) {
                        pbar.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                        goToSecondPopup();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(Employee.this.getActivity(), "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goToSecondPopup() {
        builder2 = new AlertDialog.Builder(getView().getContext());
        View view2 = getLayoutInflater().inflate(R.layout.select_title, (ViewGroup) null);
        jobTitle = (Spinner) view2.findViewById(R.id.emp_jobtitle);
        joblist = new ArrayList<>();
        savebtn =  view2.findViewById(R.id.saveinfort);
        progress2 = view2.findViewById(R.id.progress2);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, this.joblist);
        arrayAdapter3 = arrayAdapter4;
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTitle.setAdapter(arrayAdapter3);
        retriveJobtitle();
        jobTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String unused = jobTitles = joblist.get(i);
                db.collection("Users").document(userid).collection("Department").document(deptid).collection("Job Title").whereEqualTo("user_id",userid).whereEqualTo("job_Title",jobTitles).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                            while (it.hasNext()) {
                                Salary = it.next().getString("job_Salary");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        Toast.makeText(Employee.this.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                savejobtitle();
            }
        });
        builder2.setView(view2);
        AlertDialog create =builder2.create();
        dialog2 = create;
        create.show();
    }

    public void savejobtitle() {
        useremai = "gardishfuture@gmail.com";
        password = "RohitFuture@2819";
        subject = "You are added by " + this.coroporate + " on \"Payroll Them\"";
        body = "Hey! " + empn + " You are Register as Employee of " + coroporate + " on \"Payroll Them App\". Kindly Create your Account as an Employee. Use " + userid + " as Unique Corporate Id  and your Employee Id is " + random + ". Have a Nice day";
        sender = new GMailSender(useremai, password);
        progress2.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(jobTitles)) {
            Map<String, Object> jobtitle = new HashMap<>();
            jobtitle.put("Job Title", jobTitles);
            jobtitle.put("Salary",Salary);
            db.collection("Users").document(userid).collection("Employee").document(String.valueOf(random)).update(jobtitle).addOnSuccessListener(new OnSuccessListener<Void>() {
                public void onSuccess(Void aVoid) {
                    new MyAsyncClass().execute();
                    Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show();
                    progress2.setVisibility(View.VISIBLE);
                    dialog2.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            db.collection("Users").document(userid).collection("Employee").
                    whereEqualTo("User id", userid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        employeeList.clear();
                        Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                        while (it.hasNext()) {
                            QueryDocumentSnapshot listemp = it.next();
                            ListEmployee lemployee = new ListEmployee();
                            lemployee.setName(listemp.getString("Employee Name"));
                            lemployee.setEmployeesId(listemp.getString("Employee Id"));
                            employeeList.add(lemployee);
                        }
                        employeeListView = new EmployeeListView(getActivity(),employeeList);
                        listView.setAdapter(employeeListView);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                }
            });
        }
    }

    private void retriveJobtitle() {
        db.collection("Users").document(userid).collection("Department").document(deptid).collection("Job Title")
                .whereEqualTo("user_id",userid).whereEqualTo("department_Name",departmentText).get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                    while (it.hasNext()) {
                        joblist.add(it.next().getString("job_Title"));
                    }
                        arrayAdapter3.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onStart() {
        super.onStart();
        db.collection("Users").document(userid).collection("Employee").whereEqualTo("User id",userid).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    employeeList.clear();
                    Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                    while (it.hasNext()) {
                        QueryDocumentSnapshot listemp = it.next();
                        ListEmployee lemployee = new ListEmployee();
                        lemployee.setName(listemp.getString("Employee Name"));
                        lemployee.setEmployeesId(listemp.getString("Employee Id"));
                        employeeList.add(lemployee);
                    }
                    employeeListView = new EmployeeListView(getActivity(),employeeList);
                    listView.setAdapter(employeeListView);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
            }
        });
    }

    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    public boolean onQueryTextChange(String s) {
        employeeListView.filter(s);
        return false;
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {
        MyAsyncClass() {
        }


        public void onPreExecute() {
            super.onPreExecute();
            pbar.setVisibility(View.VISIBLE);
        }

        public Void doInBackground(Void... mApi) {
            try {
                sender.sendMail(subject, body, useremai, emailid);
                return null;
            } catch (Exception ex) {
                Log.d("exceptionsending", ex.toString());
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(Employee.this.getActivity(), "mail send", Toast.LENGTH_LONG).show();
        }
    }
}
