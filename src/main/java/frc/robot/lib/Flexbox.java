package frc.robot.lib;

import java.util.ArrayList;

public class Flexbox {
    private final ArrayList<Integer> amounts = new ArrayList<>();
    private final ArrayList<Pattern> patterns = new ArrayList<>();

    public final Flexbox add(final Pattern pattern) {
        return add(1, pattern);
    }

    public final Flexbox add(final int amount, final Pattern pattern) {
        amounts.add(amount);
        patterns.add(pattern);
        return this;
    }

    public final Pattern intoPositionPattern() {
        return intoPattern(true);
    }

    public final Pattern intoTimePattern() {
        return intoPattern(false);
    }

    private final Pattern intoPattern(final boolean positionPattern) {
        final var percents = new ArrayList<Double>(amounts.size());
        final var breakpoints = new ArrayList<Double>(amounts.size());

        int totalAmount = 0;
        for (final int amount : amounts) {
            totalAmount += amount;
        }

        double runningBreakpoint = 0;
        for (int i = 0; i < amounts.size(); i++) {
            final var percent = (double) amounts.get(i) / totalAmount;
            runningBreakpoint += percent;

            percents.add(percent);
            breakpoints.add(runningBreakpoint);
        }

        return (data) -> {
            final var percent = positionPattern ? data.positionPercent() : data.timePercent();

            int index = breakpoints.size() - 1;
            for (int i = index; i >= 0; i--) {
                if (percent >= breakpoints.get(i)) {
                    break;
                }
                index = i;
            }

            if (positionPattern) {
                final var newMax = Math.floor(data.maxPosition() * percents.get(index));
                data.setMaxPosition((int) newMax);
            } else {
                final var newMax = Math.floor(data.maxTime() * percents.get(index));
                data.setMaxTime((int) newMax);
            }

            return patterns.get(index).getColor(data);
        };
    }
}
