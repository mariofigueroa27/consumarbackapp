package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.service.PermissionService;
import com.fabrica.hutchisonspring.service.dto.PermissionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PermissionResource {

    private final PermissionService permissionService;

    public PermissionResource(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PutMapping("/users/{id}/permissions")
    public ResponseEntity<List<PermissionDTO>> updateMulti(@PathVariable Long id, @Valid @RequestBody List<PermissionDTO> permissionDTOS) {
        // TODO: Make id exists validation
        List<PermissionDTO> result = permissionService.saveMulti(id, permissionDTOS);
        return ResponseEntity.ok().body(result);
    }
}
