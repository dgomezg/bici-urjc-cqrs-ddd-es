package dev.dgomezg.urjc.biciurjc;

import dev.dgomezg.urjc.biciurjc.coreapi.Top10UsersQuery;
import dev.dgomezg.urjc.biciurjc.coreapi.UserRankingQuery;
import dev.dgomezg.urjc.biciurjc.query.user.ranking.UserRanking;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;


@RestController
@RequestMapping("/ranking")
public class UserRankingController {

    private final QueryGateway queryGateway;

    public UserRankingController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    
    @GetMapping
    public Future<List<UserRanking>> top10() {
        return queryGateway.query(new Top10UsersQuery(), ResponseTypes.multipleInstancesOf(UserRanking.class));
    }

    @GetMapping("/{userId}")
    public Future<UserRanking> getUserRanking (@PathVariable String userId){
        return queryGateway.query(new UserRankingQuery(userId), ResponseTypes.instanceOf(UserRanking.class));
    }
}
