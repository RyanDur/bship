package contracts.boards.endpoint.shouldBeAbleToPlaceAShip

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
        id   : 2,
        placement: [
            x: 1,
            y: 0
        ],
        orientation  : 'DOWN',
        size   : 4
    ])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([
        id            : 1,
        pieces        : [[
                             type   : "AIRCRAFT_CARRIER",
                             placement  : [
                                 x: 0,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId: 1,
                             size   : 5,
                             sunk   : false,
                             id     : 1
                         ],
                         [
                             type   : "BATTLESHIP",
                             placement  : [
                                 x: 1,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId: 1,
                             size   : 4,
                             sunk   : false,
                             id     : 2
                         ],
                         [
                             type   : "SUBMARINE",
                             placement  : [
                                 x: null,
                                 y: null
                             ],
                             orientation: 'NONE',
                             boardId: 1,
                             size   : 3,
                             sunk   : false,
                             id     : 3
                         ],
                         [
                             type   : "CRUISER",
                             placement  : [
                                 x: null,
                                 y: null
                             ],
                             orientation: 'NONE',
                             boardId: 1,
                             size   : 3,
                             sunk   : false,
                             id     : 4
                         ],
                         [
                             type   : "DESTROYER",
                             placement  : [
                                 x: null,
                                 y: null
                             ],
                             orientation: 'NONE',
                             boardId: 1,
                             size   : 2,
                             sunk   : false,
                             id     : 5
                         ]],
        opponentPieces: [],
        moves         : [],
        opponentMoves : [],
        winner        : false
    ])
  }
}
