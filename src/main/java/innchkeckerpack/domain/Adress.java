package innchkeckerpack.domain;

public class Adress {
    private String value;
    private String unrestricted_value;
    private AdressData data;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnrestricted_value() {
        return unrestricted_value;
    }

    public void setUnrestricted_value(String unrestricted_value) {
        this.unrestricted_value = unrestricted_value;
    }

    public AdressData getData() {
        return data;
    }

    public void setData(AdressData data) {
        this.data = data;
    }
}
