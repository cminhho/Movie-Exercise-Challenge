package com.exercise.movie.employee;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public Employee getEmployeeByName(String name) {
    return employeeRepository.findByName(name);
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }
}
