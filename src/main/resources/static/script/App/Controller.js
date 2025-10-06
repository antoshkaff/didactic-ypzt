import UserController from "./components/User/UserController.js"
import AuthenticationController from "./components/Authentication/AuthenticationController.js"
import IndividualPlanController from "./components/IndividualPlan/IndividualPlanController.js"
import { ROUTES } from "./constants/routes.js"

export default class Controller {
    static async init() {
        await UserController.saveUser()

        if(window.location.pathname === ROUTES.INDEX || window.location.pathname === '/') {
            console.log(UserController.getCurrentUser())
        }

        if(window.location.pathname === ROUTES.QUIZ) {
            console.log(AuthenticationController.checkAuth())
            if(AuthenticationController.checkAuth()) {
                window.location.pathname = ROUTES.INDIVIDUAL_PLAN
                console.log(UserController.getCurrentUser())
            }

        }

        if(window.location.pathname === ROUTES.RESULT) {
            // Пока что юзер на результате - недоработка
            await UserController.saveUser()
            console.log(UserController.getCurrentUser())
        }

        if(window.location.pathname === ROUTES.INDIVIDUAL_PLAN) {
            IndividualPlanController.init()
        }
    }
}