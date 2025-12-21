package com.example.demo.infrastructure.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Enum.genderEnum;
import com.example.demo.infrastructure.persistence.entity.PermissionEntity;
import com.example.demo.infrastructure.persistence.entity.RoleEntity;
import com.example.demo.infrastructure.persistence.entity.UserEntity;
import com.example.demo.infrastructure.persistence.repository.PermissionJpaRepository;
import com.example.demo.infrastructure.persistence.repository.RoleJpaRepository;
import com.example.demo.infrastructure.persistence.repository.UserJpaRepository;

@Service
public class DatabaseInitializer implements CommandLineRunner {

    private final PermissionJpaRepository permissionRepository;
    private final RoleJpaRepository roleRepository;
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(
            PermissionJpaRepository permissionRepository,
            RoleJpaRepository roleRepository,
            UserJpaRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private PermissionEntity createPermission(String name, String apiPath, String method, String module) {
        PermissionEntity permission = new PermissionEntity();
        permission.setName(name);
        permission.setApiPath(apiPath);
        permission.setMethod(method);
        permission.setModule(module);
        return permission;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countPermissions = this.permissionRepository.count();
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();

        if (countPermissions == 0) {
            ArrayList<PermissionEntity> arr = new ArrayList<>();

            // Users permissions
            arr.add(createPermission("Xem tất cả danh sách người dùng", "/api/v1/users/fetch-all", "GET", "USERS"));
            arr.add(createPermission("Tạo người dùng", "/api/v1/users/add-user", "POST", "USERS"));
            arr.add(createPermission("Xóa người dùng", "/api/v1/users/{id}", "DELETE", "USERS"));
            arr.add(createPermission("Cập nhật người dùng", "/api/v1/users/{id}", "PUT", "USERS"));
            arr.add(createPermission("thay đổi trạng thái người dùng", "/api/v1/users/changeActivity/{id}", "PUT",
                    "USERS"));

            // Permissions
            arr.add(createPermission("Tạo quyền mới", "/api/v1/permissions", "POST", "PERMISSIONS"));
            arr.add(createPermission("Cập nhật quyền", "/api/v1/permissions/{id}", "PUT", "PERMISSIONS"));
            arr.add(createPermission("Xóa quyền", "/api/v1/permissions/{id}", "DELETE", "PERMISSIONS"));
            arr.add(createPermission("Xem quyền", "/api/v1/permissions/{id}", "GET", "PERMISSIONS"));
            arr.add(createPermission("Xem danh sách quyền", "/api/v1/permissions/fetch-all", "GET", "PERMISSIONS"));

            // Roles
            arr.add(createPermission("Tạo vai trò mới", "/api/v1/roles/create", "POST", "ROLES"));
            arr.add(createPermission("Cập nhật vai trò", "/api/v1/roles/{id}", "PUT", "ROLES"));
            arr.add(createPermission("Xóa vai trò", "/api/v1/roles/{id}", "DELETE", "ROLES"));
            arr.add(createPermission("Xem vai trò", "/api/v1/roles/{id}", "GET", "ROLES"));
            arr.add(createPermission("Xem danh sách vai trò", "/api/v1/roles/fetch-all", "GET", "ROLES"));

            this.permissionRepository.saveAll(arr);
        }

        if (countRoles == 0) {
            List<PermissionEntity> allPermissions = this.permissionRepository.findAll();

            RoleEntity adminRole = new RoleEntity();
            adminRole.setName("SUPER_ADMIN");
            adminRole.setDescription("Admin thì full permissions");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);

            this.roleRepository.save(adminRole);
        }

        if (countUsers == 0) {
            UserEntity adminUser = new UserEntity();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAvatar("https://wellavn.com/wp-content/uploads/2025/07/anh-gai-xinh-2k-12.jpg");
            adminUser.setFirstName("Super");
            adminUser.setLastName("Admin");
            adminUser.setGender(genderEnum.MALE);
            adminUser.setPassword(this.passwordEncoder.encode("123456"));
            adminUser.setIs_admin(true);

            // Tìm role SUPER_ADMIN
            List<RoleEntity> roles = this.roleRepository.findAll();
            RoleEntity adminRole = roles.stream()
                    .filter(role -> "SUPER_ADMIN".equals(role.getName()))
                    .findFirst()
                    .orElse(null);
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }

            this.userRepository.save(adminUser);
        }

        if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");
    }

}

