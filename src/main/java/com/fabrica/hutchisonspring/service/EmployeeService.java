package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.Employee;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.EmployeeRepository;
import com.fabrica.hutchisonspring.service.dto.EmployeeDTO;
import com.fabrica.hutchisonspring.service.mapper.EmployeeMapper;
import com.fabrica.hutchisonspring.utils.FileManager;
import com.fabrica.hutchisonspring.utils.vm.ColumnMetaData;
import com.fabrica.hutchisonspring.utils.vm.ValidatorItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "name", Types.VARCHAR, 0, 0)
    };

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final BatchRepository<Employee> batchRepository;

    private final FileManager fileManager;

    public EmployeeService(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository, BatchRepository<Employee> batchRepository, FileManager fileManager) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<Employee> employees = ExcelUtils.sheetToEntities(Employee.class, file.getInputStream(), "EMPLOYEES", true, true);
            if (employees == null || employees.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(employees);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Employee>> items = (List<ValidatorItem<Employee>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Employee.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Employee.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> getEmployee(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }
}
