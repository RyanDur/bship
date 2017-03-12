package contracts.games.endpoint

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
    body([id    : 1,
          boards: [[
                       id            : 1,
                       ships         : [
                           [
                               id   : 1,
                               type : "AIRCRAFT_CARRIER",
                               size : 5,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 2,
                               type : "BATTLESHIP",
                               size : 4,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 3,
                               type : "SUBMARINE",
                               size : 3,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 4,
                               type : "CRUISER",
                               size : 3,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 5,
                               type : "DESTROYER",
                               size : 2,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ]
                       ],
                       opponentsShips: [],
                       moves         : [],
                       opponentsMoves: []
                   ],
                   [
                       id            : 2,
                       ships         : [
                           [
                               id   : 6,
                               type : "AIRCRAFT_CARRIER",
                               size : 5,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 7,
                               type : "BATTLESHIP",
                               size : 4,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 8,
                               type : "SUBMARINE",
                               size : 3,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 9,
                               type : "CRUISER",
                               size : 3,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ], [
                               id   : 10,
                               type : "DESTROYER",
                               size : 2,
                               start: [x: null, y: null],
                               end  : [x: null, y: 1],
                               sunk : false
                           ]
                       ],
                       opponentsShips: [],
                       moves         : [],
                       opponentsMoves: []
                   ]
          ]])
  }
}
