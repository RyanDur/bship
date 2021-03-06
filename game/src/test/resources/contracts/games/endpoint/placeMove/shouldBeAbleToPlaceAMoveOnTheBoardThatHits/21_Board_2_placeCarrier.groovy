package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoardThatHits

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/2'
    headers {
      contentType(applicationJson())
    }
    body([[
        type       : 'AIRCRAFT_CARRIER',
        id         : 6,
        placement  : [
            x: 0,
            y: 0
        ],
        orientation: 'DOWN'
    ],[
        type       : 'BATTLESHIP',
        id         : 7,
        placement  : [
            x: 1,
            y: 0
        ],
        orientation: 'DOWN'
    ],[
        type       : 'SUBMARINE',
        id         : 8,
        placement  : [
            x: 2,
            y: 0
        ],
        orientation: 'DOWN'
    ],[
        type       : 'CRUISER',
        id         : 9,
        placement  : [
            x: 3,
            y: 0
        ],
        orientation: 'DOWN'
    ],[
        type       : 'DESTROYER',
        id         : 10,
        placement  : [
            x: 4,
            y: 0
        ],
        orientation: 'DOWN'
    ]])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([
        id            : 2,
        pieces        : [[
                             type       : 'AIRCRAFT_CARRIER',
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
                             type       : 'BATTLESHIP',
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
                             type       : 'SUBMARINE',
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
                             type       : 'CRUISER',
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
                             type       : 'DESTROYER',
                             placement  : [
                                 x: 4,
                                 y: 0
                             ],
                             orientation: 'DOWN',
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
