import java.util.Objects;

public class Square {
    private String _val;
    private Color _color;

    Square(String spot){ this._val = spot; }

    Square(Square o){
        this._val = o._val;
        this._color = o._color;
    }

    public String get_val() {
        return _val;
    }

    public void set_val(String _val) {
        this._val = _val;
    }

    public void set_color(Color _color) {
        this._color = _color;
    }

    public int get_cost() {
        int ans = 0;
        switch (_color){
            case GREEN: ans = 1;
                break;
            case RED: ans =  30;
                break;
        }
        return ans;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square slot = (Square) o;
        return _val.equals(slot._val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_val);
    }
}


