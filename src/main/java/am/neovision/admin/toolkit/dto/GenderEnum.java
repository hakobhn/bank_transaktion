package am.neovision.admin.toolkit.dto;

public enum GenderEnum {
    MALE('M'),
    FEMALE('F');

    private char symbol;

    GenderEnum(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }
}
