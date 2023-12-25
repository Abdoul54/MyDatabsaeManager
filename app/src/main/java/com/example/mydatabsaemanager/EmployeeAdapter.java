package com.example.mydatabsaemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {

		Context mCtx;
		int layoutRes;
		List<Employee> employeeList;
		DatabaseManager mDatabase;

		public EmployeeAdapter(Context mCtx, int layoutRes, List<Employee> employeeList, DatabaseManager mDatabase) {
				super(mCtx, layoutRes, employeeList);
				this.mCtx = mCtx;
				this.layoutRes = layoutRes;
				this.employeeList = employeeList;
				this.mDatabase = mDatabase;
		}

		@Override
		public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
				LayoutInflater inflater = LayoutInflater.from(mCtx);
				View view = inflater.inflate(layoutRes, null);
				TextView textViewName = view.findViewById(R.id.textViewName);
				TextView textViewDept = view.findViewById(R.id.textViewDepartement);
				TextView textViewSalary = view.findViewById(R.id.textViewSalary);
				TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);
				final Employee employee = employeeList.get(position);

				textViewName.setText(employee.getName());
				textViewDept.setText(employee.getDept());
				textViewSalary.setText(String.valueOf(employee.getSalary()));
				textViewJoiningDate.setText(employee.getJoiningDate());

				view.findViewById(R.id.buttonDelete).setOnClickListener((ButtonView) -> {
						deleteEmployee(employee);
				});
				view.findViewById(R.id.buttonEdit).setOnClickListener((ButtonView) -> {
						updateAnEmployee(employee);
				});
				return view;
		}
		private void updateAnEmployee(Employee employee) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
				LayoutInflater inflater = LayoutInflater.from(mCtx);
				View view = inflater.inflate(R.layout.dialog_update_employee, null);
				builder.setView(view);

				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				final EditText editTextName = view.findViewById(R.id.editTextName);
				final EditText  editTextSalary = view.findViewById(R.id.editTextName);
				final Spinner  spinnerDept = view.findViewById(R.id.spinnerDepartment);

				editTextName.setText(employee.getName());
				editTextSalary.setText(String.valueOf(employee.getSalary()));

				view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener((ButtonView) -> {
						String name = editTextName.getText().toString();
						String dept = spinnerDept.getSelectedItem().toString();
						String salary = editTextSalary.getText().toString();
						Toast.makeText(mCtx, "name: " + name + "\ndept: " + dept + "\nsalary: " + salary, Toast.LENGTH_SHORT).show();

						if (name.isEmpty()){
								editTextName.setError("Name can't be empty");
								editTextName.requestFocus();
								return;
						}
						if (salary.isEmpty()){
								editTextSalary.setError("Salary can't be empty");
								editTextSalary.requestFocus();
								return;
						}
						if (mDatabase.updateEmployee(employee.getId(), name, dept, Double.valueOf(salary))){
								Toast.makeText(mCtx, "Employee Updated", Toast.LENGTH_SHORT).show();
								loadEmployeesFromDatabaseAgain();
						}
						alertDialog.dismiss();

				});
		}

		private void deleteEmployee(final Employee employee) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
				builder.setTitle("Are you sure?");

				builder.setPositiveButton("Yes", (dialogInterface, i) -> {
						if (mDatabase.deleteEmployee(employee.getId())){
								Toast.makeText(mCtx, "Employee Deleted", Toast.LENGTH_SHORT).show();
								loadEmployeesFromDatabaseAgain();
						}
				}
				);
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}

				});
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
		}


		private void loadEmployeesFromDatabaseAgain() {
				Cursor cursor = mDatabase.getAllEmployees();

				employeeList.clear();

				if (cursor.moveToFirst()){
						do {
								employeeList.add(new Employee(
												cursor.getInt(0),
												cursor.getString(1),
												cursor.getString(2),
												cursor.getString(3),
												cursor.getDouble(4)
								));
						} while (cursor.moveToNext());
				}
				notifyDataSetChanged();
		}

}
