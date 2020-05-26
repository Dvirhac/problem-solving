import java.util.Objects;

public class Square {
    private int _val;
    private Color _color;

    Square(String spot){
        if (spot.equals("_")){
            this._val = -1;
        }
        else{
            this._val = Integer.parseInt(spot);
        }
    }

    Square(Square o){
        this._val = o._val;
        this._color = o._color;
    }

    public int get_val() {
        return _val;
    }

    public void set_val(int _val) {
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
            case BLACK: ans = -1;
        }
        return ans;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square slot = (Square) o;
        return _val == slot._val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_val);
    }
}


