package edu.wpi.cs3733.g.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.wpi.cs3733.g.entities.Teammate;

public class TestTeammate {
    @Test
    public void TestName(){
        Teammate testTeammate = new Teammate("Peebo");
        assertEquals("Peebo", testTeammate.getName());
    }

    @Test
    public void TestNameEmpty(){
        Teammate testTeammate = new Teammate("");
        assertEquals("", testTeammate.getName());
    }

    @Test
    public void TestNameNull(){
        Teammate testTeammate = new Teammate(null);
        assertEquals(null, testTeammate.getName());
    }
}
