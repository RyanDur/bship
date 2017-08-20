package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipWithoutAStart

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
        orientation: 'DOWN',
        size       : 5
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
                                       code : "PlacementExistenceCheck",
                                       field : "pieces",
                                       value : [[
                                                type: "AIRCRAFT_CARRIER",
                                                placement: [
                                                  x: null,
                                                  y: null
                                                ],
                                                orientation: "DOWN",
                                                id: 1,
                                                boardId: null,
                                                taken: false
                                              ]],
                                     message: "Missing placement."
                                   ]]
                 ]]
    ])
  }
}