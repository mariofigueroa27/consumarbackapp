package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Employee;
import com.fabrica.hutchisonspring.service.dto.EmployeeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
