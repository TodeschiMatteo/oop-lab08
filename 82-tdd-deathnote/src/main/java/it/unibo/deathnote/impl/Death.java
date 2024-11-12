package it.unibo.deathnote.impl;

import java.util.Objects;

public final class Death {

    private static final int CAUSE_DELAY = 40;
    private static final int DETAILS_DELAY = 6040; 

    private final String cause;
    private final String details;
    private final long deathTime;

    private Death(final String cause, final String details, final long deathTime){
        this.cause = cause;
        this.details = details;
        this.deathTime = deathTime;
    }

    public Death(final String cause, final String details){
        this(cause, details, System.currentTimeMillis());
    }

    public Death(){
        this("heart attack", "");
    }

    public String getCause() {
        return cause;
    }

    public String getDetails() {
        return details;
    }

    public long getDeathTime() {
        return deathTime;
    }

    public Death withCause(final String cause){
        if (System.currentTimeMillis() < this.deathTime + CAUSE_DELAY){
            return new Death(cause, this.details, this.deathTime);
        } else {
            return this;
        }
    }

    public Death withDetails(final String details){
        if (System.currentTimeMillis() < this.deathTime + DETAILS_DELAY){
            return new Death(this.cause, details, this.deathTime);
        } else {
            return this;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Death death
            && Objects.equals(cause, death.cause)
            && Objects.equals(details, death.details)
            && Objects.equals(deathTime, deathTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cause, details, deathTime);
    }
}
