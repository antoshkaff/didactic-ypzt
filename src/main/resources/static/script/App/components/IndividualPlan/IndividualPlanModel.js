import UserController from "../User/UserController.js"

export default class IndividualPlanModel {
    static plan = null

    static loadPlan() {
        const data = UserController.getCurrentUser()

        this.plan = {
            ...data.plan.Themes
        }

    }

    static getPlanSettings() {
        const questionsPerTheme = new Map()

        return {
            questionsPerTheme: 2,

            //
        }
    }

    static calculateTopicScore() {
        // ЖДУ ОТ АРТЕМА
    }

    static getPlan() {
        return this.plan
    }
}