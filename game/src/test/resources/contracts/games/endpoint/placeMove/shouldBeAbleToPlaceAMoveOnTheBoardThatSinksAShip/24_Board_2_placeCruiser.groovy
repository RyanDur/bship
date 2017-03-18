package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoardThatSinksAShip

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
        id   : 9,
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
    body([
        id           : 2,
        ships        : [[
                            type   : "AIRCRAFT_CARRIER",
                            start  : [
                                x: 0,
                                y: 0
                            ],
                            end    : [
                                x: 0,
                                y: 4
                            ],
                            boardId: 2,
                            size   : 5,
                            sunk   : false,
                            id     : 6
                        ],
                        [
                            type   : "BATTLESHIP",
                            start  : [
                                x: 1,
                                y: 0
                            ],
                            end    : [
                                x: 1,
                                y: 3
                            ],
                            boardId: 2,
                            size   : 4,
                            sunk   : false,
                            id     : 7
                        ],
                        [
                            type   : "SUBMARINE",
                            start  : [
                                x: 2,
                                y: 0
                            ],
                            end    : [
                                x: 2,
                                y: 2
                            ],
                            boardId: 2,
                            size   : 3,
                            sunk   : false,
                            id     : 8
                        ],
                        [
                            type   : "CRUISER",
                            start  : [
                                x: 3,
                                y: 0
                            ],
                            end    : [
                                x: 3,
                                y: 2
                            ],
                            boardId: 2,
                            size   : 3,
                            sunk   : false,
                            id     : 9
                        ],
                        [
                            type   : "DESTROYER",
                            start  : [
                                x: null,
                                y: null
                            ],
                            end    : [
                                x: null,
                                y: null
                            ],
                            boardId: 2,
                            size   : 2,
                            sunk   : false,
                            id     : 10
                        ]],
        opponentShips: [],
        moves        : [],
        opponentMoves: [],
        winner       : false
    ])
  }
}