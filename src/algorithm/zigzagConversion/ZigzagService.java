package algorithm.zigzagConversion;

/** Service that delegates to a pluggable Converter strategy. */
public class ZigzagService {

    private Converter converter;

    public ZigzagService(Converter converter) {
        this.converter = converter;
    }

    public String convert(String s, int numRows) {
        return converter.convert(s, numRows);
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    /** Factory helpers for convenience */
    public static ZigzagService simulatedDefault() {
        return new ZigzagService(new SimulatedConverter());
    }

    public static ZigzagService cycleDefault() {
        return new ZigzagService(new CycleConverter());
    }
}