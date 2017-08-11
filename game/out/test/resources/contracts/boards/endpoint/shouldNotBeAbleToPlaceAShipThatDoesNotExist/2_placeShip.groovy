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
        type       : 'SCHOONER',
        id         : 1,
        placement  : [
            x: 0,
            y: 0
        ],
        orientation: 'RIGHT',
        size       : 1
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
                 validations:
                     [[
                          code   : 'ShipExists',
                          field  : 'type',
                          value  : [name: 'INVALID_SHIP'],
                          message: 'Ship does not exist.'
                      ]]
             ]]
    ])
  }
}
