package contracts.games.endpoint

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'GET'
    urlPath '/games'
    headers {
      contentType(applicationJson())
    }
  }
  response {
    status 200
    body([[
              name             : 'BATTLESHIP',
              numberOfPlayers  : 2,
              movesPerTurn     : 1,
              boardDimensions  : [
                  width : 10,
                  height: 10
              ],
              pieces           : [[
                                      size: 5,
                                      name: 'AIRCRAFT_CARRIER'
                                  ],
                                  [
                                      size: 4,
                                      name: 'BATTLESHIP'
                                  ],
                                  [
                                      size: 3,
                                      name: 'SUBMARINE'
                                  ],
                                  [
                                      size: 3,
                                      name: 'CRUISER'
                                  ],
                                  [
                                      size: 2,
                                      name: 'DESTROYER'
                                  ]],
              pieceOrientations: [
                  'LEFT',
                  'RIGHT',
                  'UP',
                  'DOWN'
              ]
          ]])
  }
}

