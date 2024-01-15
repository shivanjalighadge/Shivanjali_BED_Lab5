package com.gl.springboot.EmployeeManagement.service;

import java.util.List;

import com.gl.springboot.EmployeeManagement.entity.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();

	void saveEmployee(Employee employee);

	Employee getEmployeeById(Long id);

	void updateEmployee(Employee employee);

	List<Employee> searchEmployees(String query);

	void deleteEmployee(Long id);
}
