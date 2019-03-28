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
                if (dataForServlet.getOperation().equals("usersUpdate")) {
                    return new UsersAction().usersUpdate(data);
                } else if (dataForServlet.getOperation().equals("addNewUser")) {
                    return new UsersAction().addUserToDB(data);
                } else {
                    return new UsersAction().deleteUserFromDB(data);
                }
            }
            case "technologies": {
                if (dataForServlet.getOperation().equals("technologiesUpdate")) {
                    return new TechnologiesAction().technologiesUpdate(data);
                } else if (dataForServlet.getOperation().equals("addTechnologyToDB")) {
                    return new TechnologiesAction().addTechnologyToDB(data);
                } else {
                    return new TechnologiesAction().deleteTechnologyFromDB(data);
                }
            }
            case "skills": {
                if (dataForServlet.getOperation().equals("skillsUpdate")) {
                    return new SkillsAction().skillsUpdate(data);
                } else if (dataForServlet.getOperation().equals("addSkillToDB")) {
                    return new SkillsAction().addSkillToDB(data);
                } else {
                    return new SkillsAction().deleteSkillFromDB(data);
                }
            }
            case "useds": {
                if (dataForServlet.getOperation().equals("usedsUpdate")) {
                    return new UsedActions().usedsUpdate(data);
                } else if (dataForServlet.getOperation().equals("addUsedToDB")) {
                    return new UsedActions().addUsedToDB(data);
                } else {
                    return new UsedActions().deleteUsedFromDB(data);
                }
            }
            case "personals": {
                if (dataForServlet.getOperation().equals("personalsUpdate")) {
                    return new PersonalAction().personalsUpdate(data);
                } else if (dataForServlet.getOperation().equals("addPersonalToDB")) {
                    return new PersonalAction().addPersonalToDB(data);
                } else {
                    return new PersonalAction().deletePersonalFromDB(data);
                }
            }
        }
        return "{\"success\": false,\"message\": \"Ошибка обращения к базе данных!\"}";
    }
}
