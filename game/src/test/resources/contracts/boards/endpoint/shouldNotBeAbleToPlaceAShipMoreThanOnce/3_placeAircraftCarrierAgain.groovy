package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipMoreThanOnce

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
            x: 9,
            y: 0
        ],
        orientation: 'LEFT',
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
                                       code   : 'ShipExistsCheck',
                                       type   : 'board',
                                       message: 'Ship already exists on board.'
                                   ]]
                 ]]
    ])
  }
}

