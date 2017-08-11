package contracts.games.endpoint.placeMove.shouldNotFindGameAfterItIsOver

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
            x: 0,
            y: 3
        ]])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([
        boards: [[
                     id           : 1,
                     pieces       : [[
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
                     moves        : [[
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
                                     ]],
                     opponentMoves: [[
                                         point : [
                                             x: 4,
                                             y: 0
                                         ],
                                         id    : $(regex(number())),
                                         status: 'HIT'
                                     ]],
                     winner       : false
                 ],
                 [
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
                     moves         : [[
                                          point : [
                                              x: 4,
                                              y: 0
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
                                      ]],
                     winner        : false
                 ]],
        id    : 1,
        turn  : 2,
        over  : false
    ])
  }
}