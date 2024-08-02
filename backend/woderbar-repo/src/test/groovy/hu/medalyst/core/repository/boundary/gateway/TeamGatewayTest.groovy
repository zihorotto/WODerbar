package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.TeamGateway
import com.woderbar.core.domain.model.Club
import com.woderbar.core.domain.model.Season
import com.woderbar.core.domain.model.Team
import com.woderbar.core.domain.model.pagination.PageInfo
import com.woderbar.core.repository.AthleteRepository
import com.woderbar.core.repository.TeamPeriodRepository
import com.woderbar.core.repository.TeamRepository
import com.woderbar.core.repository.boundary.mapper.request.ClubEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.SeasonEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.TeamEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.TeamPeriodEntityRequestMapper
import com.woderbar.core.repository.model.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class TeamGatewayTest extends Specification {

    TeamGateway teamGateway
    TeamRepository teamRepository
    AthleteRepository athleteRepository
    TeamPeriodRepository teamPeriodRepository

    TeamEntityResponseMapper teamEntityResponseMapper
    ClubEntityRequestMapper clubEntityRequestMapper
    SeasonEntityRequestMapper seasonEntityRequestMapper
    TeamEntityRequestMapper teamEntityRequestMapper
    TeamPeriodEntityRequestMapper periodEntityRequestMapper

    def setup() {
        teamRepository = Mock()
        athleteRepository = Mock()
        teamPeriodRepository = Mock()
        teamEntityResponseMapper = Mock()
        clubEntityRequestMapper = Mock()
        seasonEntityRequestMapper = Mock()
        teamEntityRequestMapper = Mock()
        periodEntityRequestMapper = Mock()
        teamGateway = new TeamGatewayImpl(teamRepository, athleteRepository, teamPeriodRepository, teamEntityResponseMapper, clubEntityRequestMapper, seasonEntityRequestMapper, teamEntityRequestMapper, periodEntityRequestMapper)
    }

    def "test find all team ids by season id"() {
        given: "season id"
        def seasonId = 1L
        and: "team ids from repository"
        List<Long> teamIds = GroovyMock()

        when: "trying to get all team ids by season id"
        def result = teamGateway.findAllTeamIdsBySeasonId(seasonId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "team ids are retrieved from repository"
        1 * teamRepository.findAllTeamIdsBySeasonId(seasonId) >> teamIds
        then: "team ids are returned"
        result == teamIds
        and: "nothing else is executed"
        0 * _
    }

    def "test find team by club and season and team id - positive"() {
        given: "ids"
        Long clubId = 1
        Long seasonId = 1
        Long teamId = 1
        and: "team entity"
        TeamEntity teamEntity = Mock(TeamEntity)
        and: "team"
        Team team = Mock(Team)
        and: "expected result"
        Optional<Team> expected = Optional.of(team)

        when: "trying to get the team"
        Optional<Team> result = teamGateway.findTeamByClubSeasonAndTeamId(clubId, seasonId, teamId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "team is retrieved from repository"
        1 * teamRepository.findTeamByClubAndSeasonAndTeamId(clubId, seasonId, teamId) >> Optional.of(teamEntity)
        then: "team is mapped into domain object"
        1 * teamEntityResponseMapper.map(teamEntity) >> team
        then: "the result is a team optional"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test find team by club and season and team id - negative"() {
        given: "ids"
        Long clubId = 1
        Long seasonId = 1
        Long teamId = 1

        when: "trying to get the team"
        Optional<Team> result = teamGateway.findTeamByClubSeasonAndTeamId(clubId, seasonId, teamId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "team is retrieved from repository"
        1 * teamRepository.findTeamByClubAndSeasonAndTeamId(clubId, seasonId, teamId) >> Optional.empty()
        then: "the result is an empty optional"
        !result
        and: "nothing else is executed"
        0 * _
    }

    def "test team exists by id"() {
        given: "team id"
        Long teamId = 1

        when: "the team existence is checked by the team gateway"
        boolean result = teamGateway.existsById(teamId)

        then: "the team exist in the db"
        1 * teamRepository.existsById(teamId) >> true
        then: "the expected result returns"
        result
        and: "nothing else is executed"
        0 * _
    }

    def "test get paginated teams of clubs season"() {
        given: "ids"
        Long clubId = 1
        Long seasonId = 1
        and: "page settings"
        int pageNumber = 0
        int pageSize = 10
        and: "pageable object"
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "page of team entity"
        Page<TeamEntity> teamEntityPage = Mock(Page<TeamEntity>)
        and: "expected page info of the team"
        PageInfo<Team> expected = Mock(PageInfo<Team>)

        when: "trying to get the paginated teams of the clubs season"
        PageInfo<Team> result = teamGateway.getPaginatedTeamsOfClubSeason(pageNumber, pageSize, clubId, seasonId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "page of team entity is retrieved from the repository"
        1 * teamRepository.findAllByClubIdAndSeasonId(clubId, seasonId, pageable) >> teamEntityPage
        then: "page of team entity mapped into page info of team"
        1 * teamEntityResponseMapper.map(teamEntityPage) >> expected
        then: "the result is the expected page info of the team"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test get paginated teams of club"() {
        given: "club id"
        Long clubId = 1
        and: "page settings"
        int pageNumber = 0
        int pageSize = 10
        and: "pageable object"
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "page of team entity"
        Page<TeamEntity> teamEntityPage = Mock(Page<TeamEntity>)
        and: "expected page info of the team"
        PageInfo<Team> expected = Mock(PageInfo<Team>)

        when: "trying to get the paginated teams of the clubs season"
        PageInfo<Team> result = teamGateway.getPaginatedTeamsOfClub(pageNumber, pageSize, clubId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "page of team entity is retrieved from the repository"
        1 * teamRepository.findAllByClubId(clubId, pageable) >> teamEntityPage
        then: "page of team entity mapped into page info of team"
        1 * teamEntityResponseMapper.map(teamEntityPage) >> expected
        then: "the result is the expected page info of the team"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test create team"() {
        given: "team creation details"
        Set<String> names = ["testName"]
        and: "creator name"
        String creator = "anonymousUser"
        and: "club"
        Club club = Mock(Club)
        and: "season"
        Season season = Mock(Season)
        and: "mapped entities"
        ClubEntity clubEntity = Mock(ClubEntity)
        SeasonEntity seasonEntity = Mock(SeasonEntity)
        List<TeamEntity> teamEntity = [Mock(TeamEntity)]
        and: "expected team result"
        List<Team> expected = [Mock(Team)]

        when: "trying to create the team"
        List<Team> result = teamGateway.create(names, club, season)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "club mapped into club entity"
        1 * clubEntityRequestMapper.map(club) >> clubEntity
        then: "season mapped into season entity"
        1 * seasonEntityRequestMapper.map(season) >> seasonEntity
        then: "team entities created from the previously prepared data"
        1 * teamEntityRequestMapper.mapToEntities(names, clubEntity, seasonEntity, creator) >> teamEntity
        then: "team entity saved into the db"
        1 * teamRepository.saveAll(teamEntity) >> teamEntity
        then: "team entity mapped into expected team"
        1 * teamEntityResponseMapper.map(teamEntity) >> expected
        then: "the result is the expected team"
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def 'test assignAthletes athletes to teams'() {
        given: "athletes and teams ids"
        Long teamId = 1
        Long athleteId = 1
        and: "a collection of athlete ids"
        Set<Long> athleteIds = new HashSet<>()
        athleteIds.add(athleteId)
        and: "a map of athlete and team ids"
        Map<Long, Set<Long>> teamAthletesMap = new HashMap<>()
        teamAthletesMap.put(teamId, athleteIds)
        and: "retrieved entities"
        TeamEntity teamEntity = new TeamEntity()
        List<AthleteEntity> athleteEntities = [new AthleteEntity()]
        and: "a list of mapped period entity"
        List<TeamPeriodEntity> teamPeriodEntities = [new TeamPeriodEntity()]

        when: "trying to assign the athletes"
        teamGateway.assignAthletesToTeams(teamAthletesMap)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "the team entity is retrieved"
        1 * teamRepository.findById(teamId) >> Optional.of(teamEntity)
        then: "the athlete entities retrieved"
        1 * athleteRepository.findAllById(athleteIds) >> athleteEntities
        then: "the period entity is mapped"
        1 * periodEntityRequestMapper.mapToList(teamEntity, athleteEntities) >> teamPeriodEntities
        then: "the assignments stored in the db"
        1 * teamPeriodRepository.saveAll(teamPeriodEntities)
        and: "nothing else is executed"
        0 * _
    }
}
