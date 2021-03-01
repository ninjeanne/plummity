package shop.plumeria.plummity.utils;

public enum VeteranRatingType {
    unselect(0),
    like(10),
    dislike(-10);
    public final long value;

    private VeteranRatingType(int value){
        this.value = value;
    }
}
