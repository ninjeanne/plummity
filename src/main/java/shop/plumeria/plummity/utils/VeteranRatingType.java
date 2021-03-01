package shop.plumeria.plummity.utils;

public enum VeteranRatingType {
    unselect(0),
    like(50),
    dislike(-50);
    public final long value;

    private VeteranRatingType(int value){
        this.value = value;
    }
}
