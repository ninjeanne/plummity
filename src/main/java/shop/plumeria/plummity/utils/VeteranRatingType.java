package shop.plumeria.plummity.utils;

public enum VeteranRatingType {
    zero(0),
    good(10),
    bad(-10);
    public final long value;

    private VeteranRatingType(int value){
        this.value = value;
    }
}
