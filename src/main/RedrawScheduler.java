package main;

public class RedrawScheduler implements Runnable {

    private final Gui GUI;

    private final long INTERVAL;

    RedrawScheduler(Gui gui, long interval) {
        GUI = gui;
        INTERVAL = interval;
    }

    @Override
    public void run() {
        for(;;) {
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                throw new IllegalStateException("InterruptedException " + e + " in RedrawScheduler.run()");
            }
            GUI.redraw();
        }
    }
}
