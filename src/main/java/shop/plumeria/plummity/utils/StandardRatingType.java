package shop.plumeria.plummity.utils;

import lombok.Data;

public enum StandardRatingType {
    zero(0),
    plusOne(1),
    plusFive(5);

    public final long value;

    private StandardRatingType(int value){
        this.value = value;
    }
}
