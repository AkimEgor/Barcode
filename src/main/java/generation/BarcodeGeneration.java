package generation;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;

public class BarcodeGeneration {

    private static File directoryToGenerate;

    public BarcodeGeneration(){}

    public BarcodeGeneration(File catalogGeneration){
        directoryToGenerate = catalogGeneration;
    }

    public static StringBuilder rndEANn (){
        StringBuilder ean = new StringBuilder();
        for (int i = 0; i < 12; i++){
            ean.append(rnd(0,9));
        }
        return ean;
    }

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static StringBuilder checkDigit(StringBuilder ean){

        StringBuilder checkDigit = new StringBuilder();
        int even = 0;
        for (int i = 0; i < 12;){
            char e = ean.charAt(i);
            even = even + Character.getNumericValue(e);
            i = i + 2;
        }

        int odd = 0;
        for (int i = 1; i < 12;){
            char e = ean.charAt(i);
            odd = odd + Character.getNumericValue(e);
            i = i + 2;
        }
        odd = odd * 3;

        int control = (int) (even + odd) / 10;
        control = (control + 1) * 10;

        int total = control - (even + odd);
        if (total == 10){
            total = 0;
        }
        return checkDigit.append(total);
    }

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
        return BarcodeImageHandler.getImage(barcode);
    }

    public static BufferedImage generateEAN128BarcodeImage(String barcodeText) throws Exception {
        Barcode barcode = BarcodeFactory.createCode128(barcodeText);
        return BarcodeImageHandler.getImage(barcode);
    }

    public String generationFile(int i) throws Exception{
        String popupWindows = null;

        if (directoryToGenerate == null) {
            String s = System.getProperty("user.home");
            String path = s.replaceAll("\\\\", "\\\\\\\\");
            directoryToGenerate = new File(path+"\\Desktop\\EAN13");
        }
        if(directoryToGenerate.isDirectory()) {
            for (File item : directoryToGenerate.listFiles()) {
                if (!item.isDirectory()) {item.delete();}
            }}

        if (!directoryToGenerate.exists()){
            directoryToGenerate.mkdir();
            if (directoryToGenerate != null) {
                popupWindows = "Директории не существует она была создана";
            }}

        String shk = "\\shk.txt";
        FileWriter f2;
        File outputShk = new File(directoryToGenerate + shk);
        f2 = new FileWriter(outputShk, false);
        for (int j = 0;j < i; j++) {
            StringBuilder cod = rndEANn();
            String name ="\\" + j + ".jpg";
            File output = new File(directoryToGenerate + name);
            BufferedImage result = generateEAN13BarcodeImage(cod.toString());
            StringBuilder text = new StringBuilder();
            text.append(j + " ---  ").append(cod.append(checkDigit(cod) + "\n"));
            f2.append(text.toString());
            ImageIO.write(result, "jpg", output);
        }
        f2.close();
        return popupWindows;
    }

    public void saveShk (BarcodeObject barcodeObject) throws Exception {
        if (directoryToGenerate == null) {
            String s = System.getProperty("user.home");
            String path = s.replaceAll("\\\\", "\\\\\\\\");
            directoryToGenerate = new File(path+"\\Desktop\\EAN13");
        }
        String shk = "\\shkOne.txt";
        String name ="\\" + barcodeObject.getCod().toString() + ".jpg";
        File output = new File(directoryToGenerate + name);
        BufferedImage result = barcodeObject.getResult();
        ImageIO.write(result, "jpg", output);
        File outputShk = new File(directoryToGenerate + shk);
        FileWriter f2 = new FileWriter(outputShk, false);
        f2.append(barcodeObject.getCod().toString());
        f2.close();
    }

    public static BarcodeObject generationEAN128 (String cod) throws Exception{
        return new BarcodeObject(BarcodeTypes.EAN128,new StringBuilder(cod),generateEAN128BarcodeImage(cod.toString()));
    }

    public static BarcodeObject generationEAN13Rnd() throws Exception{
        StringBuilder cod = rndEANn();
        StringBuilder codP = new StringBuilder(cod);
        BarcodeObject barcodeObject = new BarcodeObject(BarcodeTypes.EAN13,cod.append(checkDigit(cod)),generateEAN13BarcodeImage(codP.toString()));
        return  barcodeObject;
    }

    public static BarcodeObject generationEAN13(String cod) throws Exception{
        StringBuilder codP = new StringBuilder(cod);
        BarcodeObject barcodeObject = new BarcodeObject(BarcodeTypes.EAN13S,new StringBuilder(cod),generateEAN13BarcodeImage(codP.toString()));
        return barcodeObject;
    }

    public static BarcodeObject generationMine (String cod) throws Exception{
        //StringBuilder codP = new StringBuilder(cod);
        Barcode barcode = BarcodeFactory.createCode128(cod);
        BarcodeObject barcodeObject = new BarcodeObject(BarcodeTypes.MINE,new StringBuilder(cod),BarcodeImageHandler.getImage(barcode));
        return barcodeObject;
    }
}
