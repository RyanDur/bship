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
        type : 'BATTLESHIP',
        start: [
            x: 4,
            y: 2
        ],
        end  : [
            x: 4,
            y: 5
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
                                             code   : 'ShipCollisionCheck',
                                             type   : 'board',
                                             message: 'Ship collision.'
                                         ]]
                 ]]
    ])
  }
}