package com.woderbar.domain.gateway;


import com.woderbar.domain.model.Admin;

public interface AdminGateway {

    Admin create(String email, String creator, String clubName);

}
