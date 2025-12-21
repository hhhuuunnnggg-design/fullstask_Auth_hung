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

import com.example.demo.application.dto.request.CreatePermissionRequest;
import com.example.demo.application.dto.request.UpdatePermissionRequest;
import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.dto.response.ResultPaginationDTO;
import com.example.demo.application.usecase.permission.CreatePermissionUseCase;
import com.example.demo.application.usecase.permission.DeletePermissionUseCase;
import com.example.demo.application.usecase.permission.GetAllPermissionsUseCase;
import com.example.demo.application.usecase.permission.GetPermissionByIdUseCase;
import com.example.demo.application.usecase.permission.UpdatePermissionUseCase;
import com.example.demo.util.annotation.ApiMessage;
import com.example.demo.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;
    private final GetPermissionByIdUseCase getPermissionByIdUseCase;
    private final GetAllPermissionsUseCase getAllPermissionsUseCase;

    @PostMapping("/create")
    @ApiMessage("Create a permission")
    public ResponseEntity<ResPermissionDTO> create(@Valid @RequestBody CreatePermissionRequest request)
            throws IdInvalidException {
        ResPermissionDTO response = createPermissionUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ApiMessage("Update a permission")
    public ResponseEntity<ResPermissionDTO> update(@Valid @PathVariable Long id,
            @RequestBody UpdatePermissionRequest request) throws IdInvalidException {
        ResPermissionDTO response = updatePermissionUseCase.execute(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("delete a permission")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("id") long id) throws IdInvalidException {
        deletePermissionUseCase.execute(id);
        Map<String, String> data = new HashMap<>();
        data.put("message", "Đã xóa thành công");
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/{id}")
    @ApiMessage("Get permission by id")
    public ResponseEntity<ResPermissionDTO> getById(@PathVariable("id") Long id) throws IdInvalidException {
        ResPermissionDTO response = getPermissionByIdUseCase.execute(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/fetch-all")
    @ApiMessage("Fetch permissions")
    public ResponseEntity<ResultPaginationDTO> getPermissions(
            @Filter Specification<?> spec,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {
        ResultPaginationDTO response = getAllPermissionsUseCase.execute(spec, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
