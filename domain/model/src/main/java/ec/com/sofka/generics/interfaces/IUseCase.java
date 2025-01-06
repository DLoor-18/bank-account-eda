package ec.com.sofka.generics.interfaces;

import ec.com.sofka.generics.shared.Request;
import org.reactivestreams.Publisher;

public interface IUseCase<T extends Request, R> {
    Publisher<R> execute(T request);
}