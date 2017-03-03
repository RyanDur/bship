package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipWithoutAnEnd

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'AIRCRAFT_CARRIER',
        start: [
            x: 0,
            y: 1
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
                                       field  : 'end',
                                       value  : null,
                                       message: 'Cannot be empty or null.'
                                   ]]
                 ]]
    ])
  }
}