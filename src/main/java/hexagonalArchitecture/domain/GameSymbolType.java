package hexagonalArchitecture.domain;

public enum GameSymbolType {
    CROSS, CIRCLE, EMPTY;

    public static GameSymbolType fromString(String s) {
        if (s == null) throw new IllegalArgumentException("Symbol cannot be null");
        s = s.trim().toUpperCase();
        switch (s) {
            case "CROSS":
                return CROSS;
            case "CIRCLE":
                return CIRCLE;
            default:
                throw new IllegalArgumentException("Unknown symbol: " + s);
        }
    }
};