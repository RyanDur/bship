package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipsStartOutsideTheBoard

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
                                          type       : [name: 'AIRCRAFT_CARRIER', size: 5],
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
                                          type       : [name: 'BATTLESHIP', size: 4],
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
                                          type       : [name: 'SUBMARINE', size: 3],
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
                                          type       : [name: 'CRUISER', size: 3],
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
                                          type       : [name: 'DESTROYER', size: 2],
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
                                          type       : [name: 'AIRCRAFT_CARRIER', size: 5],
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
                                          type       : [name: 'BATTLESHIP', size: 4],
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
                                          type       : [name: 'SUBMARINE', size: 3],
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
                                          type       : [name: 'CRUISER', size: 3],
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
                                          type       : [name: 'DESTROYER', size: 2],
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
        turn  : null
    ])
  }
}