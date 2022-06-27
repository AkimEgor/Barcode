package formActivity;

import generation.BarcodeGeneration;
import generation.BarcodeObject;
import generation.BarcodeTypes;


import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Vector;

public class ButtonActivity {

    //private BarcodeObject barcodeEAN13;
    private static File catalog;

    public ButtonActivity () {}

    public static File getCatalog() {
        return catalog;
    }

    public static void setCatalog(File catalogH) {
        catalog = catalogH;
    }

    public static Action directorySearch (JFormattedTextField PathTheDirectory) {
        Action directorySearchAction = new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }
            @Override
            public void putValue(String key, Object value) {}
            @Override
            public void setEnabled(boolean b) {}
            @Override
            public boolean isEnabled() {
                return false;
            }
            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {}

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(600,400);
                JFileChooser jFileChooser = new JFileChooser();
                frame.add(jFileChooser);
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                jFileChooser.addActionListener(rememberTheDirectory(PathTheDirectory,jFileChooser,frame));
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        };
        return directorySearchAction;
    }

    private static Action rememberTheDirectory (JFormattedTextField PathTheDirectory ,JFileChooser jFileChooser,JFrame frame) {
        Action rememberTheDirectoryAction = new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }
            @Override
            public void putValue(String key, Object value) {}
            @Override
            public void setEnabled(boolean b) {}
            @Override
            public boolean isEnabled() {
                return false;
            }
            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {}

            @Override
            public void actionPerformed(ActionEvent e) {
                catalog = jFileChooser.getCurrentDirectory();
                PathTheDirectory.setText(catalog.toString());
                frame.dispose();
            }
        };
        return rememberTheDirectoryAction;
    }

    public static Action generateShk (JPanel mainPanel ,JFormattedTextField NumberOfLabels) {
        Action generateShk = new Action() {
            @Override
            public Object getValue(String key) {return null;}
            @Override
            public void putValue(String key, Object value) {}
            @Override
            public void setEnabled(boolean b) {}
            @Override
            public boolean isEnabled() {return false;}
            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {}

            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodeGeneration barcodeGeneration = new BarcodeGeneration(catalog);
                try {
                    barcodeGeneration.generationFile(Integer.parseInt(NumberOfLabels.getText()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showConfirmDialog(mainPanel,"ERROR: В поле количество этикеток есть буквы",
                            "ERROR",JOptionPane.PLAIN_MESSAGE);
                }
            }
        };
        return generateShk;
    }

    public static Action generateShkOne (JFormattedTextField editorPane,JLabel imagePanel,JComboBox<String> listShk,JPanel mainPanel,JComboBox<BarcodeObject> barcodeList) {
        Action generateShkOne = new Action() {
            @Override
            public Object getValue(String key) {return null;}
            @Override
            public void putValue(String key, Object value) {}
            @Override
            public void setEnabled(boolean b) {}
            @Override
            public boolean isEnabled() {return false;}
            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {}

            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodeGeneration barcodeGeneration = new BarcodeGeneration();
                BarcodeObject barcodeEAN13 = null;
                if (listShk.getSelectedItem().toString().equals("EAN-13 Свой")){
                    try {
                        barcodeEAN13 = barcodeGeneration.generationEAN13(editorPane.getText());
                    }catch (Exception ex) {
                        JOptionPane.showConfirmDialog(mainPanel,"ERROR: Неверный код"
                                , "ERROR",JOptionPane.PLAIN_MESSAGE);
                        ex.printStackTrace();
                    }

                }else if (listShk.getSelectedItem().toString().equals("EAN-13")){
                    try {
                        barcodeEAN13 = barcodeGeneration.generationEAN13Rnd();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else if (listShk.getSelectedItem().toString().equals("EAN-128")){
                    try {
                        barcodeEAN13 = barcodeGeneration.generationEAN128(editorPane.getText());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (listShk.getSelectedItem().toString().equals("Свой ШК")){
                    try {
                        barcodeEAN13 = barcodeGeneration.generationMine(editorPane.getText());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                addBarcodeObjectToList(barcodeEAN13,barcodeList);
                imagePanel.setIcon(new ImageIcon(barcodeEAN13.getResult()));
                editorPane.setText((barcodeEAN13.getCod().toString()));
            }
        };
        return generateShkOne;
    }

    public static Action directoryShkOne(JFormattedTextField editorPane){
        Action directoryShkOne = new Action() {
            @Override
            public Object getValue(String key) {return null;}
            @Override
            public void putValue(String key, Object value) {}
            @Override
            public void setEnabled(boolean b) {}
            @Override
            public boolean isEnabled() {return false;}
            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {}

            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodeGeneration barcodeGeneration = new BarcodeGeneration(catalog);
                try {
                    BarcodeObject barcodeEAN13 = barcodeGeneration.generationEAN128(editorPane.getText());
                    barcodeGeneration.saveShk(barcodeEAN13);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        return directoryShkOne;
    }

    public static Action barcodeList (JComboBox<BarcodeObject> barcodeList1, JLabel imagePanel,JFormattedTextField editorPane) {
        Action barcodeList = new Action() {
            @Override
            public Object getValue(String key) {return null;}
            @Override
            public void putValue(String key, Object value) {}
            @Override
            public void setEnabled(boolean b) {}
            @Override
            public boolean isEnabled() {return false;}
            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {}

            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodeObject barcodeEAN13 = barcodeList1.getItemAt(barcodeList1.getSelectedIndex());
                imagePanel.setIcon(new ImageIcon(barcodeEAN13.getResult()));
                editorPane.setText((barcodeEAN13.getCod().toString()));
            }
        };
        return barcodeList;
    }

    public static void addBarcodeObjectToList (BarcodeObject BarcodeObject,JComboBox<BarcodeObject> barcodeList) {
        ComboBoxModel<BarcodeObject> cbModel1 = barcodeList.getModel();
        DefaultComboBoxModel<BarcodeObject> cbModel = new DefaultComboBoxModel<BarcodeObject>();
        for (int i = 0; i < cbModel1.getSize(); i++) {
            cbModel.addElement(cbModel1.getElementAt(i));
        }
        cbModel.addElement(BarcodeObject);
        barcodeList.setModel(cbModel);
    }

}
