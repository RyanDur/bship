package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoard

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'CRUISER',
        id   : 4,
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
        id           : 1,
        pieces        : [[
                            type   : "AIRCRAFT_CARRIER",
                            start  : [
                                x: 0,
                                y: 0
                            ],
                            end    : [
                                x: 0,
                                y: 4
                            ],
                            boardId: 1,
                            size   : 5,
                            sunk   : false,
                            id     : 1
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
                            boardId: 1,
                            size   : 4,
                            sunk   : false,
                            id     : 2
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
                            boardId: 1,
                            size   : 3,
                            sunk   : false,
                            id     : 3
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
                            boardId: 1,
                            size   : 3,
                            sunk   : false,
                            id     : 4
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
                            boardId: 1,
                            size   : 2,
                            sunk   : false,
                            id     : 5
                        ]],
        opponentPieces: [],
        moves        : [],
        opponentMoves: [],
        winner       : false
    ])
  }
}
