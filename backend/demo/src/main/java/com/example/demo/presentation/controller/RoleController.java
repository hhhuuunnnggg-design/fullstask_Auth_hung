package com.example.demo.presentation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.dto.request.CreateRoleRequest;
import com.example.demo.application.dto.request.UpdateRoleRequest;
import com.example.demo.application.dto.response.ResRoleDTO;
import com.example.demo.application.dto.response.ResultPaginationDTO;
import com.example.demo.application.usecase.role.CreateRoleUseCase;
import com.example.demo.application.usecase.role.DeleteRoleUseCase;
import com.example.demo.application.usecase.role.GetAllRolesUseCase;
import com.example.demo.application.usecase.role.GetRoleByIdUseCase;
import com.example.demo.application.usecase.role.UpdateRoleUseCase;
import com.example.demo.util.annotation.ApiMessage;
import com.example.demo.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final GetRoleByIdUseCase getRoleByIdUseCase;
    private final GetAllRolesUseCase getAllRolesUseCase;

    @PostMapping("/create")
    @ApiMessage("Create a role")
    public ResponseEntity<ResRoleDTO> create(@Valid @RequestBody CreateRoleRequest request) throws IdInvalidException {
        ResRoleDTO response = createRoleUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ApiMessage("Update a role")
    public ResponseEntity<ResRoleDTO> update(@Valid @PathVariable Long id, @RequestBody UpdateRoleRequest request)
            throws IdInvalidException {
        ResRoleDTO response = updateRoleUseCase.execute(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a role")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("id") long id) throws IdInvalidException {
        deleteRoleUseCase.execute(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã xóa thành công");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @ApiMessage("Get role by id")
    public ResponseEntity<ResRoleDTO> getById(@PathVariable("id") Long id) throws IdInvalidException {
        ResRoleDTO response = getRoleByIdUseCase.execute(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/fetch-all")
    @ApiMessage("fetch all roles")
    public ResponseEntity<ResultPaginationDTO> getAllRoles(
            @Filter Specification<?> spec,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {
        ResultPaginationDTO response = getAllRolesUseCase.execute(spec, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
