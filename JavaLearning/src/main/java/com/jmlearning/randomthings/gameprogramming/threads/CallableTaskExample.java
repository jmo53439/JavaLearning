package com.jmlearning.randomthings.gamingprogramming.threads;

import java.util.Random;
import java.util.concurrent.*;

public class CallableTaskExample implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {

        Random random = new Random();
        int seconds = random.nextInt(6);

        if(seconds == 0) {

            throw new RuntimeException("Um hello??");
        }

        try {

            Thread.sleep(seconds * 100);
        }
        catch(InterruptedException e) {

        }

        return seconds % 2 == 0;
    }

    public static void main(String[] args) {

        ExecutorService es = Executors.newCachedThreadPool();

        try {

            for(int i = 0; i < 50; ++i) {

                try {

                    Future<Boolean> result = es.submit(new CallableTaskExample());
                    Boolean success = result.get();
                    System.out.println("Result: " + success);
                }
                catch(ExecutionException ex) {

                    Throwable throwable = ex.getCause();
                    System.out.println("Error: " + throwable.getMessage());
                }
                catch(InterruptedException e) {

                    System.out.println("Thread Cancelled");
                    e.printStackTrace();
                }
            }
        }
        finally {

            try {

                es.shutdown();
                es.awaitTermination(10, TimeUnit.SECONDS);
                System.out.println("Thread Pool Shutdown");
            }
            catch(InterruptedException e) {

                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
