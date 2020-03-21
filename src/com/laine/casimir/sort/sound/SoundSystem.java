package com.laine.casimir.sort.sound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class SoundSystem {

    private Map<String, SortingSound> soundMap = new HashMap<>();

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<String, Future<?>> soundTasks = new HashMap<>();

    private boolean soundEnabled = true;

    public void mapSound(String key, SortingSound sortingSound) {
        if (sortingSound == null) {
            return;
        }
        soundMap.put(key, sortingSound);
        if (soundEnabled && !sortingSound.isAlive()) {
            sortingSound.createSound();
        }
    }

    public void playSound(String key) {
        playSound(key, null);
    }

    public void playSound(String key, Runnable afterPlay) {
        if (soundEnabled) {
            final Future<?> currentTask = soundTasks.get(key);
            if (currentTask == null || currentTask.isCancelled() || currentTask.isDone()) {
                final SortingSound sortingSound = soundMap.get(key);
                soundTasks.put(key, executorService.submit(() -> {
                    sortingSound.playSound();
                    if (afterPlay != null) {
                        afterPlay.run();
                    }
                }));
            }
        }
    }

    public void stopAllSounds() {
        for (Map.Entry<String, SortingSound> soundEntry : soundMap.entrySet()) {
            final SortingSound sortingSound = soundEntry.getValue();
            sortingSound.stopSound();
        }
        for (Map.Entry<String, Future<?>> taskEntry : soundTasks.entrySet()) {
            final Future<?> task = taskEntry.getValue();
            if (task != null) {
                task.cancel(true);
            }
        }
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
        if (!soundEnabled) {
            stopAllSounds();
        }
        for (Map.Entry<String, SortingSound> soundEntry : soundMap.entrySet()) {
            final SortingSound sortingSound = soundEntry.getValue();
            if (!soundEnabled) {
                sortingSound.destroySound();
            } else if (!sortingSound.isAlive()) {
                sortingSound.createSound();
            }
        }
    }
}
