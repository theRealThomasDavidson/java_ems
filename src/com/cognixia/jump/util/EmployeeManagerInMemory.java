package com.cognixia.jump.util;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.exceptions.EmployeeNotFoundException;
import com.cognixia.jump.model.Employee;

public class EmployeeManagerInMemory implements EmployeeManager {
	
	private static int idCounter = 1;
	private static List<Employee> employeeList = new ArrayList<Employee>();
	
	static {
		employeeList.add(new Employee(idCounter++, "Tom", "HR", 50000, "tom@email.com"));
		employeeList.add(new Employee(idCounter++, "Mary", "HR", 50000, "mary@email.com"));
		employeeList.add(new Employee(idCounter++, "Anna", "IT", 50000, "anna@email.com"));
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeList;
	}

	@Override
	public Employee findEmployeeById(int id) throws EmployeeNotFoundException {
		
		for(Employee e : employeeList) {
			if(e.getId() == id) {
				return e;
			}
		}
		
		throw new EmployeeNotFoundException(id);
	}

	@Override
	public boolean createEmployee(Employee empl) {
		
		// reset id to be unique using the counter
		empl.setId(idCounter++);
		
		employeeList.add(empl);
		
		return false;
	}

	@Override
	public boolean deleteEmployee(int id) {
		/* this will delete an employee of a particular id and give a confirmation if the delete was made
		 int id: this is an int that will reference an employee id
		 return: this is a bool that will be true if the employee list contained an employee of the selected id and was deleted, 
		 if the employee was not found then this will be false 
		 */
		try {
			employeeList.remove(findEmployeeById(id));
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean updateEmployee(Employee empl) {
		// TODO Auto-generated method stub
		if(!employeeList.contains(empl)) {return false;}
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				System.out.println("\nPlease enter one of the following options :" 
									+ "\n1.) Show Current State"
									+ "\n2.) Change Id" 
									+ "\n3.) Change Name" 
									+ "\n4.) Change Dept"
									+ "\n5.) Change Salary"
									+ "\n6.) Change Email"
									+ "\n7.) Back to menu");
	
				int option = sc.nextInt();
				String soption;
				sc.nextLine(); // getting rid of new line character
	
				switch (option) {
				case 1:
					System.out.println(empl);
					break;
				case 2:
					System.out.println("Please enter the new employee ID number");
					option = sc.nextInt();
					empl.setId(option);
					break;
				case 3:
					System.out.println("Please enter the new employee name");
					soption = sc.nextLine();
					empl.setName(soption);
					break;
				case 4:
					System.out.println("Please enter the new employee department");
					soption = sc.nextLine();
					empl.setDepartment(soption);
					break;
				case 5:
					System.out.println("Please enter the new employee yearly Salary");
					option = sc.nextInt();
					empl.setSalary(option);
					break;
				case 6:
					System.out.println("Please enter the new employee email");
					soption = sc.nextLine();
					empl.setEmail(soption);
					break;
				case 7:
					break;
	
				default:
					System.out.println("\nPlease enter a number between 1 and 7");
					break;
				}
	
				if (option == 7) {
					break;
				}
	
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("\nPlease enter a number between 1 and 7");
			}
		}
		return true;
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String dept) {
		/*  this will give a list of all employees in a department
			String dept: this is a string that should reference the singular name of the department in the employees list 
			(if you don't know this name maybe print out an employee that you know is in that dept and reference them for the department name. 
			return: a list of all employees that are in the called department if no employees are found it will be an empty list of employees 
			because for some reason an empty list still needs a type of things it contians. 
		 */
		List<Employee> ret = employeeList.stream()
				.filter((e)-> e.getDepartment().equals(dept))
				.collect(Collectors.toList());
				
		return ret;
	}

}
