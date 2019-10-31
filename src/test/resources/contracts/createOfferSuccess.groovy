package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Create Offer Request Success")
    request {
        method 'POST'
        url '/offer'
        body (
                '''
            {
                "description": "test 3",
                "amount": 2.99,
                "currency": "GBP",
                "startDate": "2019-10-28 23:45",
                "endDate": "2019-10-29 23:45"
            }
               '''
        )
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }

    response {
        status 201
        body (
            '''
            {
                "location": "http://localhost:51954/offers/1"
            }
            '''
        )
    }
}