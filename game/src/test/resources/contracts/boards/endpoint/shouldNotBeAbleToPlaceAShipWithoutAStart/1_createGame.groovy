package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipWithoutAStart

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
                     id           : 1,
                     pieces        : [[
                                         type   : "AIRCRAFT_CARRIER",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 1,
                                         size   : 5,
                                         sunk   : false,
                                         id     : 1
                                     ],
                                     [
                                         type   : "BATTLESHIP",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 1,
                                         size   : 4,
                                         sunk   : false,
                                         id     : 2
                                     ],
                                     [
                                         type   : "SUBMARINE",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 1,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 3
                                     ],
                                     [
                                         type   : "CRUISER",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 1,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 4
                                     ],
                                     [
                                         type   : "DESTROYER",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 1,
                                         size   : 2,
                                         sunk   : false,
                                         id     : 5
                                     ]],
                     opponentPieces: [],
                     moves        : [],
                     opponentMoves: [],
                     winner       : false
                 ],
                 [
                     id           : 2,
                     pieces        : [[
                                         type   : "AIRCRAFT_CARRIER",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 2,
                                         size   : 5,
                                         sunk   : false,
                                         id     : 6
                                     ],
                                     [
                                         type   : "BATTLESHIP",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 2,
                                         size   : 4,
                                         sunk   : false,
                                         id     : 7
                                     ],
                                     [
                                         type   : "SUBMARINE",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 2,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 8
                                     ],
                                     [
                                         type   : "CRUISER",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 2,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 9
                                     ],
                                     [
                                         type   : "DESTROYER",
                                         start  : [
                                             x: null,
                                             y: null
                                         ],
                                         end    : [
                                             x: null,
                                             y: null
                                         ],
                                         boardId: 2,
                                         size   : 2,
                                         sunk   : false,
                                         id     : 10
                                     ]],
                     opponentPieces: [],
                     moves        : [],
                     opponentMoves: [],
                     winner       : false
                 ]],
        id    : 1,
        turn  : null
    ])
  }
}