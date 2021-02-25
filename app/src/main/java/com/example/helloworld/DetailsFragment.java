package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private TextView rollNoTextView;
    private EditText nameTextView;
    private EditText deptTextView;
    private EditText emailTextView;
    private Button editDetailsButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_details, container, false);

        Intent intent = getActivity().getIntent();
        int position = (intent.getExtras().getInt("position"));
        RegisteredStudentsDetails registeredStudentsDetails = RegisteredStudentsDetails.get(getActivity());
        List<Student> students = registeredStudentsDetails.getStudents();
        Student studentD = students.get(position);
        rollNoTextView = (TextView) view.findViewById(R.id.student_roll_no);
        nameTextView = (EditText) view.findViewById(R.id.student_name);
        deptTextView = (EditText) view.findViewById(R.id.student_dept);
        emailTextView = (EditText) view.findViewById(R.id.student_emailId);
        editDetailsButton = (Button) view.findViewById(R.id.button_edit_details);
        rollNoTextView.setText(studentD.getRollNo());
        nameTextView.setText(studentD.getName());
        deptTextView.setText(studentD.getDept());
        emailTextView.setText(studentD.getEmailId());

        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Student student = new Student(rollNoTextView.getText().toString(),
                            nameTextView.getText().toString(), deptTextView.getText().toString(), emailTextView.getText().toString());
//                RegisteredStudentsDetails registeredStudentsDetails = RegisteredStudentsDetails.get(getActivity());
//                students = registeredStudentsDetails.getStudents();
                students.set(position,student);
                getActivity().setResult(RESULT_OK, intent);
            }
        });
        return view;
    }
}