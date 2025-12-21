package com.example.demo.application.usecase.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.application.dto.response.ResUserDTO;
import com.example.demo.application.dto.response.ResultPaginationDTO;
import com.example.demo.application.mapper.UserDtoMapper;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.RoleRepository;
import com.example.demo.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllUsersUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResultPaginationDTO execute(Specification<?> spec, int page, int size) {
        // Note: This is a simplified version. In a real implementation,
        // you'd need to handle Specification conversion properly
        List<User> users = userRepository.findAllWithSpecification(spec, page - 1, size);

        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page);
        meta.setPageSize(size);
        // Note: Total count would need to be calculated properly
        meta.setTotal(users.size());
        meta.setPages((int) Math.ceil((double) users.size() / size));
        result.setMeta(meta);

        List<ResUserDTO> userDTOs = users.stream()
                .map(user -> {
                    Role role = null;
                    if (user.getRoleId() != null) {
                        role = roleRepository.findById(user.getRoleId()).orElse(null);
                    }
                    return UserDtoMapper.toResUserDTO(user, role);
                })
                .collect(Collectors.toList());

        result.setResult(userDTOs);
        return result;
    }
}
