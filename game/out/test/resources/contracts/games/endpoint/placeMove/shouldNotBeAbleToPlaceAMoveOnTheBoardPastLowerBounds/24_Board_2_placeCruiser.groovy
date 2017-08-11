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
        type       : 'CRUISER',
        id         : 9,
        placement  : [
            x: 3,
            y: 0
        ],
        orientation: 'DOWN'
    ])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([
        id            : 2,
        pieces        : [[
                             type       : [name: "AIRCRAFT_CARRIER", size: 5],
                             placement  : [
                                 x: 0,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 2,
                             taken      : false,
                             id         : 6
                         ],
                         [
                             type       : [name: 'BATTLESHIP', size: 4],
                             placement  : [
                                 x: 1,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 2,
                             taken      : false,
                             id         : 7
                         ],
                         [
                             type       : [name: "SUBMARINE", size: 3],
                             placement  : [
                                 x: 2,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 2,
                             taken      : false,
                             id         : 8
                         ],
                         [
                             type       : [name: "CRUISER", size: 3],
                             placement  : [
                                 x: 3,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 2,
                             taken      : false,
                             id         : 9
                         ],
                         [
                             type       : [name: "DESTROYER", size: 2],
                             placement  : [
                                 x: null,
                                 y: null
                             ],
                             orientation: 'NONE',
                             boardId    : 2,
                             taken      : false,
                             id         : 10
                         ]],
        opponentPieces: [],
        moves         : [],
        opponentMoves : [],
        winner        : false
    ])
  }
}
