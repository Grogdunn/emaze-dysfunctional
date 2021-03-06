package net.emaze.dysfunctional.options;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.emaze.dysfunctional.contracts.dbc;

/**
 * Transforms an optional mapping Optional.nothing to
 * Either.left(leftTypeProvider()) and Optional.just to Either.right.
 *
 * @author rferranti
 * @param <L> the either left type parameter
 * @param <R> the either right type parameter
 */
public class OptionalToEither<L, R> implements Function<Optional<R>, Either<L, R>> {

    private final Supplier<L> left;

    public OptionalToEither(Supplier<L> left) {
        dbc.precondition(left != null, "cannot create OptionalToEither with a null left supplier");
        this.left = left;
    }

    @Override
    public Either<L, R> apply(Optional<R> right) {
        dbc.precondition(right != null, "cannot transform a null optional to an either");
        if (right.isPresent()) {
            return Either.right(right.get());
        }
        return Either.left(left.get());
    }
}
