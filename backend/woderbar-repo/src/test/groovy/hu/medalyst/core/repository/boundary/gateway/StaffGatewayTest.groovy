package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.StaffGateway
import com.woderbar.core.repository.StaffRepository
import spock.lang.Specification

class StaffGatewayTest extends Specification {

    StaffGateway staffGateway
    StaffRepository staffRepository

    def setup() {
        staffRepository = Mock()
        staffGateway = new StaffGatewayImpl(staffRepository)
    }

    def "test count number of staff by team ids"() {
        given: "team ids"
        List<Long> teamIds = [1L, 2L, 3L]
        and: "number of staff from repository"
        Long numberOfStaff = GroovyMock()

        when: "trying to count staff by team ids"
        def result = staffGateway.countNumberOfStaffByTeamIds(teamIds)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "number of staff is retrieved from repository"
        1 * staffRepository.countNumberOfStaffByTeamIds(teamIds) >> numberOfStaff
        then: "number of staff is returned"
        result == numberOfStaff
        and: "nothing else is executed"
        0 * _
    }

}