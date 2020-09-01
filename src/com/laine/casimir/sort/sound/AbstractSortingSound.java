package com.laine.casimir.sort.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public abstract class AbstractSortingSound {

    public static final String GET = "get";
    public static final String VALIDATE = "validate";

    protected static final double KILOHERTZ = 44.1;

    private final Object lock = new Object();

    private SourceDataLine sourceDataLine;

    public final void playSound() {
        synchronized (lock) {
            if (isDead()) {
                System.out.println(getClass() + ": Error: Attempting to play sound before creating it!");
                return;
            }
            play(sourceDataLine);
            stopSound();
        }
    }

    protected abstract void play(SourceDataLine sourceDataLine);

    public final void stopSound() {
        synchronized (lock) {
            if (sourceDataLine != null) {
                sourceDataLine.drain();
                sourceDataLine.flush();
            }
        }
    }

    public final void createSound() {
        synchronized (lock) {
            destroySound();
            final AudioFormat audioFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100F,
                    8,
                    1,
                    1,
                    44100F,
                    false
            );
            try {
                sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
                sourceDataLine.open();
                sourceDataLine.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public final void destroySound() {
        synchronized (lock) {
            stopSound();
            if (sourceDataLine != null) {
                sourceDataLine.stop();
                sourceDataLine.close();
            }
            sourceDataLine = null;
        }
    }

    public final boolean isDead() {
        return sourceDataLine == null;
    }
}
