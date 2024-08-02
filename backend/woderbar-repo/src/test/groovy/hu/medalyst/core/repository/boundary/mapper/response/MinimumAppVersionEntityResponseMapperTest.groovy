package com.woderbar.core.repository.boundary.mapper.response

import com.woderbar.core.repository.model.MinimumAppVersionEntity
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author vhollosi - 2024
 */
class MinimumAppVersionEntityResponseMapperTest extends Specification {

    MinimumAppVersionEntityResponseMapper responseMapper

    def setup() {
        responseMapper = new MinimumAppVersionEntityResponseMapper()
    }

    @Unroll
    def "test map entity to domain"() {
        when: "trying to map entity to domain"
        def result = responseMapper.map(new MinimumAppVersionEntity(version: version))

        then: "no exception is thrown"
        noExceptionThrown()
        and: "corresponding Version domain returns"
        result.majorVersion == majorVersion
        result.minorVersion == minorVersion
        result.patchLevel == patchLevel
        and: "nothing else is executed"
        0 * _

        where: "possible scenarios"
        version || majorVersion | minorVersion | patchLevel
        "5.2.0" || 5            | 2            | 0
        "1.2.3" || 1            | 2            | 3
    }

}