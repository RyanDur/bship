package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipUponAnotherShip

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type       : 'BATTLESHIP',
        id         : 2,
        placement  : [
            x: 4,
            y: 2
        ],
        orientation: 'DOWN',
        size       : 4
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
                                       code   : 'ShipCollisionCheck',
                                       type   : 'board',
                                       message: 'Ship collision.'
                                   ]]
                 ]]
    ])
  }
}
