// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightSubsystem extends SubsystemBase {

    private final AddressableLED lights;
    private final AddressableLEDBuffer buffer;
    private final int bufferLength;

    /** Creates a new LightSubsystem. */
    public LightSubsystem(final int id, final int length) {
        this.lights = new AddressableLED(id);
        this.buffer = new AddressableLEDBuffer(length);
        this.bufferLength = length;

        for (int i = 0; i < length; i++) {
            buffer.setLED(i, Color.kSkyBlue);
        }

        lights.setLength(length);
        lights.setData(buffer);
        lights.start();
    }

    public int defaultCycleLength = (int) Math.floor(5 / TimedRobot.kDefaultPeriod);

    public static class PixelData {
        private int position = 0;
        private int maxPosition = 0;

        public int position() {
            return position % maxPosition;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int maxPosition() {
            return maxPosition;
        }

        public double positionPercent() {
            return (double) position() / maxPosition;
        }

        private int time = 0;
        private int maxTime = 0;

        public int time() {
            return time % maxTime;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int maxTime() {
            return maxTime;
        }

        public double timePercent() {
            return (double) time() / maxTime;
        }
    }

    private class RenderCommand extends CommandBase {
        private final int cycleLength;
        private final Pattern pattern;

        private RenderCommand(final int cycleLength, final Pattern pattern) {
            this.cycleLength = cycleLength;
            this.pattern = pattern;
            addRequirements(LightSubsystem.this);
        }

        private final PixelData data = new PixelData();
        private int time = 0;

        @Override
        public void execute() {
            for (int position = 0; position < bufferLength; position++) {
                data.position = position;
                data.maxPosition = bufferLength;
                data.time = time;
                data.maxTime = cycleLength;

                buffer.setLED(position, pattern.getColor(data));
            }

            lights.setData(buffer);
            time++;
        }

        @Override
        public boolean runsWhenDisabled() {
            return true;
        }
    }

    public RenderCommand registerPattern(final Pattern pattern) {
        return new RenderCommand(250, pattern);
    }

    public Command registerPattern(final int cycleLength, final Pattern pattern) {
        return new RenderCommand(cycleLength, pattern);
    }
}
