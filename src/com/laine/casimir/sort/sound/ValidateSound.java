package com.laine.casimir.sort.sound;

import javax.sound.sampled.SourceDataLine;

public class ValidateSound extends AbstractSortingSound {

    private static final int SOUND_START = 100;
    private static final int SOUND_END = 200;
    private static final float ANGLE_MULTIPLIER = 3;

    private final byte[] soundBuffer = new byte[1];

    @Override
    protected void play(SourceDataLine sourceDataLine) {
        for (int index = (int) (SOUND_START * KILOHERTZ); index < SOUND_END * KILOHERTZ; index += 1) {
            double angle = index / KILOHERTZ * ANGLE_MULTIPLIER * Math.PI;
            soundBuffer[0] = (byte) (Math.sin(angle) * SOUND_START);
            sourceDataLine.write(soundBuffer, 0, soundBuffer.length);
        }
    }
}
