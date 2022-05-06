package com.cognixia.jump;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.model.Employee;
import com.cognixia.jump.util.EmployeeManager;
import com.cognixia.jump.util.EmployeeManagerFile;
import com.cognixia.jump.util.EmployeeManagerInMemory;

// CRUD Operations - Create Read Update Delete

// Create an EMS that allows us to:
// 1. view all employees
// 2. view particular employee
// 3. create employees
// 4. delete employees
// 5. update employees
// 6. view all employees in a singular department (ex: all employees in HR)
// Expect: Console Based Menu


/*
 * Assignment:
 * - finish the EmployeeManagerInMemory (implement rest of methods)
 * - set up the create employee section of the menu
 * - send it over through slack (files, zip, or github)
 * */

public class Main {

	private static EmployeeManager manager;
	private static Scanner sc;
	

	public static void main(String[] args) {

		manager = new EmployeeManagerInMemory();
		//manager = new EmployeeManagerFile();
		
		sc = new Scanner(System.in);

		System.out.println("WELCOME TO THE EMPLOYEE MANAGEMENT SYSTEM (EMS)\n");

		mainMenu();

	}

	public static void mainMenu() {

		while (true) {

			try {
				System.out.println("\nPlease enter one of the following options :" 
									+ "\n1.) View Employees"
									+ "\n2.) Select Employee By Id" 
									+ "\n3.) Create Employee" 
									+ "\n4.) Update Employee"
									+ "\n5.) Delete Employee" 
									+ "\n6.) Exit");

				int option = sc.nextInt();
				sc.nextLine(); // getting rid of new line character

				switch (option) {
				case 1:
					viewEmployees();
					break;
				case 2:
					System.out.println("Please enter the employee ID number");
					option = sc.nextInt();
					try {
						System.out.println(manager.findEmployeeById(option));
					}
					catch(Exception e){
						System.out.println("Could not find employee.");
					}
					break;
				case 3:
					createEmployee();
					break;
				case 4:
					try {
						System.out.println("Enter employee id number.");
						option = sc.nextInt();
						manager.updateEmployee(manager.findEmployeeById(option));
					}
					catch(Exception e) {
						System.out.println("Employee not found");
					}
					break;
				case 5:
					System.out.println("Please enter the employee ID number");
					option = sc.nextInt();
					if(manager.deleteEmployee(option)) {
						System.out.println("Deleted employee: " + option);
					}
					else{
						System.out.println("Could not find employee: " + option);
					}
					break;
				case 6:
					break;

				default:
					System.out.println("\nPlease enter a number between 1 and 6");
					break;
				}

				if (option == 6) {
					break;
				}

			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("\nPlease enter a number between 1 and 6");
			}

		}

	}
	
	public static void viewEmployees() {
		
		
		while(true) {
			
			try {
			
				System.out.println("Select one of the follow:" + 
							"\n1. Select all employees" +
						    "\n2. Select employees by department" +
							"\n3. Exit to return to main menu");
				
				int option = sc.nextInt();
				sc.nextLine();
				
				switch (option) {
				case 1:
					viewAllEmployees();
					break;
				case 2:
					System.out.println("Please enter the department name");
					String soption = sc.nextLine();
					try {
						manager.getEmployeesByDepartment(soption).forEach(System.out::println);
					}
					catch(Exception e){
						System.out.println("Could not find employee.");
					}
					break;
				case 3:
					break;
				default:
					System.out.println("Enter number between 1 and 3");
					break;
				}
				
				if(option == 3) {
					break;
				}
				
				
			} catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Enter number between 1 and 3");
			}
		}
		
	}
	
	public static void viewAllEmployees() {
		List<Employee> employees = manager.getAllEmployees();
		
		
		if(employees.isEmpty()) {
			System.out.println("No employees currently in EMS");
		}
		else {
			for(Employee e : employees) {
				System.out.println(e);
			}
		}
	}
	
	public static void createEmployee() {
		String name;
		String dept;
		int salary;
		String email;
		while(true) {
			try {
				System.out.println("Enter Employee Name: ");
				name = sc.nextLine();
				System.out.println("Enter Employee Department: ");
				dept = sc.nextLine();
				System.out.println("Enter Employee Yearly Salary: ");
				salary = sc.nextInt();
				System.out.println("Enter Employee email: ");
				sc.nextLine();
				email = sc.nextLine();
				break;
			}
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Something went wrong let's start entering that employee again.");
			}
		}
		while(true) {
			try {
				System.out.println("Employee Details:"
						+ "\nName: "+ name
						+ "\nDepartment: " + dept 
						+ "\nSalary: $" + Integer.toString(salary)
						+ "\nEmail: " + email
						+ "\nPress below number to preform that action."
						+ "\n1. Add employee"
						+ "\n2. Discard changes and return to main menu");
		
				int option = sc.nextInt();
				sc.nextLine();
				
				switch (option) {
				case 1:
					manager.createEmployee(new Employee(0, name, dept, salary, email));
					break;
				case 2:
					System.out.println("discarding changes");
					break;
				}
				if(option == 2 || option == 1) {
					break;
				}
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Something went wrong let's start entering that employee again.");
			}
		}
	}
}


