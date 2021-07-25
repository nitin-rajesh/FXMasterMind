package sample.ThreadQueue;
import java.util.concurrent.*;

public class OrderlyThreads {
    BlockingQueue<Runnable> runnableBlockingQueue;
    Thread queueRunner;
    public OrderlyThreads(){
        runnableBlockingQueue = new LinkedBlockingQueue<>();
    }

    public void AddThread(Runnable runnableTask) throws InterruptedException {
        //System.out.println("Thread added");
        runnableBlockingQueue.put(runnableTask);
    }

    public void initQueue() {
        queueRunner = new Thread(() -> {
            Thread runner;
            while(true){
                try {
                    runner = new Thread(runnableBlockingQueue.take());
                    runner.start();
                    runner.join();
                    Thread.sleep(200);
                } catch (InterruptedException ignored) { }
            }
        });
        queueRunner.setDaemon(true);
        queueRunner.start();
    }

    public void stopThreads(){
        try{
        queueRunner.interrupt();
        }catch (Exception ignored){}
    }
}
