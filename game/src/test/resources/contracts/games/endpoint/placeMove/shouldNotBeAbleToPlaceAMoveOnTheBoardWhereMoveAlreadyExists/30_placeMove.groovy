package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardPastLowerBounds

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/games/1/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        x: 0,
        y: 5
    ])
  }
  response {
    status 200
    headers {
      contentType(applicationJson())
    }
    body([
        id   : 1,
        ships: [],
        moves: [[
                    point : [
                        x: 0,
                        y: 5
                    ],
                    status: 'MISS'
                ]]
    ])
  }
}