package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipsEndOutsideTheBoard

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
        id   : 1,
        placement: [
            x: 9,
            y: 6
        ],
        orientation: 'DOWN',
        size: 5
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
                                       code   : 'PlacementCheck',
                                       type  : 'piece',
                                       message: 'Incorrect ship placement.'
                                   ]]
                 ]]
    ])
  }
}