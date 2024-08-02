package com.woderbar.core.repository

import com.woderbar.core.RepositoryConfig
import com.woderbar.core.repository.model.SportEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneOffset

@DataJpaTest
@ContextConfiguration(classes = RepositoryConfig.class)
class SportRepositoryTest extends Specification {

    @Autowired
    private SportRepository repository

    @Ignore
    def "findAll should return all saved sports"() {
        given: "2 sports are saved"
        def sport1 = new SportEntity(name: "Football", created: LocalDateTime.now(ZoneOffset.UTC), creator: "system")
        def sport2 = new SportEntity(name: "Basketball", created: LocalDateTime.now(ZoneOffset.UTC), creator: "system")
        repository.save(sport1)
        repository.save(sport2)

        when: "trying to find all sports"
        def results = repository.findAll()

        then: "the previously saved 2 sports return"
        results.size() == 2
        results.containsAll([sport1, sport2])
    }
}
