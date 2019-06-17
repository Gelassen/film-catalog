package ru.rsprm;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import ru.rsprm.storage.Database;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    protected Database database;

    @Before
    public void setUp() throws Exception {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), Database.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }
}

