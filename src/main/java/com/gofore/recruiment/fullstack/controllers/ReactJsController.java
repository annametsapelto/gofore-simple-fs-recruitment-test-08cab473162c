package com.gofore.recruiment.fullstack.controllers;

import com.gofore.recruiment.fullstack.models.Owner;
import com.gofore.recruiment.fullstack.models.Question;
import com.gofore.recruiment.fullstack.models.Questions;
import com.gofore.recruiment.fullstack.models.User;
import com.gofore.recruiment.fullstack.models.Users;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reactjs")
public class ReactJsController {
    private final WebClient.Builder webclientBuilder;

    private final URI questionsUrl;

    private final String usersUrlFmt;

    public ReactJsController(
            WebClient.Builder webclientBuilder,
            @Value("${reactjs.questionsurl}") URI questionsUrl,
            @Value("${reactjs.usersurlfmt}") String usersUrlFmt
    ) {
        this.webclientBuilder = configureWebClientBuilder(webclientBuilder);
        this.questionsUrl = questionsUrl;
        this.usersUrlFmt = usersUrlFmt;
    }

    @GetMapping(value = "questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Questions> getQuestions() {
        return webclientBuilder
                .build()
                .get()
                .uri(questionsUrl)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .retrieve()
                .bodyToMono(Questions.class)
                .map(qs -> {
                    List<Question> filtered = qs.questions
                            .stream()
                            .filter(q -> !q.isAnswered)
                            .collect(Collectors.toUnmodifiableList());
                    return new Questions(filtered);
                })
                .flatMap(qs -> {
                    List<Mono<User>> us = qs.questions
                            .stream()
                            .map(q -> getUserById(q.owner.userId))
                            .collect(Collectors.toUnmodifiableList());

                    Mono<List<User>> uM = Flux.mergeSequential(us).collectList();

                    return uM.map(users -> {
                        Iterator<Question> iterQ = qs.questions.iterator();
                        Iterator<User> iterU = users.iterator();
                        ArrayList<Question> result = new ArrayList<>(qs.questions.size());
                     
                        while (iterQ.hasNext() && iterU.hasNext()) {
                            Question q = iterQ.next();
                            User u = iterU.next();

                            result.add(new Question(
                                    new Owner(q.owner.userId, q.owner.displayName, u.location, q.owner.avatar),
                                    q.title,
                                    q.link,
                                    q.isAnswered
                            ));
                        }
                        return new Questions(result);
                    });
                });
    }

    private Mono<User> getUserById(String id) {
        return webclientBuilder
                .build()
                .get()
                .uri(String.format(usersUrlFmt, id))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .retrieve()
                .bodyToMono(Users.class)
                .map(us -> us.users.isEmpty() ? null : us.users.get(0));
    }

    private static WebClient.Builder configureWebClientBuilder(WebClient.Builder webclientBuilder) {
        HttpClient clientImpl = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE).compress(true);
        webclientBuilder.clientConnector(new ReactorClientHttpConnector(clientImpl));
        return webclientBuilder;
    }
}
