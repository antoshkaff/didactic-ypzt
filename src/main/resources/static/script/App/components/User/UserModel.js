export default class UserModel {
    static userData = null

    static async loadUser() {
        this.userData = await fetch('http://localhost/users')
            .then(res => res.json())
    }

    static getUser() {
        return this.userData
    }
}