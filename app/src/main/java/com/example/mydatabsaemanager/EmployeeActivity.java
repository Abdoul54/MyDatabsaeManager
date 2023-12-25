package com.example.mydatabsaemanager;

import android.database.Cursor;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity  extends AppCompatActivity {

		List<Employee> employeeList;
		ListView listView;
		DatabaseManager mDatabase;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_employee);
				mDatabase = new DatabaseManager(this);
				employeeList = new ArrayList();
				listView = (ListView) findViewById(R.id.mylistView);
				loadEmployeesFromDatabase();
		}

		private void loadEmployeesFromDatabase() {
				Cursor cursor = mDatabase.getAllEmployees();
				if (cursor.moveToFirst()) {
						do {
								employeeList.add(new Employee(cursor.getInt(0),
												cursor.getString(1),
												cursor.getString(2),
												cursor.getString(3),
												cursor.getDouble(4)
								));
						} while (cursor.moveToNext());
						EmployeeAdapter adapter = new EmployeeAdapter(this, R.layout.list_layout_employee, employeeList, mDatabase);
						listView.setAdapter(adapter);
				}
		}
}