package com.laine.casimir.sort.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SortingSound {

    private final Object LOCK = new Object();

    private final byte[] soundBuffer = new byte[1];
    private SourceDataLine sourceDataLine;

    public void playSound() {
        synchronized (LOCK) {
            if (sourceDataLine != null) {
                for (int index = (int) (100 * 44100.0 / 1000.0); index < 150 * 44100.0 / 1000.0; index += 2) {
                    double angle = index / (44100.0 / 440.0) * 2 * Math.PI;
                    soundBuffer[0] = (byte) (Math.sin(angle) * 100);
                    sourceDataLine.write(soundBuffer, 0, 1);
                }
                stopSound();
            }
        }
    }

    private void stopSound() {
        synchronized (LOCK) {
            if (sourceDataLine != null) {
                sourceDataLine.drain();
                sourceDataLine.flush();
            }
        }
    }

    public void createSound() {
        synchronized (LOCK) {
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

    public void destroySound() {
        synchronized (LOCK) {
            stopSound();
            if (sourceDataLine != null) {
                sourceDataLine.stop();
                sourceDataLine.close();
            }
            sourceDataLine = null;
        }
    }
}
