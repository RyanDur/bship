package contracts.boards.endpoint

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/games'
    }
    response {
        status 201
        body([id    : 1,
              boards: [{
                           id: 1
                           ships: []
                           moves: []
                       },
                       {
                           id: 2
                           ships: []
                           moves: []
                       }
              ]])
        headers {
            header('Content-Type': value(
                    producer('application/json;charset=UTF-8'),
                    consumer('application/json')
            ))
        }
    }
}