package Entity;

public enum Status {
    C("Complete"), P("Pending"),R("Reject");

    private String label;

    Status(String label) {
        this.label = label;

    }
    private String getLabel(){
        return label;
    }
}
