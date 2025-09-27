# Roman to Integer (package: `romanToInteger`)

A production-ready, **single-pass** Roman numeral parser implemented in Java with a clean OOP design and an optional strict validator.

## Why this implementation?
- **O(n) time, O(1) space** — linear scan using the classic subtractive rule.
- **Cache-friendly lookups** — uses a 26-slot lookup table (`A..Z`) instead of a `HashMap`.
- **Optional strict validation** — enforce classical Roman grammar (e.g., `I` before `V`/`X` only).
- **Zero dependencies** — runs out of the box in IntelliJ IDEA; includes a built-in test runner (no JUnit required).

---

## Project Layout
src/
main/
java/
algorithm/
romanToInteger/
Main.java # CLI entrypoint, help/STDIN/test modes
RomanNumeralService.java# Service interface
RomanConverter.java # High-performance converter (single pass)
RomanValidator.java # Optional strict validator (rules & checks)

---

## How to Run (IntelliJ IDEA)
1. Open the project folder in **IntelliJ IDEA**.
2. Ensure **Project SDK** is set (Java 17+ recommended; Java 8+ works).
3. Right-click `Main.main()` → **Run 'Main.main()'**.
4. Use **Run Configuration → Program arguments** for CLI invocations.

---

## CLI Usage
Usage:
Main [--strict] [ROMAN ...]
Main --stdin [--strict]
Main --test [--strict]
Main --help

Options:
--strict Enable strict Roman grammar validation (throws on invalid forms)
--stdin Read one Roman numeral per line from STDIN
--test Run built-in test scenarios (no JUnit required)
--help Show this help

### Examples

Basic conversions
Main MCMXCIV XL CM

→ MCMXCIV -> 1994
XL -> 40
CM -> 900
Strict validation
Main --strict MCMXCIV

From STDIN (one numeral per line)
echo "XLII" | Main --stdin --strict

Run built-in tests
Main --test --strict

---

## API (as a library)
```java
import romanToInteger.RomanNumeralService;
import romanToInteger.RomanConverter;
import romanToInteger.RomanValidator;

RomanNumeralService service = new RomanConverter();

// Optional strict validation (throws IllegalArgumentException on invalid grammar)
RomanValidator.validateStrict("MCMXCIV");

int value = service.convert("MCMXCIV"); // 1994
Algorithm (single pass)

For each character c:

If value(c) < value(next), subtract value(c) (subtractive pair like IV, IX, XL, ...).

Else, add value(c).
Finally, add the last character’s value.
This yields a single left→right pass: O(n) time, O(1) space.

Strict Validation Rules

Allowed symbols: I, V, X, L, C, D, M.

Repetition:

I, X, C, M can repeat at most 3 times in a row.

V, L, D cannot repeat.

Subtractive notation:

I before V or X only.

X before L or C only.

C before D or M only.

No double subtraction like IIV.

If you don’t need grammar enforcement, you can skip validation for best performance.
Test Scenarios (built-in)
Main --test          # fast checks without grammar validation
Main --test --strict # includes invalid-case assertions

Valid cases (samples):

Simple: I=1, II=2, III=3, V=5, X=10, L=50, C=100, D=500, M=1000

Subtractive: IV=4, IX=9, XL=40, XC=90, CD=400, CM=900

Mixed: LVIII=58, MCMXCIV=1994, MMXXV=2025, MMM=3000

Invalid (strict mode):

Repetition errors: IIII, VV, LL, DD

Bad subtractives: IC, IL, XM, VX

Double subtraction: IIV

Non-Roman chars: ABC, empty string

Performance Notes

List indexing (vals[ch - 'A']) is extremely fast and branch-light.

Avoids per-character hashing (as in HashMap lookups).

Time complexity: O(n); Space: O(1).

Extending

Add a toRoman(int n) encoder.

Add JUnit tests (if you prefer a testing framework).

Accept lowercase by normalizing s = s.toUpperCase() before validation/conversion.