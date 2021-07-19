package sample.ThreadQueue;
import java.util.concurrent.*;

public class OrderlyThreads {
    ExecutorService QueueOfThreads;
    OrderlyThreads(){
        QueueOfThreads = new ThreadPoolExecutor(1,1024,0L,TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    void AddThread(Runnable runnableTask){

    }
}
