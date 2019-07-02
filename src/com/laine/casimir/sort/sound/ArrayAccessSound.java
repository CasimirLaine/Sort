package com.laine.casimir.sort.sound;

import javax.sound.sampled.SourceDataLine;

public class ArrayAccessSound extends SortingSound {

    private final byte[] soundBuffer = new byte[1];

    @Override
    protected void play(SourceDataLine sourceDataLine) {
        for (int index = (int) (100 * KILOHERTZ); index < 200 * KILOHERTZ; index += 1) {
            double angle = index / (KILOHERTZ) * 2 * Math.PI;
            soundBuffer[0] = (byte) (Math.sin(angle) * 100);
            sourceDataLine.write(soundBuffer, 0, soundBuffer.length);
        }
    }
}
