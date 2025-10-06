import UserModel from "./UserModel.js"

export default class UserController {
    static async saveUser() {
        await UserModel.loadUser()
    }

    static getCurrentUser() {
        return UserModel.getUser()
    }
}