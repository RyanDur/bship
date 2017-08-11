package contracts.games.endpoint.placeMove.shouldNotFindGameAfterItIsOver

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type       : 'CRUISER',
        id         : 4,
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
        id            : 1,
        pieces        : [[
                             type       : [name: 'AIRCRAFT_CARRIER', size: 5],
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
                             type       : [name: 'BATTLESHIP', size: 4],
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
                             type       : [name: "SUBMARINE", size: 3],
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
                             type       : [name: "CRUISER", size: 3],
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
                             type       : [name: "DESTROYER", size: 2],
                             placement  : [
                                 x: null,
                                 y: null
                             ],
                             orientation: 'NONE',
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
