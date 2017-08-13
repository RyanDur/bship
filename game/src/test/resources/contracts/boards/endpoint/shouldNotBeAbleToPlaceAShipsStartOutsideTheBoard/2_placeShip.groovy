package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipsStartOutsideTheBoard

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([[
              type       : 'AIRCRAFT_CARRIER',
              id         : 1,
              placement  : [
                  x: -1,
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
                                       code   : "PlacementOfXCheck",
                                       field  : "pieces",
                                       value  : [[
                                                     type       : "AIRCRAFT_CARRIER",
                                                     placement  : [
                                                         x: -1,
                                                         y: 0
                                                     ],
                                                     orientation: "DOWN",
                                                     id         : 1,
                                                     boardId    : null,
                                                     taken      : false
                                                 ]],
                                       message: "Incorrect placement of X axis."
                                   ]]
                 ]]
    ])
  }
}