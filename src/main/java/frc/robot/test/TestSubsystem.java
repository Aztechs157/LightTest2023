// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.test;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.lib.Flexbox;
import frc.robot.lib.LightSubsystem;
import frc.robot.lib.Pattern;
import frc.robot.lib.ScrollBox;

public class TestSubsystem extends LightSubsystem {

    public TestSubsystem(final int id, final int length) {
        super(id, length);
    }

    public final Command solid = registerPattern(new Flexbox()
            .add(17, Pattern.solid(Color.kRed))
            .add(17, Pattern.solid(Color.kGreen))
            .add(16, Pattern.solid(Color.kBlue))
            .add(16, Pattern.solid(Color.kYellow))
            .intoPositionPattern());

    public final Command evens = registerPattern((data) -> data.position() % 2 == 0 ? Color.kWhite : Color.kBlack);

    public final Command scroll = registerPattern(
            new ScrollBox()
                    .add((data) -> data.position() % 2 == 0 ? Color.kBlue
                            : Color.kRed)
                    .add((data) -> data.position() % 2 == 0 ? Color.kYellow
                            : Color.kPurple)
                    .add((data) -> data.position() % 2 == 0 ? Color.kGreen
                            : Color.kOrange)
                    .intoPattern());
    // .modify((data) -> {
    // data.setPosition(data.position() + Constants.kLightsLength +
    // Constants.kLightsShortHalf);
    // }));

    public final Command gradientPosition = registerPattern(
            new Flexbox()
                    .add(Pattern.gradientOverPosition(Color.kBlue, Color.kYellow))
                    .add(Pattern.gradientOverPosition(Color.kYellow, Color.kBlue))
                    .intoPositionPattern()
                    .shiftOverTime());

    public final Command gradientTime = registerPattern(
            new Flexbox()
                    .add(Pattern.gradientOverTime(Color.kBlue, Color.kYellow))
                    .add(Pattern.gradientOverTime(Color.kYellow, Color.kBlue))
                    .intoTimePattern());

    public final Command flags;
    {
        final var timeFlex = new Flexbox();

        for (final var flag : kFlagData) {
            final var positionFlex = new Flexbox();

            for (final var color : flag) {
                positionFlex.add(Pattern.solid(color));
            }

            timeFlex.add(positionFlex.intoPositionPattern());
        }

        final var timePattern = timeFlex.intoTimePattern();
        final var segments = new Flexbox()
                .add(Constants.kLightsLongHalf, timePattern)
                .add(Constants.kLightsShortHalf, timePattern)
                .intoPositionPattern();

        final var cycle = 50 * 4 * kFlagData.length;

        flags = registerPattern(cycle, segments);
    }

    public final Command flagsScroll;
    {
        final var scrollBox = new ScrollBox();

        for (final var flag : kFlagData) {
            final var positionFlex = new Flexbox();

            for (final var color : flag) {
                positionFlex.add(Pattern.solid(color));
            }

            scrollBox.add(positionFlex.intoPositionPattern());
        }

        flagsScroll = registerPattern(scrollBox.intoPattern());
    }

    private static final Color[][] kFlagData = new Color[][] {
            { Color.kRed, Color.kOrange, Color.kYellow, Color.kGreen, Color.kBlue, Color.kPurple },
            { Color.kBlue, Color.kDeepPink, Color.kWhite, Color.kDeepPink, Color.kBlue },
            { Color.kYellow, Color.kWhite, Color.kPurple, Color.kBlack },
            { Color.kRed, Color.kRed, Color.kPurple, Color.kBlue, Color.kBlue },
            { Color.kPurple, Color.kYellow, Color.kBlue },
            { Color.kBlack, Color.kGray, Color.kWhite, Color.kPurple },
    };

}
