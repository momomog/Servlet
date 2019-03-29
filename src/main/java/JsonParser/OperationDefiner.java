package JsonParser;

import LastUsedDB.UsedActions;
import PersonalDB.PersonalAction;
import SkillsDB.SkillsAction;
import TechnologiesDB.TechnologiesAction;
import UsersDB.UsersAction;

public class OperationDefiner {

    private DataForServlet dataForServlet = new DataForServlet();

    public String takeCurrentOperation(String data) {
        dataForServlet.dataInitilization(data);

        switch (dataForServlet.getDataBase()) {
            case "users": {
                switch (dataForServlet.getOperation()) {
                    case "usersUpdate":
                        return new UsersAction().usersUpdate(data);
                    case "addNewUser":
                        return new UsersAction().addUserToDB(data);
                    case "deleteUser":
                        return new UsersAction().deleteUserFromDB(data);
                    default:
                        return new UsersAction().updateUserdataToDB(data);
                }
            }
            case "technologies": {
                switch (dataForServlet.getOperation()) {
                    case "technologiesUpdate":
                        return new TechnologiesAction().technologiesUpdate(data);
                    case "addTechnologyToDB":
                        return new TechnologiesAction().addTechnologyToDB(data);
                    case "deleteTechnologyFromDB":
                        return new TechnologiesAction().deleteTechnologyFromDB(data);
                    default:
                        return new TechnologiesAction().updateTechnologydataToDB(data);
                }
            }
            case "skills": {
                switch (dataForServlet.getOperation()) {
                    case "skillsUpdate":
                        return new SkillsAction().skillsUpdate(data);
                    case "addSkillToDB":
                        return new SkillsAction().addSkillToDB(data);
                    case "deleteSkillFromDB":
                        return new SkillsAction().deleteSkillFromDB(data);
                    default:
                        return new SkillsAction().updateSkilldataToDB(data);
                }
            }
            case "useds": {
                switch (dataForServlet.getOperation()) {
                    case "usedsUpdate":
                        return new UsedActions().usedsUpdate(data);
                    case "addUsedToDB":
                        return new UsedActions().addUsedToDB(data);
                    case "deleteUsedFromDB":
                        return new UsedActions().deleteUsedFromDB(data);
                    default:
                        return new UsedActions().updateUseddataToDB(data);
                }
            }
            case "personals": {
                switch (dataForServlet.getOperation()) {
                    case "personalsUpdate":
                        return new PersonalAction().personalsUpdate(data);
                    case "addPersonalToDB":
                        return new PersonalAction().addPersonalToDB(data);
                    case "deletePersonal":
                        return new PersonalAction().deletePersonalFromDB(data);
                    default:
                        return new PersonalAction().updatePersonaldataToDB(data);
                }
            }
        }
        return "{\"success\": false,\"message\": \"Ошибка обращения к базе данных!\"}";
    }
}
