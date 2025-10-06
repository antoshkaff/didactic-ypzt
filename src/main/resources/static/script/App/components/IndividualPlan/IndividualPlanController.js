import IndividualPlanModel from "./IndividualPlanModel.js"
import IndividualPlanView from "./IndividualPlanView.js"

export default class IndividualPlanController {
    static init() {
        IndividualPlanModel.loadPlan()

        const plan  = IndividualPlanModel.getPlan()
        const planSettings = IndividualPlanModel.getPlanSettings()

        this.renderPlan(plan, planSettings)
    }

    static renderPlan(plan, planSettings) {
        IndividualPlanView.render(plan, planSettings)
    }
}