package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipUponAnotherShip

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([[
        type       : 'AIRCRAFT_CARRIER',
        id         : 1,
        placement  : [
            x: 3,
            y: 3
        ],
        orientation: 'RIGHT',
    ],[
        type       : 'BATTLESHIP',
        id         : 2,
        placement  : [
            x: 4,
            y: 2
        ],
        orientation: 'DOWN'
    ]])
  }
  response {
    status 400
    headers {
      contentType(applicationJson())
    }
    body([
        errors: [[
                     validations: [[
                                       code   : 'ShipCollisionCheck',
                                       type   : 'board',
                                       message: 'Ship collision.'
                                   ]]
                 ]]
    ])
  }
}
