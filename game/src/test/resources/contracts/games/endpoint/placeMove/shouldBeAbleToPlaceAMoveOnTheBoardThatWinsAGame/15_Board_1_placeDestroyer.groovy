package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoardThatWinsAGame

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
        id   : 5,
        placement: [
            x: 4,
            y: 0
        ],
        orientation: 'DOWN',
        size   : 2
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
                             type       : "AIRCRAFT_CARRIER",
                             placement  : [
                                 x: 0,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             size       : 5,
                             taken       : false,
                             id         : 1
                         ],
                         [
                             type       : "BATTLESHIP",
                             placement  : [
                                 x: 1,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             size       : 4,
                             taken       : false,
                             id         : 2
                         ],
                         [
                             type       : "SUBMARINE",
                             placement  : [
                                 x: 2,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             size       : 3,
                             taken       : false,
                             id         : 3
                         ],
                         [
                             type       : "CRUISER",
                             placement  : [
                                 x: 3,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             size       : 3,
                             taken       : false,
                             id         : 4
                         ],
                         [
                             type       : "DESTROYER",
                             placement  : [
                                 x: 4,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             size       : 2,
                             taken       : false,
                             id         : 5
                         ]],
        opponentPieces: [],
        moves         : [],
        opponentMoves : [],
        winner        : false
    ])
  }
}
