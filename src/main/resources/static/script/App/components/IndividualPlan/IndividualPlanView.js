export default class IndividualPlanView {
    static selectors = {
        topicElement: '[data-js-plan-topic]',
        topicScoreElement: '[data-js-plan-topic-score]',
        themeElement: '[data-js-plan-theme]',
        themeScoreElement: '[data-js-plan-theme-score]'
    }

    static dataAttributes = {
        topic: 'jsPlanTopic',
        topicScore: 'jsPlanTopicScore',
        theme: 'jsPlanTheme',
        themeScore: 'jsPlanThemeScore'
    }

    static render(plan, planSettings) {
        const themeElements = document.querySelectorAll(this.selectors.themeElement)

        const questions = new Map(Object.entries(plan))
        const allThemesScore = new Map()

        questions.forEach((score, question) => {
            const themeElement = Array.from(themeElements).find((element) => element.dataset[this.dataAttributes.theme] === question)
            const themeScoreElement = themeElement.querySelector(this.selectors.themeScoreElement)

            const themeScorePercentage = score / planSettings.questionsPerTheme * 100
            themeScoreElement.textContent = `${themeScorePercentage}%`

            allThemesScore.set(question, themeScorePercentage)
        })

        const totalScorePerTopic = Array.from(allThemesScore)
            .reduce((counter, [question, score]) => counter + score, 0) / 4

        const topicElement = themeElements[0].closest('ul').previousElementSibling
        const topicScoreElement = topicElement.querySelector(this.selectors.topicScoreElement)

        topicScoreElement.textContent = `${totalScorePerTopic}%`
    }
}