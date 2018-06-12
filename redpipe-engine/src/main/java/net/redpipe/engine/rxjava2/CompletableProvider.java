package net.redpipe.engine.rxjava2;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import org.jboss.resteasy.spi.AsyncResponseProvider;

import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableProvider implements AsyncResponseProvider<Completable> {

    private static class CompletableAdaptor extends CompletableFuture<Response>
    {
        private Disposable subscription;

        public CompletableAdaptor(Completable observable)
        {
            this.subscription = observable.subscribe(this::completeEmpty, this::completeExceptionally);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning)
        {
            subscription.dispose();
            return super.cancel(mayInterruptIfRunning);
        }

        void completeEmpty() {
            super.complete(Response.status(204).build());
        }

    }

    @Override
    public CompletionStage<?> toCompletionStage(Completable asyncResponse) {
        return new CompletableAdaptor(asyncResponse);
    }

}
