package generation;

import java.awt.image.BufferedImage;

public class BarcodeObject {

    private BarcodeTypes barcodeTypesH;
    private StringBuilder cod;
    private BufferedImage resultH;

    public BarcodeObject (BarcodeTypes barcodeTypes, StringBuilder cod, BufferedImage result) {
        this.barcodeTypesH = barcodeTypes;
        this.cod = cod;
        this.resultH = result;
    }


    public BarcodeTypes getBarcodeTypes() {
        return barcodeTypesH;
    }

    public StringBuilder getCod() {
        return cod;
    }

    public BufferedImage getResult() {
        return resultH;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(cod);
        stringBuilder.append("  ---  " + barcodeTypesH.toString());
        return stringBuilder.toString();
    }


}
