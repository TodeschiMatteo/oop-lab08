package it.unibo.deathnote;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

import static it.unibo.deathnote.api.DeathNote.RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;

class TestDeathNote {

    private DeathNote deathNote;
    private static final String NAME = "Roberto";
    private static final String NAME2 = "Pierfranco";
    private static final String CAUSE = "karting accident";
    private static final String DETAILS = "ran for too long";
    
    @BeforeEach
    void start() {
        deathNote = new DeathNoteImplementation();
    }

    @Test
    void testIllegalRule() {
        deathNote.getRule(2);
        try{
            deathNote.getRule(0);
            fail("Can not get rule below 1");
        } catch (IllegalArgumentException e){
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank()); 
        }
        try{
            deathNote.getRule(-1);
            fail("Can not get rule over the limit");
        } catch (IllegalArgumentException e){
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank()); 
        }
    }

    @Test
    void nonVoidRules() {
        for (String rule: RULES) {
            if(rule == null || rule.isBlank()){
                fail("Rules must not be Null");
            }
        }
    }

    @Test
    void isDead() {
        assertFalse(deathNote.isNameWritten(NAME));
        deathNote.writeName(NAME);
        assertTrue(deathNote.isNameWritten(NAME));
        assertFalse(deathNote.isNameWritten(NAME2));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testCauseDelay() throws InterruptedException{
        try{
            deathNote.writeDeathCause(CAUSE);
            fail("Can not add cause without a name");
        } catch (IllegalStateException e){
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank()); 
        }
        deathNote.writeName(NAME);
        assertEquals(deathNote.getDeathCause(NAME), "heart attack");
        deathNote.writeName(NAME2);
        assertTrue(deathNote.writeDeathCause(CAUSE));
        assertEquals(deathNote.getDeathCause(NAME2), CAUSE);
        Thread.sleep(100L);
        var oldCause = deathNote.getDeathCause(NAME2);
        deathNote.writeDeathCause(CAUSE);
        assertEquals(deathNote.getDeathCause(NAME2), oldCause);
    }

    @Test
    void testDetailsDelay() throws InterruptedException{
        try{
            deathNote.writeDetails(DETAILS);
            fail("Can not add details without a name");
        } catch (IllegalStateException e){
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank()); 
        }
        deathNote.writeName(NAME);
        assertEquals(deathNote.getDeathDetails(NAME), "");
        assertTrue(deathNote.writeDetails(DETAILS));
        assertEquals(deathNote.getDeathDetails(NAME), DETAILS);
        deathNote.writeName(NAME2);
        Thread.sleep(6100L);
        var oldDetails = deathNote.getDeathDetails(NAME2);
        assertFalse(deathNote.writeDetails(DETAILS));
        assertEquals(deathNote.getDeathDetails(NAME2), oldDetails);
    }

}