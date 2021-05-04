package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoSpaceCategory;
import api.storage.models.SpaceCategory;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.List;
import java.util.Optional;

public class DaoMySQLSpaceCategory extends DaoMySQL implements DaoSpaceCategory {

    private static final String
            SQL_SELECT_ALL = "SELECT * FROM space_categories",
            SQL_SELECT_BY_ID = "SELECT * FROM space_categories WHERE id = ?",
            SQL_INSERT = "INSERT INTO space_categories (name, description) VALUES (?, ?)",
            SQL_UPDATE = "UPDATE space_categories SET name = ?, description = ? WHERE id = ?",
            SQL_DELETE = "DELETE FROM space_categories WHERE id = ?";

    private static final ResultSetHandler<SpaceCategory>
            ONE_SPACE_CATEGORY = getHandlerForOne(Mappers.spaceCategoryMapper());

    private static final ResultSetHandler<List<SpaceCategory>>
            MANY_SPACE_CATEGORIES = getHandlerForMany(Mappers.spaceCategoryMapper());

    @Override
    public Optional<SpaceCategory> search(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_BY_ID, ONE_SPACE_CATEGORY, id)
                )
        );
    }

    @Override
    public List<SpaceCategory> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_SPACE_CATEGORIES)
        );
    }

    @Override
    public void insert(SpaceCategory spaceCategory) throws DaoException {
        runWithoutResult(
                ex -> spaceCategory.setID(
                        ex.insert(SQL_INSERT,
                                spaceCategory.getName(),
                                spaceCategory.getDescription()
                        )
                )
        );
    }

    @Override
    public void update(SpaceCategory spaceCategory) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_UPDATE,
                        spaceCategory.getName(),
                        spaceCategory.getDescription(),
                        spaceCategory.getID()
                )
        );
    }

    @Override
    public void delete(int id) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_DELETE, id)
        );
    }

}
