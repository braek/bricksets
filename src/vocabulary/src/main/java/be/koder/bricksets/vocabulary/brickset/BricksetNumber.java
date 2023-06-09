package be.koder.bricksets.vocabulary.brickset;

import be.koder.bricksets.vocabulary.domain.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public final class BricksetNumber implements ValueObject {

    private final String value;
    private final static Pattern REGEX = Pattern.compile("^(\\d{3}-\\d)|(\\d{4,6}(-\\d)?)$");

    private BricksetNumber(final String str) {
        final var sanitized = ofNullable(str)
                .map(String::trim)
                .orElse(null);
        if (isNull(sanitized) || !REGEX.matcher(sanitized).matches()) {
            throw new InvalidBricksetNumberException(str);
        }
        this.value = sanitized;
    }

    public static BricksetNumber fromString(final String str) {
        return new BricksetNumber(str);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BricksetNumber that = (BricksetNumber) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String getValue() {
        return value;
    }
}
