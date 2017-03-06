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
        x: 5
    ])
  }
  response {
    status 400
    headers {
      contentType(applicationJson())
    }
    body([
        errors: [[
                     validations: [[
                                       code   : 'BoundsCheck',
                                       type   : 'point',
                                       message: 'out of bounds.'
                                   ]]
                 ]]
    ])
  }
}