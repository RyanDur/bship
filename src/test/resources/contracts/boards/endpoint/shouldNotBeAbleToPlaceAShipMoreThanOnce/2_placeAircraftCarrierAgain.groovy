package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipMoreThanOnce

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'AIRCRAFT_CARRIER',
        start: [
            x: 9,
            y: 0
        ],
        end  : [
            x: 9,
            y: 4
        ]
    ])
  }
  response {
    status 400
    headers {
      contentType(applicationJson())
    }
    body([
        errors: [[
            objectValidations: [[
                                    code   : 'ShipExistsCheck',
                                    type   : 'board',
                                    message: 'Ship already exists on board.'
                                ]]
        ]]
    ])
  }
}

