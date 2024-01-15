package com.gl.springboot.EmployeeManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gl.springboot.EmployeeManagement.entity.Employee;
import com.gl.springboot.EmployeeManagement.service.EmployeeService;

import java.util.List;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping({"/", "/employees"})
	public String listEmployees(@RequestParam(name = "query", defaultValue = "") String query, Model model) {
		try {
			List<Employee> employees;
			if (query != null && !query.isEmpty()) {
				employees = employeeService.getAllEmployees();
			} else {
				employees = employeeService.searchEmployees(query);
			}
			model.addAttribute("employees", employees);
			model.addAttribute("searchQuery", query);
			return "employee/list-employee";
		} catch (Exception e) {
			model.addAttribute("error", "Error fetching Employees: " + e.getLocalizedMessage());
			return "employee/error";
		}
	}


	@GetMapping("/employees/new")
	public String createEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "employee/create-employee";
	}

	@PostMapping("/employees")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employees";
	}

	@GetMapping("/employees/edit/{id}")
	public String editEmployeeForm(@PathVariable Long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "employee/edit-employee";
	}

	@PostMapping("/employees/{id}")
	public String updateEmployeeForm(@PathVariable Long id, @ModelAttribute("employee") Employee updatedEmployee, Model model) {
		try {
			// Retrieve the existing employee from the database
			Employee existingEmployee = employeeService.getEmployeeById(id);

			// Check if the employee with the given ID exists
			if (existingEmployee != null) {
				// Update the existing employee's details
				existingEmployee.setFirstName(updatedEmployee.getFirstName());
				existingEmployee.setLastName(updatedEmployee.getLastName());
				existingEmployee.setEmail(updatedEmployee.getEmail());

				// Save the updated employee
				employeeService.updateEmployee(existingEmployee);
			} else {
				// Handle the case where the employee with the given ID is not found
				model.addAttribute("error", "Employee with ID " + id + " not found.");
				return "employee/error";
			}

			return "redirect:/employees";
		} catch (Exception e) {
			model.addAttribute("error", "Error updating Employee: " + e.getLocalizedMessage());
			return "employee/error";
		}
	}


	@GetMapping("/employees/delete/{id}")
	public String deleteEmployee(@PathVariable Long id, Model model) {
		try {
			employeeService.deleteEmployee(id);
			return "redirect:/employees";
		} catch (Exception e) {
			model.addAttribute("error", "Error deleting Employee: " + e.getLocalizedMessage());
			return "employee/error";
		}
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		model.addAttribute("error", e.getMessage());
		return "employee/error";
	}

}
