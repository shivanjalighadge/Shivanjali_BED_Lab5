package com.gl.springboot.EmployeeManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.springboot.EmployeeManagement.entity.Employee;
import com.gl.springboot.EmployeeManagement.repo.EmployeeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(Long id) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		return employeeOptional.orElse(null);
	}

	@Override
	public void updateEmployee(Employee employee) {
		if (employee.getId() != null && employeeRepository.existsById(employee.getId())) {
			employeeRepository.save(employee);
		} else {
			throw new EntityNotFoundException("Employee with id " + employee.getId() + " not found");
		}
	}


	public List<Employee> searchEmployees(String query) {
		// You can customize this method based on your specific search criteria
		return employeeRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(query, query, query);
	}

	@Override
	public void deleteEmployee(Long id) {
		// Your custom logic for deleting an employee, if needed
		// For example, you might want to perform additional checks or actions before deleting

		// Delete the employee by ID
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("Employee with id " + id + " not found");
		}
	}
}
