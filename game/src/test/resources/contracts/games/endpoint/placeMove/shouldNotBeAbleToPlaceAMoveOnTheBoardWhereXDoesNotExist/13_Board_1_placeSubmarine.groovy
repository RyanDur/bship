package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardPastLowerBounds

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'SUBMARINE',
        start: [
            x: 2,
            y: 0
        ],
        end  : [
            x: 2,
            y: 2
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
                      type : 'BATTLESHIP',
                      start: [
                          x: 2,
                          y: 0
                      ],
                      end  : [
                          x: 2,
                          y: 2
                      ],
                      sunk : false,
                      id   : $(regex(number()))
                  ]],
          moves: []
    ])
  }
}
