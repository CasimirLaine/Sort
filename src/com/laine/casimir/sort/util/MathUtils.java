package com.laine.casimir.sort.util;

public final class MathUtils {

    private MathUtils() {}

    public static double getExtraMod(int length, int widthAvailable) {
        final double mod;
        final double maxWidthOfColumn = (double) widthAvailable / (double) length;
        final double surplusWidth = maxWidthOfColumn - Math.floor(maxWidthOfColumn);
        final double amountOfExtraPixelColumns = Math.floor(surplusWidth * length);
        if (amountOfExtraPixelColumns != 0) {
            mod = ((double) length / amountOfExtraPixelColumns);
        } else {
            mod = 0;
        }
        return mod;
    }

    public static double getSkipModulo(int length, double widthAvailable) {
        final double requiredOmissions = length - widthAvailable;
        if (requiredOmissions <= 0) {
            return 0;
        }
        return (double) length / requiredOmissions;
    }
}
