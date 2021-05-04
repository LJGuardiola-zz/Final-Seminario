package api.storage.mysql;

import api.storage.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class DaoMySQLManager implements DaoManager {

    private final Map<Class<?>, DaoMySQL> daos;

    private DaoMySQLManager() {
        this.daos = new HashMap<>();
    }

    public static DaoMySQLManager getInstance() {
        return DaoMySQLManagerHolder.INSTANCE;
    }

    private static class DaoMySQLManagerHolder {
        private static final DaoMySQLManager INSTANCE = new DaoMySQLManager();
    }

    @Override
    public DaoSession getDaoSession() {
        return (DaoSession) daos.computeIfAbsent(
                DaoSession.class, k -> new DaoMySQLSession()
        );
    }

    @Override
    public DaoPermission getDaoPermission() {
        return (DaoPermission) daos.computeIfAbsent(
                DaoPermission.class, k -> new DaoMySQLPermission()
        );
    }

    @Override
    public DaoRole getDaoRole() {
        return (DaoRole) daos.computeIfAbsent(
                DaoRole.class, k -> new DaoMySQLRole()
        );
    }

    @Override
    public DaoUser getDaoUser() {
        return (DaoUser) daos.computeIfAbsent(
                DaoUser.class, k -> new DaoMySQLUser()
        );
    }

    @Override
    public DaoPerson getDaoPerson() {
        return (DaoPerson) daos.computeIfAbsent(
                DaoPerson.class, k -> new DaoMySQLPerson()
        );
    }

    @Override
    public DaoCitizen getDaoCitizen() {
        return (DaoCitizen) daos.computeIfAbsent(
                DaoCitizen.class, k -> new DaoMySQLCitizen()
        );
    }

    @Override
    public DaoCity getDaoCity() {
        return (DaoCity) daos.computeIfAbsent(
                DaoCity.class, k -> new DaoMySQLCity()
        );
    }

    @Override
    public DaoSpaceCampaign getDaoSpaceCampaign() {
        return (DaoSpaceCampaign) daos.computeIfAbsent(
                DaoSpaceCampaign.class, k -> new DaoMySQLSpaceCampaign()
        );
    }

    @Override
    public DaoSpaceCategory getDaoSpaceCategory() {
        return (DaoSpaceCategory) daos.computeIfAbsent(
                DaoSpaceCategory.class, k -> new DaoMySQLSpaceCategory()
        );
    }

    @Override
    public DaoSpace getDaoSpace() {
        return (DaoSpace) daos.computeIfAbsent(
                DaoSpace.class, k -> new DaoMySQLSpace()
        );
    }

    @Override
    public DaoDevice getDaoDevice() {
        return (DaoDevice) daos.computeIfAbsent(
                DaoDevice.class, k -> new DaoMySQLDevice()
        );
    }

    @Override
    public DaoMovement getDaoMovement() {
        return (DaoMovement) daos.computeIfAbsent(
                DaoMovement.class, k -> new DaoMySQLMovement()
        );
    }

    @Override
    public DaoNotification getDaoNotification() {
        return (DaoNotification) daos.computeIfAbsent(
                DaoNotification.class, k -> new DaoMySQLNotification()
        );
    }

}
