package com.woderbar.core.repository.boundary.gateway

import com.fasterxml.jackson.core.Version
import com.woderbar.core.business.gateway.VersionGateway
import com.woderbar.core.domain.model.type.ClientPlatformType
import com.woderbar.core.repository.MinimumAppVersionRepository
import com.woderbar.core.repository.model.MinimumAppVersionEntity
import spock.lang.Specification

/**
 * @author vhollosi - 2024
 */
class VersionGatewayTest extends Specification {

    VersionGateway versionGateway

    MinimumAppVersionRepository minimumAppVersionRepository
    MinimumAppVersionEntityResponseMapper responseMapper

    def setup() {
        minimumAppVersionRepository = Mock()
        responseMapper = Mock()
        versionGateway = new VersionGatewayImpl(minimumAppVersionRepository, responseMapper)
    }

    def "test get minimum app version"() {
        given: "Client platform"
        ClientPlatformType clientPlatform = ClientPlatformType.ANDROID
        and: "minimum app version from repository"
        def minimumAppVersion = Mock(MinimumAppVersionEntity)
        Version version = Mock()

        when: "trying to get minimum app version"
        def result = versionGateway.getMinimumAppVersion(clientPlatform)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "minimum app version is retrieved from repository"
        1 * minimumAppVersionRepository.findById(clientPlatform) >> Optional.of(minimumAppVersion)
        then: "response entity is mapped to domain object"
        1 * responseMapper.map(minimumAppVersion) >> version
        and: "optional result returns with the mapped version"
        result
        result.get() == version
        and: "nothing else is executed"
        0 * _
    }

}