package frc.robot.lib;

import java.util.ArrayList;

public class ScrollBox {
    private final ArrayList<Pattern> patterns = new ArrayList<>();

    public ScrollBox add(final Pattern pattern) {
        patterns.add(pattern);
        return this;
    }

    public Pattern intoPattern() {
        return (data) -> {
            final var physicalMaxPosition = data.maxPosition();
            final var virtualMaxPosition = physicalMaxPosition * patterns.size();

            final var shiftAmount = (int) Math.floor(data.timePercent() * virtualMaxPosition);

            final var virtualPosition = (data.rawPosition() + shiftAmount) % virtualMaxPosition;

            final var index = virtualPosition / physicalMaxPosition;

            return patterns.get(index).getColor(data);
        };
    }
}
