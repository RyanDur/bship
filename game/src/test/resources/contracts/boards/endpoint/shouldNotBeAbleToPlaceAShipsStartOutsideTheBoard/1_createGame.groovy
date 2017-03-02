package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipsStartOutsideTheBoard

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
                       id   : 1,
                       ships: [],
                       moves: []
                   ],
                   [
                       id   : 2,
                       ships: [],
                       moves: []
                   ]
          ]])
  }
}