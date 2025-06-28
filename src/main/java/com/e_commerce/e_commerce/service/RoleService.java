package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.dto.requests.RoleRequest;
import com.e_commerce.e_commerce.dto.response.RoleResponse;
import com.e_commerce.e_commerce.entity.Role;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.RoleMapper;
import com.e_commerce.e_commerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse getById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        return roleMapper.toRoleResponse(role);
    }

    public RoleResponse update(String id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setPermissions(roleMapper.toRole(request).getPermissions());

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public void delete(String id) {
        roleRepository.deleteById(id);
    }
}
