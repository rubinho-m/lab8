package app.lab8;

public class Validator {
    public boolean isCoordsOk(String sx, String sy) {
        try {
            float x = Float.parseFloat(sx);
            int y = Integer.parseInt(sy);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean isXOk(String sx){
        try {
            float x = Float.parseFloat(sx);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isYOk(String sy) {
        try {
            int y = Integer.parseInt(sy);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean isPriceOk(String sprice) {
        try {
            double price = Double.parseDouble(sprice);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCapacityOk(String scapacity) {
        try {
            long capacity = Long.parseLong(scapacity);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
