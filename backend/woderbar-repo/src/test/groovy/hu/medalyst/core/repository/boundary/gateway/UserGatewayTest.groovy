package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.UserGateway
import com.woderbar.core.domain.model.User
import com.woderbar.core.repository.UserRepository
import com.woderbar.core.repository.boundary.mapper.response.UserEntityResponseMapper
import com.woderbar.core.repository.model.UserEntity
import spock.lang.Specification

/**
 * @author vhollosi - 2024
 */
class UserGatewayTest extends Specification {

    UserGateway userGateway

    UserRepository userRepository
    UserEntityResponseMapper responseMapper

    def setup() {
        userRepository = Mock()
        responseMapper = Mock()
        userGateway = new UserGatewayImpl(userRepository, responseMapper)
    }

    def "test get user by username"() {
        given: "username"
        String username = "username"
        and: "user entity"
        UserEntity entity = Mock()
        and: "user domain"
        User user = Mock()

        when: "trying to get user by username"
        def result = userGateway.getUserByUserName(username)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "user is retrieved from the repository"
        1 * userRepository.findByUsername(username) >> Optional.of(entity)
        then: "optional entity is mapped"
        1 * responseMapper.map(entity) >> user
        then: "optional user returns"
        result
        result == Optional.of(user)
        and: "nothing else is executed"
        0 * _
    }

}