package api;

import api.modules.*;
import api.storage.interfaces.DaoManager;
import api.storage.mysql.DaoMySQLManager;

import java.util.HashMap;

public class Api {

    private final DaoManager daoManager;
    private final HashMap<Class<?>, Module> modules = new HashMap<>();

    private Api() {
        this.daoManager = DaoMySQLManager.getInstance();
    }

    public static Api getInstance() {
        return ApiHolder.INSTANCE;
    }

    private static class ApiHolder {
        private static final Api INSTANCE = new Api();
    }

    private Module getModuleInstance(Class<?> key, Module value) {
        return modules.computeIfAbsent(
                key, k -> value
        );
    }

    public ModulePermission permission() {
        return (ModulePermission) getModuleInstance(
                ModulePermission.class, new ModulePermission(daoManager)
        );
    }

    public ModuleRole role() {
        return (ModuleRole) getModuleInstance(
                ModuleRole.class, new ModuleRole(daoManager)
        );
    }

    public ModuleUser user() {
        return (ModuleUser) getModuleInstance(
                ModuleUser.class, new ModuleUser(daoManager)
        );
    }

    public ModuleApp app() {
        return (ModuleApp) getModuleInstance(
                ModuleApp.class, new ModuleApp(daoManager)
        );
    }

    public ModulePerson person() {
        return (ModulePerson) getModuleInstance(
                ModulePerson.class, new ModulePerson(daoManager)
        );
    }

    public ModuleCategory category() {
        return (ModuleCategory) getModuleInstance(
                ModuleCategory.class, new ModuleCategory(daoManager)
        );
    }

    public ModuleCampaign campaign() {
        return (ModuleCampaign) getModuleInstance(
                ModuleCampaign.class, new ModuleCampaign(daoManager)
        );
    }

    public ModuleSpace space() {
        return (ModuleSpace) getModuleInstance(
                ModuleSpace.class, new ModuleSpace(daoManager)
        );
    }

    public ModuleDevice device() {
        return (ModuleDevice) getModuleInstance(
                ModuleDevice.class, new ModuleDevice(daoManager)
        );
    }

}
