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
        type : 'AIRCRAFT_CARRIER',
        start: [
            x: 3,
            y: 3
        ],
        end  : [
            x: 7,
            y: 3
        ]
    ])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([id   : 1,
          ships: [[
                      type : 'AIRCRAFT_CARRIER',
                      start: [
                          x: 3,
                          y: 3
                      ],
                      end  : [
                          x: 7,
                          y: 3
                      ],
                      sunk : false,
                      id   : 1
                  ]],
          moves: []
    ])
  }
}
