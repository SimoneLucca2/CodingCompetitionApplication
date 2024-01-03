package com.polimi.ckb.tournament.tournamentService.entity;

import com.polimi.ckb.tournament.entity.Educator;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EducatorTest {

    /**
     * Testing equals method in Educator class. 
     * Two Educator objects are considered equal if they have the same ID.
     */
    @Test
    void testEquals_SameObject_ShouldReturnTrue() {
        Educator educator = new Educator(1L, Collections.emptyList(), Collections.emptyList());
        assertEquals(educator, educator); // same object should be equal
    }

    @Test
    void testEquals_Null_ShouldReturnFalse() {
        Educator educator = new Educator(1L, Collections.emptyList(), Collections.emptyList());
        assertNotEquals(null, educator); // null object should not be equal
    }

    @Test
    void testEquals_SameId_ShouldReturnTrue() {
        Educator educator1 = new Educator(1L, Collections.emptyList(), Collections.emptyList());
        Educator educator2 = new Educator(1L, Collections.emptyList(), Collections.emptyList());
        assertEquals(educator1, educator2); // objects with same id should be equal
    }

    @Test
    void testEquals_DifferentId_ShouldReturnFalse() {
        Educator educator1 = new Educator(1L, Collections.emptyList(), Collections.emptyList());
        Educator educator2 = new Educator(2L, Collections.emptyList(), Collections.emptyList());
        assertNotEquals(educator1, educator2); // objects with different id should not be equal
    }
}