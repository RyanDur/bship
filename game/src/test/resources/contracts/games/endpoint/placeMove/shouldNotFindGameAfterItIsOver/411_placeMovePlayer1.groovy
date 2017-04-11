package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoardThatWinsAGame

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/games/1'
    headers {
      contentType(applicationJson())
    }
    body([
        boardId: 1,
        point  : [
            x: 2,
            y: 2
        ]])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([
        boards: [[
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
                                          taken       : true,
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
                                          taken       : true,
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
                                          taken       : true,
                                          id         : 5
                                      ]],
                     opponentPieces: [[
                                          type       : "AIRCRAFT_CARRIER",
                                          placement  : [
                                              x: 0,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 5,
                                          taken       : true,
                                          id         : 6
                                      ],
                                      [
                                          type       : "BATTLESHIP",
                                          placement  : [
                                              x: 1,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 4,
                                          taken       : true,
                                          id         : 7
                                      ],
                                      [
                                          type       : "SUBMARINE",
                                          placement  : [
                                              x: 2,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 3,
                                          taken       : true,
                                          id         : 8
                                      ]],
                     moves         : [[
                                          point : [
                                              x: 0,
                                              y: 4
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 3
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 3
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ]],
                     opponentMoves : [[
                                          point : [
                                              x: 4,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 4,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 3,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 3,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 3,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ]],
                     winner        : false
                 ],
                 [
                     id            : 2,
                     pieces        : [[
                                          type       : "AIRCRAFT_CARRIER",
                                          placement  : [
                                              x: 0,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 5,
                                          taken       : true,
                                          id         : 6
                                      ],
                                      [
                                          type       : "BATTLESHIP",
                                          placement  : [
                                              x: 1,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 4,
                                          taken       : true,
                                          id         : 7
                                      ],
                                      [
                                          type       : "SUBMARINE",
                                          placement  : [
                                              x: 2,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 3,
                                          taken       : true,
                                          id         : 8
                                      ],
                                      [
                                          type       : "CRUISER",
                                          placement  : [
                                              x: 3,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 3,
                                          taken       : false,
                                          id         : 9
                                      ],
                                      [
                                          type       : "DESTROYER",
                                          placement  : [
                                              x: 4,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 2,
                                          size       : 2,
                                          taken       : false,
                                          id         : 10
                                      ]],
                     opponentPieces: [[
                                          type       : 'DESTROYER',
                                          placement  : [
                                              x: 4,
                                              y: 0
                                          ],
                                          orientation: 'DOWN',
                                          boardId    : 1,
                                          size       : 2,
                                          taken       : true,
                                          id         : 5
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
                                          taken       : true,
                                          id         : 4
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
                                          taken       : true,
                                          id         : 3
                                      ]],
                     moves         : [[
                                          point : [
                                              x: 4,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 4,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 3,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 3,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 3,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ]],
                     opponentMoves : [[
                                          point : [
                                              x: 0,
                                              y: 4
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 3
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 0,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 1,
                                              y: 3
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 0
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 1
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ],
                                      [
                                          point : [
                                              x: 2,
                                              y: 2
                                          ],
                                          id    : $(regex(number())),
                                          status: 'HIT'
                                      ]],
                     winner        : false
                 ]],
        id    : 1,
        turn  : 2,
        over  : false
    ])
  }
}