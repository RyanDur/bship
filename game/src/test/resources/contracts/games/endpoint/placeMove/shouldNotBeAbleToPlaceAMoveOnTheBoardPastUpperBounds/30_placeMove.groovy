package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardPastUpperBounds

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
            y: 15
        ]])
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
                                       field  : 'point',
                                       value  : [
                                           x: 0,
                                           y: 15
                                       ],
                                       message: 'out of bounds.'
                                   ]]
                 ]]
    ])
  }
}