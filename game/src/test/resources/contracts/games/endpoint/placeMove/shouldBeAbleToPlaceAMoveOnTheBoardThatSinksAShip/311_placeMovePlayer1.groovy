package contracts.games.endpoint.placeMove.shouldBeAbleToPlaceAMoveOnTheBoardThatSinksAShip

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PATCH'
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
                     ships        : [[
                                         type   : 'AIRCRAFT_CARRIER',
                                         start  : [
                                             x: 0,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 0,
                                             y: 4
                                         ],
                                         boardId: 1,
                                         size   : 5,
                                         sunk   : false,
                                         id     : 1
                                     ],
                                     [
                                         type   : 'BATTLESHIP',
                                         start  : [
                                             x: 1,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 1,
                                             y: 3
                                         ],
                                         boardId: 1,
                                         size   : 4,
                                         sunk   : false,
                                         id     : 2
                                     ],
                                     [
                                         type   : 'SUBMARINE',
                                         start  : [
                                             x: 2,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 2,
                                             y: 2
                                         ],
                                         boardId: 1,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 3
                                     ],
                                     [
                                         type   : 'CRUISER',
                                         start  : [
                                             x: 3,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 3,
                                             y: 2
                                         ],
                                         boardId: 1,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 4
                                     ],
                                     [
                                         type   : 'DESTROYER',
                                         start  : [
                                             x: 4,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 4,
                                             y: 1
                                         ],
                                         boardId: 1,
                                         size   : 2,
                                         sunk   : false,
                                         id     : 5
                                     ]],
                     opponentShips: [],
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
                     id           : 2,
                     ships        : [[
                                         type   : 'AIRCRAFT_CARRIER',
                                         start  : [
                                             x: 0,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 0,
                                             y: 4
                                         ],
                                         boardId: 2,
                                         size   : 5,
                                         sunk   : false,
                                         id     : 6
                                     ],
                                     [
                                         type   : 'BATTLESHIP',
                                         start  : [
                                             x: 1,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 1,
                                             y: 3
                                         ],
                                         boardId: 2,
                                         size   : 4,
                                         sunk   : false,
                                         id     : 7
                                     ],
                                     [
                                         type   : 'SUBMARINE',
                                         start  : [
                                             x: 2,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 2,
                                             y: 2
                                         ],
                                         boardId: 2,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 8
                                     ],
                                     [
                                         type   : 'CRUISER',
                                         start  : [
                                             x: 3,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 3,
                                             y: 2
                                         ],
                                         boardId: 2,
                                         size   : 3,
                                         sunk   : false,
                                         id     : 9
                                     ],
                                     [
                                         type   : 'DESTROYER',
                                         start  : [
                                             x: 4,
                                             y: 0
                                         ],
                                         end    : [
                                             x: 4,
                                             y: 1
                                         ],
                                         boardId: 2,
                                         size   : 2,
                                         sunk   : false,
                                         id     : 10
                                     ]],
                     opponentShips: [],
                     moves        : [[
                                         point : [
                                             x: 4,
                                             y: 0
                                         ],
                                         id    : $(regex(number())),
                                         status: 'HIT'
                                     ]],
                     opponentMoves: [[
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
                     winner       : false
                 ]],
        id    : 1,
        turn  : 2
    ])
  }
}