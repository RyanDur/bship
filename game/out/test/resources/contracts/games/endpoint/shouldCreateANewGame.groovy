package contracts.games.endpoint

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'POST'
    urlPath '/games/BATTLESHIP'
    headers {
      contentType(applicationJson())
    }
  }
  response {
    status 201
    headers {
      contentType(applicationJson())
    }
    body([
        boards: [[
                     id            : 1,
                     pieces        : [[
                                          type       : 'AIRCRAFT_CARRIER',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 1,
                                          taken      : false,
                                          id         : 1
                                      ],
                                      [
                                          type       : 'BATTLESHIP',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 1,
                                          taken      : false,
                                          id         : 2
                                      ],
                                      [
                                          type       : 'SUBMARINE',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 1,
                                          taken      : false,
                                          id         : 3
                                      ],
                                      [
                                          type       : 'CRUISER',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 1,
                                          taken      : false,
                                          id         : 4
                                      ],
                                      [
                                          type       : 'DESTROYER',
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
                 ],
                 [
                     id            : 2,
                     pieces        : [[
                                          type       : 'AIRCRAFT_CARRIER',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 2,
                                          taken      : false,
                                          id         : 6
                                      ],
                                      [
                                          type       : 'BATTLESHIP',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 2,
                                          taken      : false,
                                          id         : 7
                                      ],
                                      [
                                          type       : 'SUBMARINE',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 2,
                                          taken      : false,
                                          id         : 8
                                      ],
                                      [
                                          type       : 'CRUISER',
                                          placement  : [
                                              x: null,
                                              y: null
                                          ],
                                          orientation: 'NONE',
                                          boardId    : 2,
                                          taken      : false,
                                          id         : 9
                                      ],
                                      [
                                          type       : 'DESTROYER',
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
                 ]],
        id    : 1,
        turn  : null,
        over  : false,
        rules : [
            numberOfPlayers  : 2,
            movesPerTurn     : 1,
            boardDimensions  : [
                width : 10,
                height: 10
            ],
            pieces           : [
                [name: "AIRCRAFT_CARRIER", size: 5],
                [name: "BATTLESHIP", size: 4],
                [name: "SUBMARINE", size: 3],
                [name: "CRUISER", size: 3],
                [name: "DESTROYER", size: 2]
            ],
            pieceOrientations: [
                'UP',
                'DOWN',
                'LEFT',
                'RIGHT'
            ]
        ]
    ])
  }
}