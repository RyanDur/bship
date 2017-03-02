package contracts.boards.endpoint.shouldNotBeAbleToPlaceAShipThatDoesNotExist

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'PUT'
    urlPath '/boards/1'
    headers {
      contentType(applicationJson())
    }
    body([
        type : 'SCHOONER',
        start: [
            x: 0,
            y: 0
        ],
        end  : [
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
        errors:
            [[
                 fieldValidations:
                     [[
                          code   : 'ShipExists',
                          field  : 'type',
                          value  : 'INVALID_SHIP',
                          message: 'Ship does not exist.'
                      ]]
             ]]
    ])
  }
}
