package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipWithoutAType

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        id   : 1,
        start: [
            x: 0,
            y: 0
        ],
        end  : [
            x: 0,
            y: 4
        ]
    ])
  }
  response {
    status 400
    headers {
      contentType(applicationJson())
    }
    body([
        errors: [[
                     validations: [[
                                       code   : 'NonEmpty',
                                       field  : 'type',
                                       value  : null,
                                       message: 'Cannot be empty or null.'
                                   ]]
                 ]]
    ])
  }
}