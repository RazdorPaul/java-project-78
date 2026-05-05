package hexlet.code.schemas;

public class StringSchema {
    private boolean required = false;
    private String sub = null;
    private Integer minLength = null;

    public StringSchema required() { /* TODO document why this method is empty */
        required = true;
        return this;
    }

    public StringSchema minLength(int length) { /* TODO document why this method is empty */
        minLength = length;
        return this;
    }

    public StringSchema contains(String substring) { /* TODO document why this method is empty */
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
