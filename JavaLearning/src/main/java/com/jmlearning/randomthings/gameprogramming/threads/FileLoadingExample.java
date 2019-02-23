package com.jmlearning.randomthings.gamingprogramming.threads;

import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class FileLoadingExample extends SimpleFramework {

    private static final int NUMBER_OF_FILES = 100;
    private ExecutorService singleThread;
    private ExecutorService thirtyTwoThreads;
    private ExecutorService unlimitedThreads;
    private boolean loading = false;
    private List<Callable<Boolean>> fileTasks;
    private List<Future<Boolean>> fileResults;

    public FileLoadingExample() {

        appWidth = 640;
        appHeight = 640;
        appSleep = 1L;
        appTitle = "File Loading Example";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    @Override
    protected void initialize() {

        super.initialize();

        singleThread = Executors.newSingleThreadExecutor();
        thirtyTwoThreads = Executors.newFixedThreadPool(32);
        unlimitedThreads = Executors.newCachedThreadPool();
        fileTasks = new ArrayList <>();

        for(int i = 0; i < NUMBER_OF_FILES; ++i) {

            final int taskNumber = i;
            fileTasks.add(() -> {

                try {

                    Thread.sleep(new Random().nextInt(750));
                    System.out.println("Task: " + taskNumber);
                }
                catch(InterruptedException ex) {

                }

                return Boolean.TRUE;
            });
        }

        fileResults = new ArrayList <>();
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        if(keyboard.keyDownOnce(KeyEvent.VK_1)) {

            if(!loading) {

                for(Callable<Boolean> task : fileTasks) {

                    fileResults.add(singleThread.submit(task));
                }
            }
        }

        if(keyboard.keyDownOnce(KeyEvent.VK_2)) {

            if(!loading) {

                for(Callable<Boolean> task : fileTasks) {

                    fileResults.add(thirtyTwoThreads.submit(task));
                }
            }
        }

        if(keyboard.keyDownOnce(KeyEvent.VK_3)) {

            if(!loading) {

                for(Callable<Boolean> task : fileTasks) {

                    fileResults.add(unlimitedThreads.submit(task));
                }
            }
        }
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        Iterator<Future<Boolean>> it = fileResults.iterator();

        while(it.hasNext()) {

            Future<Boolean> next = it.next();

            if(next.isDone()) {

                try {

                    if(next.get()) {

                        it.remove();
                    }
                }
                catch(ExecutionException | InterruptedException ex) {

                    ex.printStackTrace();
                }
            }
        }

        loading = !fileResults.isEmpty();
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        textPos = Utility.drawString(g, 20, textPos, "",
                "Press the Number Keys to start loading files",
                "(1) One Thread",
                "(2) Thirty Two Threads",
                "(3) Unlimited Threads", "");
        double percentComplete = (NUMBER_OF_FILES - fileResults.size())
                / (double) NUMBER_OF_FILES;
        String fileProgress = String.format("File Progress: %.0f%%",
                100.0 * percentComplete);
        textPos = Utility.drawString(g, 20, textPos, fileProgress);
    }

    @Override
    protected void terminate() {

        super.terminate();

        shutdownExecutor(singleThread);
        shutdownExecutor(thirtyTwoThreads);
        shutdownExecutor(unlimitedThreads);
    }

    private void shutdownExecutor(ExecutorService es) {

        try {

            es.shutdown();
            es.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println("Executor Shutdown");
        }
        catch(InterruptedException e) {

        }
    }

    public static void main(String[] args) {

        launchApp(new FileLoadingExample());
    }
}
