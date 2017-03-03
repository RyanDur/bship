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
        start: [
            x: -1,
            y: 0
        ],
        end  : [
            x: 3,
            y: 0
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
                                       code   : 'BoundsCheck',
                                       field  : 'start',
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