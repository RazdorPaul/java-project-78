package hexlet.code.schemas;

public class StringSchema {
    private boolean required = false;
    private String sub = null;
    private Integer minLength = null;

    public StringSchema required() {
        required = true;
        return this;
    }

    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        sub = substring;
        return this;
    }

    public boolean isValid(String data) {
        System.out.println("DEBUG: data=" + data + ", required=" + required + ", minLength=" + minLength + ", sub=" + sub);
        if (!required && (data == null || data.isEmpty())) {
            return true;
        }
        if (required && (data == null || data.isEmpty())) {
            return false;
        }
        if (minLength != null && data.length() < minLength) {
            return false;
        }
        if (sub != null && !data.contains(sub)) {
            return false;
        }
        return true;
    }
}
