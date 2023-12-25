package com.example.mydatabsaemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

		EditText inputName, inputSalary;
		Spinner inputspinnerDept;
		Button buttonadd, buttonview;
		DatabaseManager mDatabase;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				mDatabase = new DatabaseManager(this);
				inputName = (EditText) findViewById(R.id.editText);
				inputSalary = (EditText) findViewById(R.id.editText2);
				inputspinnerDept = (Spinner) findViewById(R.id.spinnerDepartement);
				buttonadd = (Button) findViewById(R.id.button);
				buttonadd.setOnClickListener((v) -> {addEmployee();});
				buttonview = (Button) findViewById(R.id.button4);
				buttonview.setOnClickListener((v) -> {viewdata();});

		}

		private void addEmployee() {
				String name = inputName.getText().toString();
				String dept = inputspinnerDept.getSelectedItem().toString();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String joiningDate = sdf.format(cal.getTime());
				String salary = inputSalary.getText().toString();
				if (name.isEmpty()){
						inputName.setError("Name can't be empty");
						inputName.requestFocus();
						return;
				}
				if (salary.isEmpty()){
						inputSalary.setError("Salary can't be empty");
						inputSalary.requestFocus();
						return;
				}
				if (mDatabase.addEmployee(name, dept, joiningDate, Double.parseDouble(salary)))
						Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
				else

						Toast.makeText(this, "Could not add employee", Toast.LENGTH_SHORT).show();
		}

		public void viewdata() {
				Intent intent = new Intent(this, EmployeeActivity.class);
				startActivity(intent);
		}
}
