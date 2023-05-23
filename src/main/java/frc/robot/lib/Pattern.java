// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.lib.LightSubsystem.PixelData;

@FunctionalInterface
public interface Pattern {
    public Color getColor(final PixelData data);

    public default Pattern modify(final Consumer<PixelData> consumer) {
        return (data) -> {
            consumer.accept(data);
            return getColor(data);
        };
    }

    public default Pattern shiftOverTime() {
        return (data) -> {
            final var shiftAmount = Math.floor(data.timePercent() * data.maxPosition());
            data.setPosition(data.position() + (int) shiftAmount);
            return getColor(data);
        };
    }

    public static Pattern solid(final Color color) {
        return (data) -> color;
    }

    public static Pattern gradientOverPosition(final Color startColor, final Color endColor) {
        return (data) -> {
            final var startPercent = data.positionPercent();
            final var endPercent = 1 - startPercent;
            return new Color(
                    startColor.red * startPercent + endColor.red * endPercent,
                    startColor.green * startPercent + endColor.green * endPercent,
                    startColor.blue * startPercent + endColor.blue * endPercent);
        };
    }

    public static Pattern gradientOverTime(final Color startColor, final Color endColor) {
        return (data) -> {
            final var startPercent = data.timePercent();
            final var endPercent = 1 - startPercent;
            return new Color(
                    startColor.red * startPercent + endColor.red * endPercent,
                    startColor.green * startPercent + endColor.green * endPercent,
                    startColor.blue * startPercent + endColor.blue * endPercent);
        };
    }
}
