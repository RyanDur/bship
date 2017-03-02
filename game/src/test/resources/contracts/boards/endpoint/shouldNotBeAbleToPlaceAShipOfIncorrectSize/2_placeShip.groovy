package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipOfIncorrectSize

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
            y: 0
        ],
        end  : [
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
                     objectValidations: [[
                                             code   : 'PlacementCheck',
                                             type   : 'ship',
                                             message: 'Incorrect ship placement.'
                                         ]]
                 ]]
    ])
  }
}