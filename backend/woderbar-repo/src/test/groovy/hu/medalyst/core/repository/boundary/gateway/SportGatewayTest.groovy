package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.SportGateway
import com.woderbar.core.domain.model.Sport
import com.woderbar.core.repository.SportRepository
import com.woderbar.core.repository.model.SportEntity
import spock.lang.Specification

class SportGatewayTest extends Specification {

    SportGateway sportGateway
    SportRepository sportRepository
    SportEntityResponseMapper sportEntityResponseMapper

    def setup() {
        sportRepository = Mock()
        sportEntityResponseMapper = Mock()
        sportGateway = new SportGatewayImpl(sportRepository, sportEntityResponseMapper)
    }

    def "test get all sports"() {
        given: "sport entities"
        List<SportEntity> sportEntities = [Mock(SportEntity)]
        and: "expected gateway response"
        List<Sport> sports = [Mock(Sport)]

        when: "trying to get all sport"
        def result = sportGateway.getAll()

        then: "no exception is thrown"
        noExceptionThrown()
        then: "all sport are retrieved from repository"
        1 * sportRepository.findAll() >> sportEntities
        then: "response entities are mapped to domain object"
        1 * sportEntityResponseMapper.map(sportEntities) >> sports
        and: "the expected result returns"
        result
        result == sports
        and: "nothing else is executed"
        0 * _
    }
}
