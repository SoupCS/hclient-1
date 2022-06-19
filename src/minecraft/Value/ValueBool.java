package Value;

public class ValueBool extends Value {
    private boolean value;
    private String name;

    public ValueBool(String name, boolean state) {
        super(name, state);
        this.name = name;
        this.value = state;
        arrayVal.add(this);
    }

    public Boolean getValue() {
        return this.value;
    }

    public boolean isValue() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
