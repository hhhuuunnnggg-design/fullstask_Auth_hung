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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.application.dto.request.CreateUserRequest;
import com.example.demo.application.dto.request.UpdateUserRequest;
import com.example.demo.application.dto.response.ResCreateUserDTO;
import com.example.demo.application.dto.response.ResUpdateUserDTO;
import com.example.demo.application.dto.response.ResUserDTO;
import com.example.demo.application.dto.response.ResultPaginationDTO;
import com.example.demo.application.usecase.user.ChangeActivityUserUseCase;
import com.example.demo.application.usecase.user.CreateUserUseCase;
import com.example.demo.application.usecase.user.DeleteUserUseCase;
import com.example.demo.application.usecase.user.GetAllUsersUseCase;
import com.example.demo.application.usecase.user.GetUserByIdUseCase;
import com.example.demo.application.usecase.user.UpdateUserAvatarUseCase;
import com.example.demo.application.usecase.user.UpdateUserCoverPhotoUseCase;
import com.example.demo.application.usecase.user.UpdateUserUseCase;
import com.example.demo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangeActivityUserUseCase changeActivityUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final UpdateUserAvatarUseCase updateUserAvatarUseCase;
    private final UpdateUserCoverPhotoUseCase updateUserCoverPhotoUseCase;

    @PostMapping("/add-user")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody CreateUserRequest request)
            throws Exception {
        ResCreateUserDTO response = createUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @ApiMessage("fetch user by id")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") Long id) throws Exception {
        ResUserDTO response = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/changeActivity/{id}")
    @ApiMessage("ChangeActivity a user")
    public ResponseEntity<Map<String, String>> changeActivityUser(@PathVariable Long id) throws Exception {
        var updatedUser = changeActivityUserUseCase.execute(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã thay đổi trạng thái " + updatedUser.getEmail() + " thành "
                + (updatedUser.isBlocked() ? "khóa" : "mở khóa"));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ApiMessage("Update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) throws Exception {
        ResUpdateUserDTO response = updateUserUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ApiMessage(value = "delete a user")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable long id) throws Exception {
        deleteUserUseCase.execute(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã xóa thành công");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/avatar")
    @ApiMessage(value = "upload image avatar")
    public ResponseEntity<Map<String, String>> updateAvatar(
            @RequestParam("userId") Long userId,
            @RequestParam("avatar") MultipartFile avatarFile) throws Exception {
        String originalFilename = avatarFile.getOriginalFilename();
        String avatarUrl = updateUserAvatarUseCase.execute(userId, originalFilename, avatarFile.getInputStream());
        Map<String, String> data = new HashMap<>();
        data.put("avatar", avatarUrl);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/coverPhoto")
    @ApiMessage(value = "upload coverPhoto")
    public ResponseEntity<Map<String, String>> uploadCoverPhoto(
            @RequestParam("userId") Long userId,
            @RequestParam("coverPhoto") MultipartFile coverPhotoFile) throws Exception {
        String originalFilename = coverPhotoFile.getOriginalFilename();
        String coverPhotoUrl = updateUserCoverPhotoUseCase.execute(userId, originalFilename,
                coverPhotoFile.getInputStream());
        Map<String, String> data = new HashMap<>();
        data.put("coverPhoto", coverPhotoUrl);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/fetch-all")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<?> spec,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {
        ResultPaginationDTO response = getAllUsersUseCase.execute(spec, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
