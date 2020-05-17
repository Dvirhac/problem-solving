import java.util.Objects;

public class Slot {
    private String _val;
    private Color _color;
    private int _cost;

    Slot(String spot){
        this._val = spot;
        this._cost = 0;
    }

    Slot(Slot o){
        this._val = o._val;
        this._color = o._color;
        this._cost = o._cost;
    }

    public String get_val() {
        return _val;
    }

    public void set_val(String _val) {
        this._val = _val;
    }

    public Color get_color() {
        return _color;
    }

    public void set_color(Color _color) {
        this._color = _color;
    }

    public int get_cost() {
        return _cost;
    }

    public void set_cost(int _cost) {
        this._cost = _cost;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return _val.equals(slot._val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_val);
    }
}


