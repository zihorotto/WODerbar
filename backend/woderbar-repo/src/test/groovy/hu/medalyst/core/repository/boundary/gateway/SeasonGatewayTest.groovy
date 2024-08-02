package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.SeasonGateway
import com.woderbar.core.domain.model.Season
import com.woderbar.core.repository.SeasonRepository
import com.woderbar.core.repository.boundary.mapper.request.ClubEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.CurrencyEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.SeasonEntityRequestMapper
import com.woderbar.core.repository.model.SeasonEntity
import spock.lang.Specification

class SeasonGatewayTest extends Specification {

    SeasonGateway seasonGateway

    SeasonRepository seasonRepository
    SeasonEntityResponseMapper responseMapper
    SeasonEntityRequestMapper requestMapper
    ClubEntityRequestMapper clubEntityRequestMapper
    CurrencyEntityRequestMapper currencyEntityRequestMapper

    def setup() {
        seasonRepository = Mock()
        responseMapper = Mock()
        requestMapper = Mock()
        clubEntityRequestMapper = Mock()
        currencyEntityRequestMapper = Mock()
        seasonGateway = new SeasonGatewayImpl(seasonRepository, responseMapper, requestMapper, clubEntityRequestMapper, currencyEntityRequestMapper)
    }

    def "test find active season by club id"() {
        given: "id"
        Long id = 1
        and: "season entity from the repository"
        List<SeasonEntity> seasonEntity = List.of(Mock(SeasonEntity))
        and: "mapped season entity"
        List<Season> mappedSeasonEntity = List.of(Mock(Season))

        when: "trying to get the active season"
        List<Season> result = seasonGateway.findSeasonsByCLubId(id)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "season entity is retrieved from repository"
        1 * seasonRepository.findAllByClub_Id(id) >> seasonEntity
        then: "season entity is mapped to domain object"
        1 * responseMapper.map(seasonEntity) >> mappedSeasonEntity
        and: "the expected result returns"
        result
        result.size() == mappedSeasonEntity.size()
        and: "nothing else is executed"
        0 * _
    }

    def "test find active season by club id with no active season"() {
        given: "id"
        Long id = 1
        and: "no season registered to the club"
        List<SeasonEntity> seasons = new ArrayList<>()
        and: "expected empty list"
        List<Season> expected = new ArrayList<>()

        when: "trying to get the active season"
        List<Season> result = seasonGateway.findSeasonsByCLubId(id)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "season entity with active season not found in the repository"
        1 * seasonRepository.findAllByClub_Id(id) >> seasons
        then: "season entity is mapping returns an empty list"
        1 * responseMapper.map(seasons) >> expected
        and: "the result is an empty optional"
        result.size() == seasons.size()
        and: "nothing else is executed"
        0 * _
    }

    def "test season exists by id - positive"() {
        given: "id"
        Long id = 1
        and: "season exist in database"
        boolean seasonIsExists = true

        when: "trying to get the active season"
        boolean result = seasonGateway.existsById(id)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "season is found in the database"
        1 * seasonRepository.existsById(id) >> seasonIsExists
        and: "the result is an empty optional"
        result
        and: "nothing else is executed"
        0 * _
    }

    def "test season exists by id - negative"() {
        given: "id"
        Long id = 1
        and: "season not exist in database"
        boolean seasonIsExists = false

        when: "trying to get the active season"
        boolean result = seasonGateway.existsById(id)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "season is found in the database"
        1 * seasonRepository.existsById(id) >> seasonIsExists
        and: "the result is an empty optional"
        !result
        and: "nothing else is executed"
        0 * _
    }

    def "test get number of season by club id in year"() {
        given: "id"
        Long id = 1
        and: "year of start date"
        int startDateYear = 2000
        and: "expected number of seasons"
        long expected = 1

        when: "trying to get the number of seasons in the year"
        long result = seasonGateway.getNumberOfSeasonsInYearByClubId(id, startDateYear)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "the number of seasons returned"
        1 * seasonRepository.countSeasonsByClubIdAndStartDateYear(id, startDateYear) >> expected
        and: "the expected result returns"
        result == expected
        and: "nothing else is executed"
        0 * _
    }
}
