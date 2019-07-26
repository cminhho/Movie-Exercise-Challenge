package com.exercise.movie.employee;

import java.util.List;

public interface EmployeeService {
  Employee getEmployeeByName(String name);
  List<Employee> getAllEmployees();
}
