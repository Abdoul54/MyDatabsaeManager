package com.example.mydatabsaemanager;

public class Employee {

		private int id;
		private String name;
		private String department;
		private String joiningDate;
		private double salary;

		public Employee(int id, String name, String department, String joiningDate, double salary) {
				this.id = id;
				this.name = name;
				this.department = department;
				this.joiningDate = joiningDate;
				this.salary = salary;
		}

		public int getId() {
				return id;
		}

		public String getName() {
				return name;
		}

		public String getDept() {
				return department;
		}

		public String getJoiningDate() {
				return joiningDate;
		}

		public double getSalary() {
				return salary;
		}
}
