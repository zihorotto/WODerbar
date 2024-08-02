package com.woderbar.core.repository.boundary.gateway

import com.woderbar.core.business.gateway.PaymentProductGateway
import com.woderbar.core.domain.model.PaymentProduct
import com.woderbar.core.domain.model.pagination.PageInfo
import com.woderbar.core.repository.PaymentProductRepository
import com.woderbar.core.repository.model.PaymentProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class PaymentProductGatewayTest extends Specification {

    PaymentProductGateway productGateway
    PaymentProductRepository paymentProductRepository
    PaymentProductEntityResponseMapper paymentProductEntityResponseMapper

    def setup() {
        paymentProductRepository = Mock()
        paymentProductEntityResponseMapper = Mock()
        productGateway = new PaymentProductGatewayImpl(paymentProductEntityResponseMapper, paymentProductRepository)
    }

    def "test get paginated payment product of clubs season"() {
        given: "ids"
        Long clubId = 1
        Long seasonId = 1
        and: "page settings"
        int pageNumber = 0
        int pageSize = 10
        and: "pageable object"
        Pageable pageable = PageRequest.of(pageNumber, pageSize)
        and: "page of payment product entity"
        Page<PaymentProductEntity> paymentProductEntity = Mock(Page<PaymentProductEntity>)
        and: "expected page info of the payment product"
        PageInfo<PaymentProduct> expected = Mock(PageInfo<PaymentProduct>)

        when: "trying to get the paginated payment product of the clubs season"
        PageInfo<PaymentProduct> result = productGateway.getPaginatedPaymentProductOfClubSeason(pageNumber, pageSize, clubId, true)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "page of payment product entity is retrieved from the repository"
        1 * paymentProductRepository.findAllBySeasonId(seasonId, pageable) >> paymentProductEntity
        then: "page of payment product entity mapped into page info of payment product"
        1 * paymentProductEntityResponseMapper.map(paymentProductEntity) >> expected
        then: "the result is the expected page info of the payment product"
        result == expected
        and: "nothing else is executed"
        0 * _
    }

    def "test find payment product by club and season id - positive"() {
        given: "ids"
        Long clubId = 1
        Long seasonId = 1
        Long productId = 1
        and: "payment product entity"
        PaymentProductEntity paymentProductEntity = Mock(PaymentProductEntity)
        and: "payment product"
        PaymentProduct paymentProduct = Mock(PaymentProduct)
        and: "expected result"
        Optional<PaymentProduct> expected = Optional.of(paymentProduct)

        when: "trying to get the payment product"
        Optional<PaymentProduct> result = productGateway.findPaymentProductBySeasonAndClubId(clubId, seasonId, productId)

        then: "no exception is thrown"
        noExceptionThrown()
        then: "payment product is retrieved from repository"
        1 * paymentProductRepository.findPaymentProductBySeasonAndClubId(clubId, seasonId, productId) >> Optional.of(paymentProductEntity)
        then: "payment product is mapped into domain object"
        1 * paymentProductEntityResponseMapper.map(paymentProductEntity) >> paymentProduct
        then: "the result is a payment product optional"
        result
        result == expected
        and: "nothing else is executed"
        0 * _
    }
}