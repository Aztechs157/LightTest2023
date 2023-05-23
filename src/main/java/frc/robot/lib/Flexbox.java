package frc.robot.lib;

import java.util.ArrayList;

public class Flexbox {
    private final ArrayList<Integer> amounts = new ArrayList<>();
    private final ArrayList<Pattern> patterns = new ArrayList<>();

    public final Flexbox add(final int amount, final Pattern pattern) {
        amounts.add(amount);
        patterns.add(pattern);
        return this;
    }

    public final Pattern intoPattern() {
        final var percents = new ArrayList<Double>(amounts.size());
        final var breakpoints = new ArrayList<Double>(amounts.size());

        int totalAmount = 0;
        for (final int amount : amounts) {
            totalAmount += amount;
        }

        double runningBreakpoint = 0;
        for (int i = 0; i < amounts.size(); i++) {
            final var percent = (double) amounts.get(i) / totalAmount;
            percents.add(percent);
            breakpoints.add(runningBreakpoint + percent);
        }

        return (data) -> {
            final var percent = data.positionPercent();

            int highestPattern = 0;
            for (int i = 0; i < breakpoints.size(); i++) {
                if (percent < breakpoints.get(i)) {
                    break;
                }
                highestPattern = i;
            }

            data.setMaxPosition((int) Math.floor(data.maxPosition() * percents.get(highestPattern)));
            return patterns.get(highestPattern).getColor(data);
        };
    }
}
