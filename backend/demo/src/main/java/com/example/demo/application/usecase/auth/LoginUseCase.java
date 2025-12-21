package com.example.demo.application.usecase.auth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.application.dto.response.ResLoginDTO;
import com.example.demo.application.dto.response.ResPermissionDTO;
import com.example.demo.application.dto.response.ResRoleDTO;
import com.example.demo.application.usecase.user.GetUserByEmailUseCase;
import com.example.demo.domain.entity.Permission;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.PermissionRepository;
import com.example.demo.domain.repository.RoleRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public User getUserByEmail(String email) throws IdInvalidException {
        Optional<User> userOpt = getUserByEmailUseCase.execute(email);
        if (userOpt.isEmpty()) {
            throw new IdInvalidException("Email hoặc password không hợp lệ");
        }
        User user = userOpt.get();
        if (user.getIs_blocked() != null && user.getIs_blocked()) {
            throw new IdInvalidException("Tài khoản của bạn đã bị khóa");
        }
        return user;
    }

    public ResLoginDTO.UserLogin buildUserLogin(User user) {
        ResRoleDTO roleDTO = null;
        if (user.getRoleId() != null) {
            Optional<Role> roleOpt = roleRepository.findById(user.getRoleId());
            if (roleOpt.isPresent()) {
                Role role = roleOpt.get();
                List<ResPermissionDTO> permissions = null;
                if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
                    List<Permission> permissionList = permissionRepository.findByIdIn(role.getPermissionIds());
                    permissions = permissionList.stream()
                            .map(permission -> ResPermissionDTO.builder()
                                    .id(permission.getId())
                                    .name(permission.getName())
                                    .apiPath(permission.getApiPath())
                                    .method(permission.getMethod())
                                    .module(permission.getModule())
                                    .build())
                            .collect(Collectors.toList());
                }
                roleDTO = ResRoleDTO.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .description(role.getDescription())
                        .active(role.getActive())
                        .permissions(permissions)
                        .build();
            }
        }

        String fullName = user.getLastName() + " " + user.getFirstName();
        return new ResLoginDTO.UserLogin(
                user.getId(),
                user.getEmail(),
                fullName,
                user.getIs_admin(),
                user.getAvatar(),
                user.getCoverPhoto(),
                user.getIs_blocked(),
                roleDTO);
    }
}
