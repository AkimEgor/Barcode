import formActivity.ButtonActivity;
import generation.BarcodeGeneration;
import generation.BarcodeObject;
import generation.BarcodeTypes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class MainForm {
    private JPanel mainPanel;
    private JButton generatingToADirectory;
    private JButton choosingADirectory;
    private JButton saveBufferButton;
    private JButton bufferGenerationButton;
    private JComboBox<String> listBarcodeTypes;
    private JComboBox<BarcodeObject> barcodeList;
    private JButton OpenDirectory;
    private JFormattedTextField barcodeBuffer;
    private JFormattedTextField directoryPath;
    private JFormattedTextField enteringQuantity;
    private JFormattedTextField formElementText;
    private JLabel barcodePicture;


    public MainForm () {
        initialFillingForm();
        JFrame frame = new JFrame();
        try {
            //URL url1 = this.getClass().getResource("sf.png");
            String s = System.getProperty("user.dir");
            String path = s.replaceAll("\\\\", "\\\\\\\\");
            File file= new File(path + "\\sf.png");
            //BufferedImage source = ImageIO.read(file);
            //frame.setIconImage(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //user.dir
        frame.setSize(850,500);
        frame.add(mainPanel);
        choosingADirectory.addActionListener(ButtonActivity.directorySearch(directoryPath));
        generatingToADirectory.addActionListener(ButtonActivity.generateShk(mainPanel,enteringQuantity));
        bufferGenerationButton.addActionListener(ButtonActivity.generateShkOne(barcodeBuffer,barcodePicture,listBarcodeTypes,mainPanel,barcodeList));
        saveBufferButton.addActionListener(ButtonActivity.generateShkOne(barcodeBuffer,barcodePicture,listBarcodeTypes,mainPanel,barcodeList));
        saveBufferButton.addActionListener(ButtonActivity.directoryShkOne(barcodeBuffer));
        barcodeList.addActionListener(ButtonActivity.barcodeList(barcodeList,barcodePicture,barcodeBuffer));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initialFillingForm (){
        directoryPath.setText(System.getProperty("user.home") + "\\Desktop\\EAN13");
        directoryPath.setEditable(false);
        enteringQuantity.setText("1");
        bufferGenerationButton.setText("Сгенерировать 1 ШК");
        saveBufferButton.setText("Сохранить в директорию");
        generatingToADirectory.setText("Сгенерировать ШК");
        choosingADirectory.setText("Выбор директории");
        OpenDirectory.setText("Открыть");
        formElementText.setText("Количество этикеток:");
        formElementText.setEditable(false);
        listBarcodeTypes.setModel(listBarcodeTypesInitial());
    }

    private DefaultComboBoxModel<String> listBarcodeTypesInitial () {
        DefaultComboBoxModel<String> cbModel = new DefaultComboBoxModel<String>();
        for (BarcodeTypes env : BarcodeTypes.values()) {
            cbModel.addElement(env.getUrl());
        }
        return cbModel;
    }

}
