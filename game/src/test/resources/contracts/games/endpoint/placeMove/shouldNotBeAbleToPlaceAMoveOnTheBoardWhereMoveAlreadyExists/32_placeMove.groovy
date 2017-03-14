package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardWhereMoveAlreadyExists

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
    status 400
    headers {
      contentType(applicationJson())
    }
    body([
        errors: [[
                     validations: [[
                                       code   : 'MoveCollision',
                                       type   : 'game',
                                       message: 'Move already exists on board.'
                                   ]]
                 ]]
    ])
  }
}