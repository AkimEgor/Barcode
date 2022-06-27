package generation;

public enum BarcodeTypes {
    EAN13 ("EAN-13"),
    EAN13S ("EAN-13 Свой"),
    EAN128 ("EAN-128"),
    MINE ("Свой ШК");


    private String title;

    BarcodeTypes(String title) {
        this.title = title;
    }
    public String getUrl() {
        return title;
    }

}
