package com.dhruv.studentdatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhruv.studentdatabase.database.DatabaseHandler;
import com.dhruv.studentdatabase.database.Student;
import com.dhruv.studentdatabase.utility.InputFilterMinMax;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText marks;
    EditText id;
    Button add;
    Button delete;
    Button update;
    Button view;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        marks = findViewById(R.id.marks);
        id = findViewById(R.id.id);

        add = findViewById(R.id.add);
        view = findViewById(R.id.view);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        marks.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});

        db = new DatabaseHandler(this);

    }


    public void viewData(View view) {
        List<Student> studentList = db.allStudents();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (!studentList.isEmpty()) {
            String[] students = new String[studentList.size()];

            for (int i = 0; i < studentList.size(); i++) {
                students[i] = studentList.get(i).toString();
            }
            builder.setTitle("Student Database");
            builder.setItems(students, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });


        } else {
            builder.setTitle("ERROR");
            builder.setMessage("No Data found !!!");
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteData(View view) {
        if (TextUtils.isEmpty(id.getText().toString())) {
            id.setError("id can't be empty");
        } else {
            int ID = Integer.parseInt(id.getText().toString());
            int deletedRow = db.deleteOne(ID);
            if (deletedRow == 0)
                Toast.makeText(this, "Student Data for ID : " + ID + " is not available", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Student Data with ID : " + ID + "  is deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void addData(View view) {
        if (TextUtils.isEmpty(firstName.getText().toString())) {
            firstName.setError("First Name can't be empty");
        } else if (TextUtils.isEmpty(lastName.getText().toString())) {
            lastName.setError("Last Name can't be empty");
        } else if (TextUtils.isEmpty(marks.getText().toString())) {
            marks.setError("Marks can't be empty");
        } else {
            Student student = new Student(firstName.getText().toString(), lastName.getText().toString(), Integer.parseInt(marks.getText().toString()));
            db.addStudent(student);
            Toast.makeText(this, "Data of Student with Name : " + student.getFirstName() + " " + student.getLastName() + "  is added", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(View view) {
        if (TextUtils.isEmpty(id.getText().toString())) {
            id.setError("id can't be empty");
        } else {
            int ID = Integer.parseInt(id.getText().toString());
            if (db.getStudent(ID) != null) {
                Student student = db.getStudent(ID);

                if (!TextUtils.isEmpty(firstName.getText().toString())) {
                    student.setFirstName(firstName.getText().toString());
                }
                if (!TextUtils.isEmpty(lastName.getText().toString())) {
                    student.setLastName(lastName.getText().toString());
                }
                if (!TextUtils.isEmpty(marks.getText().toString())) {
                    student.setMarks(Integer.parseInt(marks.getText().toString()));
                }
                db.updateStudent(student);
                Toast.makeText(this, "Student Data with ID : " + student.getID() + "  is updated", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Student Data for ID : " + ID + " is not available", Toast.LENGTH_SHORT).show();
        }
    }
}
