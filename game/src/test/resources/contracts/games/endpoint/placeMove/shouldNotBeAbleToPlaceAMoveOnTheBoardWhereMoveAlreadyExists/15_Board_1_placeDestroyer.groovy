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
        type : 'DESTROYER',
        start: [
            x: 4,
            y: 0
        ],
        end  : [
            x: 4,
            y: 1
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
                      type : 'DESTROYER',
                      start: [
                          x: 4,
                          y: 0
                      ],
                      end  : [
                          x: 4,
                          y: 1
                      ],
                      sunk : false,
                      id   : $(regex(number()))
                  ]],
          moves: []
    ])
  }
}
