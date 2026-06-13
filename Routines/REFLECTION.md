# Reflection – processCustomer Refactoring

## Functional Cohesion Achieved

The original routine mixed six unrelated concerns: validation, summation,
discount lookup, discount application, message formatting, console output,
and email notification — all inside one method with 8 parameters and no
single purpose.

Extracted routines, each functionally cohesive:

- `OrderValidator.validateOrders()` — validates input data only
- `OrderCalculator.sumOrders()` — sums order totals only
- `OrderCalculator.discountRate()` — looks up a discount rate (noun-style,
  accessor-like function)
- `OrderCalculator.calculateDiscountedTotal()` — applies a discount, pure
  computation
- `MessageBuilder.buildSummaryMessage()` — builds a string, no side effects
- `NotificationService.printSummary()` / `emailCustomer()` — output/
  side-effect routines, separated from computation

The orchestrating `processCustomer()` is now sequential/procedural —
acceptable at the top-level coordination layer since each step it calls is
itself functionally cohesive.

## Parameter Passing Issues Encountered

The original line `d = total;` attempted to "return" the computed total
through an `int`/`double` parameter `d`. In Java, primitives (and the
`double d` parameter) are passed by value — any reassignment inside the
method is purely local and invisible to the caller. The original `d` in
the caller's scope would remain unchanged, making this line dead code with
no observable effect — a silent bug.

The fix: the refactored `processCustomer()` returns the computed `total`
explicitly via `return total;`, and additionally stores it on the
`Customer` object (`customer.currentDiscountTotal`), since `Customer` is
passed by reference (object reference passed by value, but the object
itself is shared/mutable), so changes to its fields ARE visible to the
caller.

## Behavior Under Pass-by-Value-Result

If Java used pass-by-value-result instead of pass-by-value for `d`:

- **Copy-in**: at the call, the value of the caller's `d` would be copied
  into the local parameter.
- **Local execution**: `d = total;` would update only the local copy.
- **Copy-out**: at method exit, this local copy WOULD be copied back into
  the caller's `d`, making the caller's variable equal to `total`.

So under value-result, the original buggy line `d = total;` would actually
have "worked" — the caller's `d` would end up holding `total` after the
call, despite no explicit `return`. This illustrates why value-result was
historically attractive for "in/out" parameters in languages like Ada/
Fortran: it allows a parameter to communicate a result back to the caller
without an explicit return statement — something pass-by-value in Java/C
cannot do for primitives.
