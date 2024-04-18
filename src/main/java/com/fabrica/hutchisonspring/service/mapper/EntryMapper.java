package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Entry;
import com.fabrica.hutchisonspring.service.dto.EntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EmployeeMapper.class })
public interface EntryMapper extends EntityMapper<EntryDTO, Entry> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.name", target = "employeeName")
    EntryDTO toDto(Entry entry);

    @Mapping(source = "employeeId", target = "employee")
    Entry toEntity(EntryDTO entryDTO);

    default Entry fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entry entry = new Entry();
        entry.setId(id);
        return entry;
    }
}
