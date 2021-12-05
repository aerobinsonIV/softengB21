package edu.wpi.cs3733.g.controllers;

import edu.wpi.cs3733.g.db.DatabaseAccess;
import org.junit.jupiter.api.BeforeEach;

public class BaseControllerTest {
    @BeforeEach
    void setupDB() {
        DatabaseAccess.forceReconnectForTesting();
        DatabaseAccess.initSchemaForTesting();
    }
}
