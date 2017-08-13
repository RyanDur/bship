package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardWhereXDoesNotExist

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([[
        type       : 'AIRCRAFT_CARRIER',
        id         : 1,
        placement  : [
            x: 0,
            y: 0
        ],
        orientation: 'DOWN'
    ],[
        type       : 'BATTLESHIP',
        id         : 2,
        placement  : [
            x: 1,
            y: 0
        ],
        orientation: 'DOWN',
        size       : 4
    ],[
        type       : 'SUBMARINE',
        id         : 3,
        placement  : [
            x: 2,
            y: 0
        ],
        orientation: 'DOWN'
    ],[
        type       : 'CRUISER',
        id         : 4,
        placement  : [
            x: 3,
            y: 0
        ],
        orientation: 'DOWN'
    ],[
        type       : 'DESTROYER',
        id         : 5,
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
        id            : 1,
        pieces        : [[
                             type       : 'AIRCRAFT_CARRIER',
                             placement  : [
                                 x: 0,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             taken      : false,
                             id         : 1
                         ],
                         [
                             type       : 'BATTLESHIP',
                             placement  : [
                                 x: 1,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             taken      : false,
                             id         : 2
                         ],
                         [
                             type       : 'SUBMARINE',
                             placement  : [
                                 x: 2,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             taken      : false,
                             id         : 3
                         ],
                         [
                             type       : 'CRUISER',
                             placement  : [
                                 x: 3,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             taken      : false,
                             id         : 4
                         ],
                         [
                             type       : 'DESTROYER',
                             placement  : [
                                 x: 4,
                                 y: 0
                             ],
                             orientation: 'DOWN',
                             boardId    : 1,
                             taken      : false,
                             id         : 5
                         ]],
        opponentPieces: [],
        moves         : [],
        opponentMoves : [],
        winner        : false
    ])
  }
}
