package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link McStudentsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class McStudentsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int requestCode = -1;

    private RecyclerView studentListRecylerView;
    private StudentListAdapter studentListAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public McStudentsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment McStudentsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static McStudentsListFragment newInstance(String param1, String param2) {
        McStudentsListFragment fragment = new McStudentsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mc_students_list, container, false);

        studentListRecylerView = (RecyclerView) view
                .findViewById(R.id.student_recycler_view);
        studentListRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
       
    }
    private void updateUI() {
        RegisteredStudentsDetails registeredStudentsDetails = RegisteredStudentsDetails.get(getActivity());
        List<Student> students = registeredStudentsDetails.getStudents();

        studentListAdapter = new StudentListAdapter(students);
        studentListRecylerView.setAdapter(studentListAdapter);
    }

    private class StudentHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Student student;

        private TextView rollNoTextView;
        private TextView nameTextView;
        private TextView deptTextView;
        private TextView emailTextView;

        public StudentHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_student, parent, false));
            itemView.setOnClickListener(this);

            rollNoTextView = (TextView) itemView.findViewById(R.id.student_roll_no);
            nameTextView = (TextView) itemView.findViewById(R.id.student_name);
            deptTextView = (TextView) itemView.findViewById(R.id.student_dept);
            emailTextView = (TextView) itemView.findViewById(R.id.student_emailId);
        }

        public void bind(Student student) {
            this.student = student;
            rollNoTextView.setText(this.student.getRollNo());
            nameTextView.setText(this.student.getName());
            deptTextView.setText(this.student.getDept());
            emailTextView.setText(this.student.getEmailId());
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(),
//                    this.student.getName() + " clicked!", Toast.LENGTH_SHORT)
//                    .show();

            String rollNo = this.student.getRollNo();
            System.out.println("Vishesh" + rollNo);
            Intent intent = new Intent(getActivity(), DetailsActivity.class);

            intent.putExtra("position" , getAdapterPosition());
            startActivityForResult(intent, requestCode);
        }
    }

    private class StudentListAdapter extends RecyclerView.Adapter<StudentHolder> {

        private List<Student> students;

        public StudentListAdapter(List<Student> students) {
            this.students = students;
        }

        @Override
        public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StudentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(StudentHolder holder, int position) {
            Student student = students.get(position);
            holder.bind(student);
        }

        @Override
        public int getItemCount() {
            return students.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        studentListAdapter.notifyDataSetChanged();
        if (requestCode == -1) {
            if(resultCode == RESULT_OK) {
                studentListAdapter.notifyDataSetChanged();
            }
        }
    }



}