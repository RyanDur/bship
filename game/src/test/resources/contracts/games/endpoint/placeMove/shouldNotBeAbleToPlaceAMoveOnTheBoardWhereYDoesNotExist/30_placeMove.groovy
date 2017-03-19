package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardWhereYDoesNotExist

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
            x: 5
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
                                       code   : 'ValidPoint',
                                       field  : 'point',
                                       value  : [
                                           x: 5,
                                           y: null
                                       ],
                                       message: 'Cannot be empty or null.'
                                   ]]
                 ]]
    ])
  }
}