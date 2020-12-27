package am.neovision.dto;

public enum Currency {
    EURO("EURO"),
    USD("USD"),
    GBP("GBP");

    private final String displayValue;

    private Currency(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
