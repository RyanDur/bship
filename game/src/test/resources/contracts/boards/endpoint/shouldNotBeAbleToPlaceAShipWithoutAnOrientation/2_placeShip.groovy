package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipWithoutAnOrientation

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([[
              type     : 'AIRCRAFT_CARRIER',
              id       : 1,
              placement: [
                  x: 0,
                  y: 1
              ],
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
                                       code   : "OrientationExistenceCheck",
                                       field  : "pieces",
                                       value  : [[
                                                     type       : "AIRCRAFT_CARRIER",
                                                     placement  : [
                                                         x: 0,
                                                         y: 1
                                                     ],
                                                     orientation: null,
                                                     id         : 1,
                                                     boardId    : null,
                                                     taken      : false
                                                 ]],
                                       message: "Missing orientation."
                                   ]]
                 ]]
    ])
  }
}