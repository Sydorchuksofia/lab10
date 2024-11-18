import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.DBConnection;

public class DBConnectionTest {
    private DBConnection dbConnection;
    private Path tempDbPath;

    @BeforeEach
    void setup() throws SQLException, IOException {
        tempDbPath = Files.createTempFile("test_cache", ".db");

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + tempDbPath.toAbsolutePath());
        DBConnection.dbConnection = null;
        dbConnection = DBConnection.getInstance();
        dbConnection.connection = connection;

        connection.createStatement().executeUpdate(
                "CREATE TABLE document (" +
                        "path TEXT PRIMARY KEY, " +
                        "parsed TEXT)");
    }

    @AfterEach
    void tearDown() throws SQLException, IOException {
        if (dbConnection.connection != null && !dbConnection.connection.isClosed()) {
            dbConnection.connection.close();
        }

        Files.deleteIfExists(tempDbPath);
    }

    @Test
    void testGetInstance() {
        DBConnection instance = DBConnection.getInstance();
        assertNotNull(instance, "DBConnection instance should not be null.");
    }

    @Test
    void testCreateAndRetrieveDocument() throws SQLException {
        String gcsPath = "test/path";
        String parsedContent = "SampleParsedContent";

        dbConnection.createDocument(gcsPath, parsedContent);

        String retrievedContent = dbConnection.getDocument(gcsPath);

        assertEquals(parsedContent, retrievedContent, "The retrieved content should match the inserted content.");
    }

    @Test
    void testRetrieveNonExistentDocument() throws SQLException {
        String nonExistentPath = "non/existent/path";

        String result = dbConnection.getDocument(nonExistentPath);

        assertNull(result, "Retrieving a non-existent document should return null.");
    }
}