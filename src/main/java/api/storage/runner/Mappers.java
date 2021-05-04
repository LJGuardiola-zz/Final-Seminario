package api.storage.runner;

import api.storage.models.*;
import api.storage.runner.interfaces.Mapper;

import java.util.Optional;

public class Mappers {

    private Mappers() {
    }

    public static Mapper<Role> roleMapper() {
        return rs -> new Role(
                rs.getInt("roles.id"),
                rs.getString("roles.name"),
                rs.getBoolean("roles.enabled")
        );
    }

    public static Mapper<Permission> permissionMapper() {
        return rs -> new Permission(
                rs.getInt("permissions.id"),
                rs.getString("permissions.code"),
                rs.getString("permissions.description")
        );
    }

    public static Mapper<Optional<Permission>> permissionMapperForRole() {
        return rs -> {
            int id = rs.getInt("permissions.id");
            if (!rs.wasNull())
                return Optional.of(
                        new Permission(
                                id,
                                rs.getString("permissions.code"),
                                rs.getString("permissions.description")
                        )
                );
            return Optional.empty();
        };
    }

    public static Mapper<User> userMapper() {
        return rs -> new User(
                rs.getInt("users.id"),
                rs.getString("users.username"),
                rs.getString("users.email"),
                rs.getString("users.password"),
                User.State.getByCode(
                        rs.getInt("users.state")
                ),
                roleMapper().apply(rs)
        );
    }

    public static Mapper<Session> sessionMapper() {
        return rs -> new Session(
                rs.getInt("sessions.id"),
                rs.getInt("sessions.user_id"),
                rs.getString("sessions.ip"),
                rs.getString("sessions.token"),
                rs.getTimestamp("sessions.update_at").toInstant()
        );
    }

    public static Mapper<Person> personMapper() {
        return rs -> rs.getBoolean("persons.is_natural")
                ? naturalPersonMapper().apply(rs)
                : legalPersonMapper().apply(rs);
    }

    public static Mapper<NaturalPerson> naturalPersonMapper() {
        return rs -> new NaturalPerson(
                rs.getInt("natural_persons.person_id"),
                rs.getString("natural_persons.cuil"),
                rs.getString("natural_persons.first_name"),
                rs.getString("natural_persons.last_name"),
                rs.getDate("natural_persons.birthdate").toLocalDate()
        );
    }

    public static Mapper<LegalPerson> legalPersonMapper() {
        return rs -> new LegalPerson(
                rs.getInt("legal_persons.person_id"),
                rs.getString("legal_persons.cuit"),
                rs.getString("legal_persons.name")
        );
    }

    public static Mapper<City> cityMapper() {
        return rs -> new City(
                rs.getInt("cities.id"),
                rs.getString("cities.name"),
                rs.getString("cities.postcode")
        );
    }

    public static Mapper<SpaceCategory> spaceCategoryMapper() {
        return rs -> new SpaceCategory(
                rs.getInt("space_categories.id"),
                rs.getString("space_categories.name"),
                rs.getString("space_categories.description")
        );
    }

    public static Mapper<SpaceCampaign> spaceCampaignMapper() {
        return rs -> new SpaceCampaign(
                rs.getInt("space_campaigns.id"),
                rs.getString("space_campaigns.message")
        );
    }

    public static Mapper<Space> spaceMapper() {
        return rs -> rs.getBoolean("spaces.is_closed")
                ? closedSpaceMapper().apply(rs)
                : openSpaceMapper().apply(rs);
    }

    public static Mapper<DelimitedArea> delimitedAreaMapper() {
        return rs -> new DelimitedArea(
                new Coordinate(
                        rs.getDouble("spaces.latitude"),
                        rs.getDouble("spaces.longitude")
                ),
                rs.getFloat("spaces.radius")
        );
    }

    public static Mapper<OpenSpace> openSpaceMapper() {
        return rs -> new OpenSpace(
                rs.getInt("spaces.id"),
                rs.getString("spaces.name"),
                rs.getBoolean("spaces.available"),
                rs.getInt("spaces.capacity"),
                delimitedAreaMapper().apply(rs),
                spaceCategoryMapper().apply(rs),
                new SpaceCampaign(
                        rs.getInt("entry_campaign.id"),
                        rs.getString("entry_campaign.message")
                ),
                new SpaceCampaign(
                        rs.getInt("exit_campaign.id"),
                        rs.getString("exit_campaign.message")
                )
        );
    }

    public static Mapper<ClosedSpace> closedSpaceMapper() {
        return rs -> new ClosedSpace(
                rs.getInt("spaces.id"),
                rs.getString("spaces.name"),
                rs.getBoolean("spaces.available"),
                rs.getInt("spaces.capacity"),
                delimitedAreaMapper().apply(rs),
                spaceCategoryMapper().apply(rs),
                new SpaceCampaign(
                        rs.getInt("entry_campaign.id"),
                        rs.getString("entry_campaign.message")
                ),
                new SpaceCampaign(
                        rs.getInt("exit_campaign.id"),
                        rs.getString("exit_campaign.message")
                ),
                new Address(
                        rs.getString("closed_spaces.street"),
                        new City(
                                rs.getInt("cities.id"),
                                rs.getString("cities.name"),
                                rs.getString("cities.postcode")
                        )
                ),
                personMapper().apply(rs)
        );
    }

    public static Mapper<DeviceCompany> deviceCompanyMapper() {
        return rs -> new DeviceCompany(
                rs.getInt("device_companies.id"),
                rs.getString("device_companies.name")
        );
    }

    public static Mapper<DeviceBrand> deviceBrandMapper() {
        return rs -> new DeviceBrand(
                rs.getInt("device_brands.id"),
                rs.getString("device_brands.name")
        );
    }

    public static Mapper<DeviceModel> deviceModelMapper() {
        return rs -> new DeviceModel(
                rs.getInt("device_models.id"),
                rs.getString("device_models.name"),
                deviceBrandMapper().apply(rs)
        );
    }

    public static Mapper<Device> deviceMapper() {
        return rs -> new Device(
                rs.getInt("devices.id"),
                rs.getString("devices.number"),
                deviceCompanyMapper().apply(rs),
                deviceModelMapper().apply(rs),
                rs.getTimestamp("devices.start_date").toLocalDateTime(),
                rs.getTimestamp("end_date") != null
                        ? rs.getTimestamp("end_date").toLocalDateTime()
                        : null
        );
    }

    public static Mapper<Movement> movementMapper() {
        return rs -> new Movement(
                rs.getInt("movements.id"),
                deviceMapper().apply(rs),
                spaceMapper().apply(rs),
                rs.getInt("movements.amount"),
                rs.getTimestamp("movements.entry_date").toLocalDateTime(),
                rs.getTimestamp("movements.exit_date") != null
                        ? rs.getTimestamp("movements.exit_date").toLocalDateTime()
                        : null
        );
    }

    public static Mapper<Notification> notificationMapper() {
        return rs -> new Notification(
                rs.getInt("notifications.id"),
                deviceMapper().apply(rs),
                rs.getString("notifications.message"),
                rs.getTimestamp("notifications.date").toLocalDateTime()
        );
    }
}
