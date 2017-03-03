package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoard

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/2'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'CRUISER',
        start: [
            x: 3,
            y: 0
        ],
        end  : [
            x: 3,
            y: 2
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
                      type : 'CRUISER',
                      start: [
                          x: 3,
                          y: 0
                      ],
                      end  : [
                          x: 3,
                          y: 2
                      ],
                      sunk : false,
                      id   : $(regex(number()))
                  ]],
          moves: []
    ])
  }
}
