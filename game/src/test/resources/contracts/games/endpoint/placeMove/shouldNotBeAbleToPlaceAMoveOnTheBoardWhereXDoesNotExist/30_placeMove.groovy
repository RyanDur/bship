package contracts.games.endpoint.placeMove.shouldNotBeAbleToPlaceAMoveOnTheBoardWhereXDoesNotExist

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
            y: 5
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
                                           x: null,
                                           y: 5
                                       ],
                                       message: 'Cannot be empty or null.'
                                   ]]
                 ]]
    ])
  }
}