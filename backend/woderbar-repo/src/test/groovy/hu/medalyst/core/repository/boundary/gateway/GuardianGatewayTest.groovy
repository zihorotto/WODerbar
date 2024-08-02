package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.GuardianGateway
import com.woderbar.core.repository.GuardianRepository
import spock.lang.Specification

class GuardianGatewayTest extends Specification {

    GuardianGateway guardianGateway
    GuardianRepository guardianRepository

    def setup() {
        guardianRepository = Mock()
        guardianGateway = new GuardianGatewayImpl(guardianRepository)
    }

    def "test count number of guardians by club id"() {
        given: "club id"
        Long clubId = 1L
        and: "number of guardians from repository"
        Long numberOfGuardians = GroovyMock()

        when: "trying to count guardians by club id"
        def result = guardianGateway.countByClubId(clubId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "number of guardians is retrieved from repository"
        1 * guardianRepository.countByClubId(clubId) >> numberOfGuardians
        and: "number of guardians is returned"
        result == numberOfGuardians
        and: "nothing else is executed"
        0 * _
    }
}
