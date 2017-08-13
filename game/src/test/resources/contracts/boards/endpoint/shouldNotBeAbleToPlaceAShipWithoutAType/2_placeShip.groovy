package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipWithoutAType

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([[
              id         : 1,
              placement  : [
                  x: 0,
                  y: 0
              ],
              orientation: 'DOWN'
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
                                       code   : "PieceTypeExistenceCheck",
                                       field  : "pieces",
                                       value  : [[
                                                     type       : null,
                                                     placement  : [
                                                         x: 0,
                                                         y: 0
                                                     ],
                                                     orientation: "DOWN",
                                                     id         : 1,
                                                     boardId    : null,
                                                     taken      : false
                                                 ]],
                                       message: "Missing piece type."
                                   ]]
                 ]]
    ])
  }
}