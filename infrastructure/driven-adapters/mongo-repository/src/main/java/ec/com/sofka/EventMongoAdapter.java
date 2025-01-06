package ec.com.sofka;

import ec.com.sofka.data.EventEntity;
import ec.com.sofka.database.events.IEventMongoRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
public class EventMongoAdapter implements IEventStore {

    private final IEventMongoRepository repository;
    private final JSONMap mapper;
    private final ReactiveMongoTemplate eventReactiveMongoTemplate;

    public EventMongoAdapter(IEventMongoRepository repository, JSONMap mapper, @Qualifier("eventReactiveMongoTemplate")ReactiveMongoTemplate eventReactiveMongoTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventReactiveMongoTemplate = eventReactiveMongoTemplate;
    }

    @Override
    public Mono<DomainEvent> save(DomainEvent event) {
        return Mono.fromCallable(() -> {
                    String eventData = mapper.writeToJson(event);
                    return new EventEntity(
                            event.getEventId(),
                            event.getAggregateRootId(),
                            event.getEventType(),
                            eventData,
                            event.getWhen().toString(),
                            event.getVersion()
                    );
                })
                .flatMap(repository::save)
                .thenReturn(event);
    }

    @Override
    public Flux<DomainEvent> findAggregate(String aggregateId) {
        return repository.findByAggregateId(aggregateId)
                .flatMap(eventEntity -> Mono.fromCallable(() ->
                        eventEntity.deserializeEvent(mapper)
                ).subscribeOn(Schedulers.boundedElastic()));
    }

}