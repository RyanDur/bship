package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoardThatWinsAGame

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/2'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'AIRCRAFT_CARRIER',
        id   : 6,
        placement: [
            x: 0,
            y: 0
        ],
        orientation: 'DOWN',
        size: 5
    ])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([
        id           : 2,
        pieces        : [[
                            type   : "AIRCRAFT_CARRIER",
                            placement: [
                                x: 0,
                                y: 0
                            ],
                            orientation: 'DOWN',
                            boardId: 2,
                            size   : 5,
                            taken   : false,
                            id     : 6
                        ],
                        [
                            type   : "BATTLESHIP",
                            placement: [
                                x: null,
                                y: null
                            ],
                            orientation: 'NONE',
                            boardId: 2,
                            size   : 4,
                            taken   : false,
                            id     : 7
                        ],
                        [
                            type   : "SUBMARINE",
                            placement: [
                                x: null,
                                y: null
                            ],
                            orientation: 'NONE',
                            boardId: 2,
                            size   : 3,
                            taken   : false,
                            id     : 8
                        ],
                        [
                            type   : "CRUISER",
                            placement: [
                                x: null,
                                y: null
                            ],
                            orientation: 'NONE',
                            boardId: 2,
                            size   : 3,
                            taken   : false,
                            id     : 9
                        ],
                        [
                            type   : "DESTROYER",
                            placement: [
                                x: null,
                                y: null
                            ],
                            orientation: 'NONE',
                            boardId: 2,
                            size   : 2,
                            taken   : false,
                            id     : 10
                        ]],
        opponentPieces: [],
        moves        : [],
        opponentMoves: [],
        winner       : false
    ])
  }
}
