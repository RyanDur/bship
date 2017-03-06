package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardPastLowerBounds

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/2'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'BATTLESHIP',
        start: [
            x: 1,
            y: 0
        ],
        end  : [
            x: 1,
            y: 3
        ]
    ])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([id   : 2,
          ships: [[
                      type : 'BATTLESHIP',
                      start: [
                          x: 1,
                          y: 0
                      ],
                      end  : [
                          x: 1,
                          y: 3
                      ],
                      sunk : false,
                      id   : $(regex(number()))
                  ]],
          moves: []
    ])
  }
}
