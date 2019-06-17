package ru.rsprm;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.functions.Predicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import ru.rsprm.storage.dao.TruckDao;
import ru.rsprm.storage.model.TruckEntity;

import java.util.List;

public class TruckDaoTest extends DatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TruckDao subject;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        subject = database.getTruckDao();
    }

    @Test
    public void testGetAll_putTwoEntities_returnBoth() {
        TruckEntity entity = generateTruckEntity(10, "name-first", "price-first", "comment-first");
        subject.insert(entity);
        TruckEntity entitySecond = generateTruckEntity(20, "name-second", "price-second", "comment-second");
        subject.insert(entitySecond);

        subject.getAll()
                .test()
                .assertValue(new Predicate<List<TruckEntity>>() {
                    @Override
                    public boolean test(List<TruckEntity> entities) throws Exception {
                        return entities.size() == 2;
                    }
                });
    }

    @Test
    public void testDelete_putTwoEntities_getAllReturnsOneWithRightId() {
        TruckEntity entity = generateTruckEntity(10, "name-first", "price-first", "comment-first");
        subject.insert(entity);
        TruckEntity entitySecond = generateTruckEntity(20, "name-second", "price-second", "comment-second");
        subject.insert(entitySecond);

        subject.delete(20);

        subject.getAll()
                .test()
                .assertValue(new Predicate<List<TruckEntity>>() {
                    @Override
                    public boolean test(List<TruckEntity> entities) throws Exception {
                        return entities.size() == 1 && entities.get(0).equals(entity);
                    }
                });
    }

    @Test
    public void testUpdate_putTwoEntities_updateOneWithRightId() {
        TruckEntity entity = generateTruckEntity(10, "name-first", "price-first", "comment-first");
        subject.insert(entity);
        TruckEntity entitySecond = generateTruckEntity(20, "name-second", "price-second", "comment-second");
        subject.insert(entitySecond);
        entity.setName("new-name");

        subject.update(entity);

        subject.getAll()
                .test()
                .assertValue(new Predicate<List<TruckEntity>>() {
                    @Override
                    public boolean test(List<TruckEntity> entities) throws Exception {
                        return entities.get(0).getName().equals("new-name");
                    }
                });
    }

    private TruckEntity generateTruckEntity(int id, String name, String price, String comment) {
        return new TruckEntity(id, name, price, comment);
    }
}
