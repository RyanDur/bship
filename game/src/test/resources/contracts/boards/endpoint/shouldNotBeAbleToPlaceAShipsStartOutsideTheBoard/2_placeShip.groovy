package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipsStartOutsideTheBoard

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
            x: -1,
            y: 0
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
                                       code   : 'BoundsCheck',
                                       field  : 'placement',
                                       value  : [
                                           x: -1,
                                           y: 0
                                       ],
                                       message: 'out of bounds.'
                                   ]]
                 ]]
    ])
  }
}