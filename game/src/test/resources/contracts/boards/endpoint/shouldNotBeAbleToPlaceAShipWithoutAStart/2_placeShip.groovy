package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipWithoutAStart

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type: 'AIRCRAFT_CARRIER',
        end : [
            x: 0,
            y: 1
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
                     fieldValidations: [[
                                            code   : 'NonEmpty',
                                            field  : 'start',
                                            value  : null,
                                            message: 'Cannot be empty or null.'
                                        ]]
                 ]]
    ])
  }
}