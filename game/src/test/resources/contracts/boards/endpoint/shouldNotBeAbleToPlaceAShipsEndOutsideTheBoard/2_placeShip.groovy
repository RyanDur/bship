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
        start: [
            x: 9,
            y: 6
        ],
        end  : [
            x: 9,
            y: 10
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
                                       field  : 'end',
                                       value  : [
                                           x: 9,
                                           y: 10
                                       ],
                                       message: 'out of bounds.'
                                   ]]
                 ]]
    ])
  }
}