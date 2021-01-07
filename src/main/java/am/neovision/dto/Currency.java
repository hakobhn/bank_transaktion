package am.neovision.dto;

public enum Currency {
    EUR("EUR"),
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
