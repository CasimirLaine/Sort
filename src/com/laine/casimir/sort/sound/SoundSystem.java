package com.laine.casimir.sort.sound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class SoundSystem {

    private final Map<String, AbstractSortingSound> soundMap = new HashMap<>();

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Map<String, Future<?>> soundTasks = new HashMap<>();

    private boolean soundEnabled = true;

    public void mapSound(String key, AbstractSortingSound sortingSound) {
        if (sortingSound == null) {
            return;
        }
        soundMap.put(key, sortingSound);
        if (soundEnabled && sortingSound.isDead()) {
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
                final AbstractSortingSound sortingSound = soundMap.get(key);
                soundTasks.put(key, executorService.submit(() -> {
                    sortingSound.playSound();
                    if (afterPlay != null) {
                        afterPlay.run();
                    }
                }));
            }
        }
    }

    public void createAllSounds() {
        for (Map.Entry<String, AbstractSortingSound> soundEntry : soundMap.entrySet()) {
            final AbstractSortingSound sortingSound = soundEntry.getValue();
            if (sortingSound.isDead()) {
                sortingSound.createSound();
            }
        }
    }

    public void stopAllSounds() {
        for (Map.Entry<String, AbstractSortingSound> soundEntry : soundMap.entrySet()) {
            final AbstractSortingSound sortingSound = soundEntry.getValue();
            sortingSound.stopSound();
            sortingSound.destroySound();
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
        if (soundEnabled) {
            createAllSounds();
        } else {
            stopAllSounds();
        }
    }
}
