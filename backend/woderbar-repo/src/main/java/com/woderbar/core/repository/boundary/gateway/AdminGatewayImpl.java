package com.woderbar.core.repository.boundary.gateway;


import com.woderbar.core.repository.AdminRepository;
import com.woderbar.core.repository.boundary.mapper.request.AdminEntityRequestMapper;
import com.woderbar.core.repository.boundary.mapper.response.AdminEntityResponseMapper;
import com.woderbar.core.repository.model.AdminEntity;
import com.woderbar.domain.gateway.AdminGateway;
import com.woderbar.domain.model.Admin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class AdminGatewayImpl implements AdminGateway {

    private final AdminEntityResponseMapper adminEntityResponseMapper;
    private AdminRepository repository;
    private AdminEntityRequestMapper adminEntityRequestMapper;

    @Autowired
    public AdminGatewayImpl(AdminEntityResponseMapper adminEntityResponseMapper) {
        this.adminEntityResponseMapper = adminEntityResponseMapper;
    }

    @Override
    public Admin create(String email, String creator, String clubName) {
        AdminEntity adminEntity = adminEntityRequestMapper.map(email, creator, clubName);
        adminEntity.setUserRole();
        return adminEntityResponseMapper.map(repository.save(adminEntity));
    }

}
