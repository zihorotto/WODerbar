package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.AthleteGateway
import com.woderbar.core.domain.model.Athlete
import com.woderbar.core.domain.model.pagination.PageInfo
import com.woderbar.core.repository.AthleteRepository
import com.woderbar.core.repository.TeamPeriodRepository
import com.woderbar.core.repository.TeamRepository
import com.woderbar.core.repository.boundary.mapper.request.AthleteEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.TeamPeriodEntityRequestMapper
import com.woderbar.core.repository.model.AthleteEntity
import com.woderbar.core.repository.model.TeamEntity
import com.woderbar.core.repository.model.TeamPeriodEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class AthleteGatewayTest extends Specification {

    AthleteGateway athleteGateway

    AthleteRepository athleteRepository
    TeamPeriodRepository teamPeriodRepository
    TeamRepository teamRepository

    AthleteEntityResponseMapper athleteEntityResponseMapper
    AthleteEntityRequestMapper athleteEntityRequestMapper
    TeamPeriodEntityRequestMapper periodMapper

    def setup() {
        athleteRepository = Mock()
        athleteEntityResponseMapper = Mock()
        athleteEntityRequestMapper = Mock()
        teamPeriodRepository = Mock()
        teamRepository = Mock()
        periodMapper = Mock()
        athleteGateway = new AthleteGatewayImpl(athleteRepository, teamPeriodRepository, teamRepository, athleteEntityResponseMapper, athleteEntityRequestMapper, periodMapper)
    }

    def "test count active athletes by teamIds"() {
        given: "team ids"
        List<Long> teamIds = [1L, 2L, 3L]
        and: "active athletes count from repository"
        Long activeAthleteCount = GroovyMock()

        when: "trying to count active athletes by team ids"
        def result = athleteGateway.countActiveAthletesByTeamIds(teamIds)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "active athlete count is retrieved from repository"
        1 * athleteRepository.countActiveAthletesByTeamIds(_, teamIds) >> activeAthleteCount
        then: "active athlete count is returned"
        result == activeAthleteCount
        and: "nothing else is executed"
        0 * _
    }

    def "test get athlete by id"() {
        given: "ids"
        Long athleteId = 1
        Long clubId = 1
        and: "athlete entity"
        AthleteEntity athleteEntity = Mock(AthleteEntity)
        and: "the expected athlete"
        Athlete expected = Mock(Athlete)

        when: "trying to retrieve the athlete by id"
        Optional<Athlete> result = athleteGateway.getByClubAndAthleteId(clubId, athleteId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athlete entity is retrieved from repository"
        1 * athleteRepository.getAthleteByClub(clubId, athleteId) >> Optional.of(athleteEntity)
        then: "athlete mapped into athlete model"
        1 * athleteEntityResponseMapper.map(athleteEntity) >> expected
        then: "the expected result returned"
        result == Optional.of(expected)
        and: "nothing else is executed"
        0 * _
    }

    def "test athlete exists by id - positive"() {
        given: "id"
        Long athleteId = 1

        when: "trying to validate the existence of the athlete"
        boolean result = athleteGateway.existsById(athleteId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athlete entity is exists in the db"
        1 * athleteRepository.existsById(athleteId) >> true
        then: "the expected result returned"
        result
        and: "nothing else is executed"
        0 * _
    }

    def "test athlete exists by id - negative"() {
        given: "id"
        Long athleteId = 1

        when: "trying to validate the existence of the athlete"
        boolean result = athleteGateway.existsById(athleteId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athlete entity is not exists in the db"
        1 * athleteRepository.existsById(athleteId) >> false
        then: "the expected result returned"
        !result
        and: "nothing else is executed"
        0 * _
    }

    def "test get all paginated athletes by club season"() {
        given: "page number and page size"
        def pageNumber = 0
        def pageSize = 10
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "ids"
        Long clubId = 1
        Long seasonId = 1
        and: "athlete active status filter"
        Boolean isActive = null
        and: "athletes page info found in the db"
        Page<AthleteEntity> athletesPageInfo = Mock(Page<AthleteEntity>)
        and: "expected result"
        PageInfo<Athlete> expected = Mock(PageInfo<Athlete>)

        when: "trying to validate the existence of the athlete"
        PageInfo<Athlete> result = athleteGateway.getPaginatedAthletesOfClubSeason(pageNumber, pageSize, clubId, seasonId, isActive)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athletes entity page is retrieved from the db"
        1 * athleteRepository.findAllByClubIdAndSeasonId(clubId, seasonId, pageable) >> athletesPageInfo
        then: "athlete entity page info is mapped into athlete page info"
        1 * athleteEntityResponseMapper.map(athletesPageInfo) >> expected
        then: "the expected result returned"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test get all active paginated athletes by club season"() {
        given: "page number and page size"
        def pageNumber = 0
        def pageSize = 10
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "ids"
        Long clubId = 1
        Long seasonId = 1
        and: "athlete active status filter"
        Boolean isActive = true
        and: "athletes page info found in the db"
        Page<AthleteEntity> athletesPageInfo = Mock(Page<AthleteEntity>)
        and: "expected result"
        PageInfo<Athlete> expected = Mock(PageInfo<Athlete>)

        when: "trying to validate the existence of the athlete"
        PageInfo<Athlete> result = athleteGateway.getPaginatedAthletesOfClubSeason(pageNumber, pageSize, clubId, seasonId, isActive)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athletes entity page is retrieved from the db"
        1 * athleteRepository.findAllByClubIdAndSeasonIdAndActiveStatus(clubId, seasonId, isActive, pageable) >> athletesPageInfo
        then: "athlete entity page info is mapped into athlete page info"
        1 * athleteEntityResponseMapper.map(athletesPageInfo) >> expected
        then: "the expected result returned"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test get all paginated athletes by team"() {
        given: "page number and page size"
        def pageNumber = 0
        def pageSize = 10
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "ids"
        Long clubId = 1
        Long seasonId = 1
        Long teamId = 1
        and: "athlete active status filter"
        Boolean isActive = null
        and: "athletes page info found in the db"
        Page<AthleteEntity> athletesPageInfo = Mock(Page<AthleteEntity>)
        and: "expected result"
        PageInfo<Athlete> expected = Mock(PageInfo<Athlete>)

        when: "trying to validate the existence of the athlete"
        PageInfo<Athlete> result = athleteGateway.getPaginatedAthletesOfTeam(pageNumber, pageSize, clubId, seasonId, teamId, isActive)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athletes entity page is retrieved from the db"
        1 * athleteRepository.findAllByClubIdAndSeasonIdAndTeamId(clubId, seasonId, teamId, pageable) >> athletesPageInfo
        then: "athlete entity page info is mapped into athlete page info"
        1 * athleteEntityResponseMapper.map(athletesPageInfo) >> expected
        then: "the expected result returned"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test get all active paginated athletes by team"() {
        given: "page number and page size"
        def pageNumber = 0
        def pageSize = 10
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "ids"
        Long clubId = 1
        Long seasonId = 1
        Long teamId = 1
        and: "athlete active status filter"
        Boolean isActive = true
        and: "athletes page info found in the db"
        Page<AthleteEntity> athletesPageInfo = Mock(Page<AthleteEntity>)
        and: "expected result"
        PageInfo<Athlete> expected = Mock(PageInfo<Athlete>)

        when: "trying to validate the existence of the athlete"
        PageInfo<Athlete> result = athleteGateway.getPaginatedAthletesOfTeam(pageNumber, pageSize, clubId, seasonId, teamId, isActive)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athletes entity page is retrieved from the db"
        1 * athleteRepository.findAllByClubIdAndSeasonIdAndTeamIdAndActiveStatus(clubId, seasonId, teamId, isActive, pageable) >> athletesPageInfo
        then: "athlete entity page info is mapped into athlete page info"
        1 * athleteEntityResponseMapper.map(athletesPageInfo) >> expected
        then: "the expected result returned"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test get all paginated athletes by club"() {
        given: "page number and page size"
        def pageNumber = 0
        def pageSize = 10
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "ids"
        Long clubId = 1
        and: "athlete active status filter"
        Boolean isActive = null
        and: "athletes page info found in the db"
        Page<AthleteEntity> athletesPageInfo = Mock(Page<AthleteEntity>)
        and: "expected result"
        PageInfo<Athlete> expected = Mock(PageInfo<Athlete>)

        when: "trying to validate the existence of the athlete"
        PageInfo<Athlete> result = athleteGateway.getPaginatedAthletesOfClub(pageNumber, pageSize, clubId, isActive)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athletes entity page is retrieved from the db"
        1 * athleteRepository.findAllByClubId(clubId, pageable) >> athletesPageInfo
        then: "athlete entity page info is mapped into athlete page info"
        1 * athleteEntityResponseMapper.map(athletesPageInfo) >> expected
        then: "the expected result returned"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test get all active paginated athletes by club"() {
        given: "page number and page size"
        def pageNumber = 0
        def pageSize = 10
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "ids"
        Long clubId = 1
        and: "athlete active status filter"
        boolean isActive = true
        and: "athletes page info found in the db"
        Page<AthleteEntity> athletesPageInfo = Mock(Page<AthleteEntity>)
        and: "expected result"
        PageInfo<Athlete> expected = Mock(PageInfo<Athlete>)

        when: "trying to validate the existence of the athlete"
        PageInfo<Athlete> result = athleteGateway.getPaginatedAthletesOfClub(pageNumber, pageSize, clubId, isActive)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athletes entity page is retrieved from the db"
        1 * athleteRepository.findAllByClubIdAndActiveStatus(clubId, isActive, pageable) >> athletesPageInfo
        then: "athlete entity page info is mapped into athlete page info"
        1 * athleteEntityResponseMapper.map(athletesPageInfo) >> expected
        then: "the expected result returned"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test athlete create by team"() {
        given: "team id"
        Long id = 1
        and: "the athletes team id list"
        Set<Long> teamIds = [id]
        and: "domain models"
        Athlete athlete = Mock(Athlete)
        and: "team to assign the athlete"
        List<TeamEntity> teamEntity = List.of(Mock(TeamEntity))
        and: "the assigment to store"
        List<TeamPeriodEntity> teamPeriodEntity = List.of(Mock(TeamPeriodEntity))
        and: "athlete to be stored"
        AthleteEntity athleteEntity = Mock(AthleteEntity)

        when: "trying to create the athlete"
        Athlete result = athleteGateway.create(athlete, teamIds)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "athlete is mapped into entity"
        1 * athleteEntityRequestMapper.map(athlete) >> athleteEntity
        then: "athlete entity is stored in the db"
        1 * athleteRepository.save(athleteEntity) >> athleteEntity
        then: "the team is retrieved from the db"
        1 * teamRepository.findAllByIdSet(teamIds) >> teamEntity
        then: "the retrieved objects mapped into team period entity"
        1 * periodMapper.mapToList(athleteEntity, teamEntity) >> teamPeriodEntity
        then: "the athlete is assigned to the team"
        1 * teamPeriodRepository.saveAll(teamPeriodEntity) >> teamPeriodEntity
        then: "the db returned entity is mapped into domain model"
        1 * athleteEntityResponseMapper.map(athleteEntity) >> athlete
        then: "the expected result returned"
        result
        result == athlete
        and: "nothing else is executed"
        0 * _
    }
}
