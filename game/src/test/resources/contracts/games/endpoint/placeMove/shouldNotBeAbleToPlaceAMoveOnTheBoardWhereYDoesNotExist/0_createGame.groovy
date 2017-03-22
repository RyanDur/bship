package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardWhereYDoesNotExist

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'POST'
    urlPath '/games'
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
                                          size       : 5,
                                          sunk       : false,
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
                                          size       : 4,
                                          sunk       : false,
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
                                          size       : 3,
                                          sunk       : false,
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
                                          size       : 3,
                                          sunk       : false,
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
                                          size       : 2,
                                          sunk       : false,
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
                                          size       : 5,
                                          sunk       : false,
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
                                          size       : 4,
                                          sunk       : false,
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
                                          size       : 3,
                                          sunk       : false,
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
                                          size       : 3,
                                          sunk       : false,
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
                                          size       : 2,
                                          sunk       : false,
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