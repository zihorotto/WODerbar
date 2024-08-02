package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.ClubGateway
import com.woderbar.core.domain.model.Admin
import com.woderbar.core.domain.model.Club
import com.woderbar.core.domain.model.Sport
import com.woderbar.core.domain.model.pagination.PageInfo
import com.woderbar.core.repository.ClubRepository
import com.woderbar.core.repository.boundary.mapper.request.AdminEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.ClubEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.CurrencyEntityRequestMapper
import com.woderbar.core.repository.boundary.mapper.request.SportEntityRequestMapper
import com.woderbar.core.repository.model.AdminEntity
import com.woderbar.core.repository.model.ClubEntity
import com.woderbar.core.repository.model.CurrencyEntity
import com.woderbar.core.repository.model.SportEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class ClubGatewayTest extends Specification {

    ClubGateway clubGateway

    ClubRepository clubRepository
    ClubEntityResponseMapper responseMapper
    ClubEntityRequestMapper clubEntityRequestMapper
    AdminEntityRequestMapper adminRequestMapper
    CurrencyEntityRequestMapper currencyRequestMapper
    SportEntityRequestMapper sportEntityRequestMapper

    def setup() {
        clubRepository = Mock()
        responseMapper = Mock()
        clubEntityRequestMapper = Mock()
        adminRequestMapper = Mock()
        currencyRequestMapper = Mock()
        sportEntityRequestMapper = Mock()
        clubGateway = new ClubGatewayImpl(clubRepository, responseMapper, clubEntityRequestMapper, adminRequestMapper, currencyRequestMapper, sportEntityRequestMapper)
    }

    def "test get paginated club"() {
        given: "page number and page size"
        def pageNumber = 0
        def pageSize = 10
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "paginated club entity from repository"
        Page<ClubEntity> paginatedClubEntity = Mock(Page<ClubEntity>)
        PageInfo<Club> paginatedClub = Mock(PageInfo<Club>)

        when: "trying to get paginated clubs"
        def result = clubGateway.getPaginatedClubs(pageNumber, pageSize)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "paginated club entity is retrieved from repository"
        1 * clubRepository.findAll(pageable) >> paginatedClubEntity
        then: "response entity is mapped to domain object"
        1 * responseMapper.map(paginatedClubEntity) >> paginatedClub
        and: "the expected result returns"
        result
        result == paginatedClub
        and: "nothing else is executed"
        0 * _
    }

    def "test get club by id"() {
        given: "id"
        Long id = 1
        and: "club entity from the repository"
        Optional<ClubEntity> clubEntity = Optional.of(Mock(ClubEntity))
        and: "expected optional club response"
        Club expected = Mock(Club)

        when: "trying to get the club"
        Optional<Club> result = clubGateway.getClubByClubId(id)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "club entity is retrieved from repository"
        1 * clubRepository.findById(id) >> clubEntity
        then: "club entity is mapped to domain object"
        1 * responseMapper.map(clubEntity.get()) >> expected
        and: "the expected result returns"
        result
        result == Optional.of(expected)
        and: "nothing else is executed"
        0 * _
    }

    def "test club exist by id - positive"() {
        given: "club id"
        Long id = 1

        when: "trying to validate the existence of the club in the database"
        Boolean result = clubGateway.existsById(id)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "club entity is found in the repository"
        1 * clubRepository.existsById(id) >> true
        and: "the result is true"
        result
        and: "nothing else is executed"
        0 * _
    }

    def "test club exist by id - negative"() {
        given: "club id"
        Long id = 1

        when: "trying to validate the existence of the club in the database"
        Boolean result = clubGateway.existsById(id)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "club entity is not found in the repository"
        1 * clubRepository.existsById(id) >> false
        and: "the result is false"
        !result
        and: "nothing else is executed"
        0 * _
    }

    def "test club create"() {
        given: "request domain models"
        Club clubRequest = Mock(Club)
        Admin admin = Mock(Admin)
        Currency currency = Mock(Currency)
        Sport sport = Mock(Sport)
        and: "mapped entities"
        ClubEntity clubEntity = Mock(ClubEntity)
        AdminEntity adminEntity = Mock(AdminEntity)
        CurrencyEntity currencyEntity = Mock(CurrencyEntity)
        SportEntity sportEntity = Mock(SportEntity)
        and: "expected club result"
        Club expected = Mock(Club)

        when: "trying to create the club "
        Club result = clubGateway.create(clubRequest, admin, currency, sport)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "club request is mapped into club entity"
        1 * clubEntityRequestMapper.map(clubRequest) >> clubEntity
        then: "admin is mapped into admin entity"
        1 * adminRequestMapper.map(admin) >> adminEntity
        then: "currency is mapped into currency entity"
        1 * currencyRequestMapper.map(currency) >> currencyEntity
        then: "sport is mapped into sport entity"
        1 * sportEntityRequestMapper.map(sport) >> sportEntity
        then: "then the club entity admin is set"
        1 * clubEntity.setAdmins(List.of(adminEntity))
        then: "then the club entity currency is set"
        1 * clubEntity.setCurrency(currencyEntity)
        then: "then the club entity sport is set"
        1 * clubEntity.setSport(sportEntity)
        then: "the club entity saved in the db"
        1 * clubRepository.save(clubEntity) >> clubEntity
        then: "the saved club entity mapped into club"
        1 * responseMapper.map(clubEntity) >> expected
        and: "the result is the expected club"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }
}
