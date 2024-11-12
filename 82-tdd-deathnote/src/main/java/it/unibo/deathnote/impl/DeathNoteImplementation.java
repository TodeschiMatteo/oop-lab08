package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DeathNoteImplementation implements DeathNote{

    private final Map<String, Death> map;
    private String lastName;

    public DeathNoteImplementation(){
        this.map = new LinkedHashMap<>();
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()){
            throw new IllegalArgumentException("Given rule number is smaller than 1 or larger than the number of rules");
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(final String name) {
        map.put(Objects.requireNonNull(name), new Death());
        lastName = name;
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if ( cause == null ) {
            throw new IllegalStateException("Cause must not be null");
        }
        if ( lastName == null ) {
            throw new IllegalStateException("No name written in this DeathNote");
        }
        final var original = map.get(lastName);
        final var modified = map.get(lastName).withCause(cause);
        if (modified.equals(original)) {
            return false;
        } else {
            map.put(lastName, modified);
            return true;
        }
    }

    @Override
    public boolean writeDetails(final String details) {
        if( details == null ){
            throw new IllegalStateException("Cause must not be null");
        }
        if( lastName == null ){
            throw new IllegalStateException("No name written in this DeathNote");
        }
        final var original = map.get(lastName);
        final var modified = map.get(lastName).withDetails(details);
        if (modified.equals(original)) {
            return false;
        } else {
            map.put(lastName, modified);
            return true;
        }
    }

    @Override
    public String getDeathCause(final String name) {
        if ( !isNameWritten(name) ){
            throw new IllegalArgumentException("Name is not written in the DeathNote");
        }
        final var death = map.get(name);
        return death.getCause();
    }

    @Override
    public String getDeathDetails(final String name) {
        if ( !isNameWritten(name) ){
            throw new IllegalArgumentException("Name is not written in the DeathNote");
        }
        final var death = map.get(name);
        return death.getDetails();
    }

    @Override
    public boolean isNameWritten(final String name) {
        return map.containsKey(name);
    }

}