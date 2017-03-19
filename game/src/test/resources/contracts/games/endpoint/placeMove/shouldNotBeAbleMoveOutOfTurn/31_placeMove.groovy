package contracts.games.endpoint.placeMove.shouldNotBeAbleMoveOutOfTurn

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/games/1'
    headers {
      contentType(applicationJson())
    }
    body([
        boardId: 1,
        point  : [
            x: 0,
            y: 6
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
                                       code   : 'TurnCheck',
                                       type   : 'game',
                                       message: 'It is not your turn.'
                                   ]]
                 ]]
    ])
  }
}