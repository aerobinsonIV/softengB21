package edu.wpi.cs3733.g.tests.entity;

import edu.wpi.cs3733.g.entities.Teammate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTeammate {
    @Test
    public void TestName() {
        Teammate testTeammate = new Teammate("Peebo", "P");
        assertEquals("Peebo", testTeammate.getName());
    }

    @Test
    public void TestNameEmpty() {
        Teammate testTeammate = new Teammate("", "P");
        assertEquals("", testTeammate.getName());
    }

    @Test
    public void TestNameNull() {
        Teammate testTeammate = new Teammate(null, "P");
        assertEquals(null, testTeammate.getName());
    }
}
