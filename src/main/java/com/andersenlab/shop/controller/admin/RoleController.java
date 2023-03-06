package com.andersenlab.shop.controller.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.RoleDto;
import com.andersenlab.shop.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/role")
public class RoleController {

    private final IRoleService roleService;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody RoleDto roleDto) {
        roleService.create(roleDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<Void> edit(@RequestBody RoleDto roleDto) {
        roleService.edit(roleDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);
        roleService.delete(roleDto);
        return ResponseEntity.ok().build();
    }
}
