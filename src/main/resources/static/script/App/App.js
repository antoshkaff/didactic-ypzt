import Controller from "./Controller.js"
import UserController from "./components/User/UserController.js"

const App = async () => {
    await UserController.saveUser()
    await Controller.init()
}

App()